package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Model {


    private ArrayList<Player> players = new ArrayList<Player>();
    private IslandBoard board = new IslandBoard();
    private VirtualBoard oldBoard = new VirtualBoard();
    private VirtualSlot vSlot;

    private PropertyChangeSupport modelListeners = new PropertyChangeSupport(this);

    public void addModelListener(PropertyChangeListener listener) {
        modelListeners.addPropertyChangeListener(listener);
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public IslandBoard getBoard() {
        return this.board;
    }

    public int getNumOfPlayers() {
        return this.players.size();
    }

    public void setCard(int playerID, String card) {
        this.getPlayer(playerID).setCard(card.toUpperCase());
    }


    public boolean addWorker(int playerIndex, Coordinate c, int workerIndex) {
        oldBoard = cloneVBoard(board);
        try {
            if (c.isValid() && board.infoSlot(c).isFree()) {
                board.infoSlot(c).occupy(this.getPlayer(playerIndex).getWorker(workerIndex));
                this.getPlayer(playerIndex).getWorker(workerIndex).setPosition(c);
            } else {
                return false;
            }

            notifyChanges();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Player getPlayer(int id) {
        for (Player p : players) {
            if (p.getId() == id) return p;
        }
        return null;
    }


    public void turnHandler(int idPlayerPlaying, Action message) {
        oldBoard = cloneVBoard(board);
        try {
            boolean turnhan;
            turnhan = this.getPlayer(idPlayerPlaying).turnHandler(this.board, message);
            System.out.println(board);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyChanges();
    }

    public VirtualBoard cloneVBoard(IslandBoard board) {
        VirtualBoard newBoard = new VirtualBoard();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Color color = null;
                Coordinate c = new Coordinate(row, col);
                if (board.infoSlot(c).getWorker() != null) {
                    color = board.infoSlot(c).getWorker().getColor();
                }
                VirtualSlot tempo = new VirtualSlot(color,
                        board.infoSlot(c).getConstructionLevel(), board.infoSlot(c).hasADome(), c);
                newBoard.setSlot(tempo);
            }
        }
        return newBoard;
    }

    public void notifyChanges() {
        VirtualSlot oldVSlot;
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                oldVSlot = oldBoard.getSlot(i, j);
                Color color = null;
                if (board.infoSlot(new Coordinate(i, j)).getWorker() != null) {
                    color = board.infoSlot(new Coordinate(i, j)).getWorker().getColor();
                }
                vSlot = new VirtualSlot(color, board.infoSlot(new Coordinate(i, j)).getConstructionLevel(),
                        board.infoSlot(new Coordinate(i, j)).hasADome(), new Coordinate(i, j));

                if (!oldVSlot.equals(vSlot)) {
                    modelListeners.firePropertyChange("deltaUpdate", null, vSlot);
                }
                modelListeners.firePropertyChange("deltaUpdate", oldVSlot, vSlot);

            }
        }
    }


    //It invokes specialRule on opponent gods'
    public void treeEditorBySpecialRule(int currPlayer) {
        for (Player p : players) {
            if (p.getId() != currPlayer) {
                p.getCard().specialRule(this.getPlayer(currPlayer).getTree(), board);
            }
        }

    }
    public void sendActions(int id){
        if(getPlayer(id).getTree()==null){
            buildTree(id);
        }
        ActionMessage message = getPlayer(id).getNextActions();
        message.setOptional(getPlayer(id).essentialDone());
        if (!message.getChoices().isEmpty()) {
            modelListeners.firePropertyChange("actionsAvailable", null, message);
        }
        else notifyPlayerHasLost(id);
    }


    //crea l'albero e lo fa correggere dagli altri dei. //si potrebbe spostare in model.java
    public void buildTree(int ID) {
        try {
            getPlayer(ID).playerTreeSetup(board);
            treeEditorBySpecialRule(ID);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public boolean checkWinner(int id) {
        if (this.getPlayer(id).getCard().winningCondition(this.getPlayer(id).getActualWorker(), board, oldBoard)) {
            modelListeners.firePropertyChange("winnerDetected", null,
                    new WinnerMessage(id, this.getPlayer(id).getNickName()));
            return true;
        }
        return false;
    }

    public void removePlayer(int id) {
        for (int i = 0; i < 2; i++) {
            board.infoSlot(getPlayer(id).getWorker(i).getPosition()).free();
            getPlayer(id).getWorker(i).setPosition(new Coordinate(-1, -1));
        }
        Player toRemove = getPlayer(id);
        players.remove(toRemove);
        notifyChanges();
    }

    public void removeModelListener(PropertyChangeListener listener){
        modelListeners.removePropertyChangeListener(listener);
    }

    public void endGameForNoAvailableMoves(int id) {
        int winnerID = -1;
        for (Player player : players) {
            if (player.getId() != id) winnerID = player.getId();
        }
        modelListeners.firePropertyChange("winnerDetected", null,
                new WinnerMessage(winnerID, this.getPlayer(winnerID).getNickName()));

    }

    public void notifyPlayerHasLost(int id) {
        modelListeners.firePropertyChange("playerLostDetected", null,
                new GenericMessage(id, this.getPlayer(id).getNickName(), " has no more available actions!"));
    }


    public void endTurn(int id) {
        getPlayer(id).setEndTurn();
        modelListeners.firePropertyChange("endTurnConfirm", null,
                new NicknameMessage(id, this.getPlayer(id).getNickName()));
        notifyChanges();
    }

    public void endGame(int id) {
        if(id<0){
            modelListeners.firePropertyChange("endGame", false, true);
            int n = modelListeners.getPropertyChangeListeners().length;
            for (int i = 0 ; i<n; i++){
                modelListeners.removePropertyChangeListener(modelListeners.getPropertyChangeListeners()[0]);

            }
        }
    }
}
