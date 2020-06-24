package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Model.
 */
public class Model {


    private final ArrayList<Player> players = new ArrayList<>();
    private final IslandBoard board = new IslandBoard();
    private VirtualBoard oldBoard = new VirtualBoard();
    private final PropertyChangeSupport modelListeners = new PropertyChangeSupport(this);
    private boolean winnerDetected = false;
    Logger logger = Logger.getLogger("model");

    /**
     * Add model listener.
     *
     * @param listener the listener
     */
    public void addModelListener(PropertyChangeListener listener) {
        modelListeners.addPropertyChangeListener(listener);
    }

    /**
     * Add player.
     *
     * @param p the p
     */
    public void addPlayer(Player p) {
        players.add(p);
    }

    /**
     * Gets board.
     *
     * @return the board
     */
    public IslandBoard getBoard() {
        return this.board;
    }

    /**
     * Gets num of players.
     *
     * @return the num of players
     */
    public int getNumOfPlayers() {
        return this.players.size();
    }

    /**
     * Sets card.
     *
     * @param playerID the player id
     * @param card     the card
     */
    public void setCard(int playerID, String card) {
        this.getPlayer(playerID).setCard(card.toUpperCase());
    }


    /**
     * Add worker boolean.
     *
     * @param playerIndex the player index
     * @param c           the c
     * @param workerIndex the worker index
     * @return the boolean
     */
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


    /**
     * Gets player.
     *
     * @param id the id
     * @return the player
     */
    public Player getPlayer(int id) {
        for (Player p : players) {
            if (p.getId() == id) return p;
        }
        return null;
    }


    /**
     * Turn handler.
     *
     * @param idPlayerPlaying the id player playing
     * @param message         the message
     */
    public void turnHandler(int idPlayerPlaying, Action message) {
        oldBoard = cloneVBoard(board);
        try {
            boolean turnHandler;
            turnHandler = this.getPlayer(idPlayerPlaying).turnHandler(this.board, message);
            if(!turnHandler) logger.log(Level.WARNING, "Invalid Action");
            System.out.println(board);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        notifyChanges();
    }

    /**
     * Clone v board virtual board.
     *
     * @param board the board
     * @return the virtual board
     */
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

    /**
     * Notify changes.
     */
    public void notifyChanges() {
        VirtualSlot oldVSlot;
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                oldVSlot = oldBoard.getSlot(new Coordinate(i, j));
                Color color = null;
                if (board.infoSlot(new Coordinate(i, j)).getWorker() != null) {
                    color = board.infoSlot(new Coordinate(i, j)).getWorker().getColor();
                }
                VirtualSlot vSlot = new VirtualSlot(color, board.infoSlot(new Coordinate(i, j)).getConstructionLevel(),
                        board.infoSlot(new Coordinate(i, j)).hasADome(), new Coordinate(i, j));

                modelListeners.firePropertyChange("deltaUpdate", oldVSlot, vSlot);

            }
        }
    }


    /**
     * Tree editor by special rule.
     *
     * @param currPlayer the curr player
     */
//It invokes specialRule on opponent gods'
    public void treeEditorBySpecialRule(int currPlayer) {
        for (Player p : players) {
            if (p.getId() != currPlayer) {
                p.getCard().specialRule(this.getPlayer(currPlayer).getTree(), board);
            }
        }

    }

    /**
     * Send actions.
     *
     * @param id the id
     */
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


    /**
     * Build tree.
     *
     * @param ID the id
     */
//crea l'albero e lo fa correggere dagli altri dei.
    public void buildTree(int ID) {
        try {
            getPlayer(ID).playerTreeSetup(board);
            treeEditorBySpecialRule(ID);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Check winner boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean checkWinner(int id) {
        if (this.getPlayer(id).getCard().winningCondition(this.getPlayer(id).getActualWorker(), board, oldBoard)) {
            winnerDetected = true;
            modelListeners.firePropertyChange("winnerDetected", null,
                    new WinnerMessage(id, this.getPlayer(id).getNickName()));
            return true;
        }
        return false;
    }

    /**
     * Remove player.
     *
     * @param id the id
     */
    public void removePlayer(int id) {
        for (int i = 0; i < 2; i++) {
            board.infoSlot(getPlayer(id).getWorker(i).getPosition()).free();
            getPlayer(id).getWorker(i).setPosition(new Coordinate(-1, -1));
        }
        Player toRemove = getPlayer(id);
        players.remove(toRemove);
        notifyChanges();
    }

    /**
     * Remove model listener.
     *
     * @param listener the listener
     */
    public void removeModelListener(PropertyChangeListener listener){
        modelListeners.removePropertyChangeListener(listener);
    }

    /**
     * End game for no available moves.
     *
     * @param id the id
     */
    public void endGameForNoAvailableMoves(int id) {
        int winnerID = -1;
        for (Player player : players) {
            if (player.getId() != id) winnerID = player.getId();
        }
        modelListeners.firePropertyChange("winnerDetected", null,
                new WinnerMessage(winnerID, this.getPlayer(winnerID).getNickName()));

    }

    /**
     * Notify player has lost.
     *
     * @param id the id
     */
    public void notifyPlayerHasLost(int id) {
        modelListeners.firePropertyChange("playerLostDetected", null,
                new GenericMessage(id,"", " has no more available actions!"));
    }


    /**
     * End turn.
     *
     * @param id the id
     */
    public void endTurn(int id) {
        getPlayer(id).setEndTurn();
        modelListeners.firePropertyChange("endTurnConfirm", null,
                new NicknameMessage(id, this.getPlayer(id).getNickName()));
        //notifyChanges();
    }

    /**
     * End game.
     *
     * @param id the id
     */
    public void endGame(int id) {
        if(id<0){
            modelListeners.firePropertyChange("endGame", false, true);
            int n = modelListeners.getPropertyChangeListeners().length;
            for (int i = 0 ; i<n; i++){
                modelListeners.removePropertyChangeListener(modelListeners.getPropertyChangeListeners()[0]);

            }
        }
    }

    /**
     * Is winner detected boolean.
     *
     * @return the boolean
     */
    public boolean isWinnerDetected() {
        return winnerDetected;
    }

}
