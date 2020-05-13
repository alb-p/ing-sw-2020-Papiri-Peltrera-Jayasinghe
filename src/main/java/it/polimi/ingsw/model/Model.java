package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ActionsEnum;
import it.polimi.ingsw.utils.messages.ActionMessage;
import it.polimi.ingsw.utils.messages.ChoiceMessage;
import it.polimi.ingsw.utils.messages.GenericMessage;
import it.polimi.ingsw.utils.messages.WinnerMessage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Model {


    private ArrayList<Player> players = new ArrayList<Player>();
    private IslandBoard board = new IslandBoard();
    private VirtualBoard oldBoard = new VirtualBoard();
    private VirtualSlot vSlot;

    private PropertyChangeSupport modelListeners = new PropertyChangeSupport(this);


    public void addPlayer(Player p) {
        players.add(p);
    }

    public boolean addWorker(int playerIndex, Coordinate c, int workerIndex) {
        oldBoard = new VirtualBoard(board);
        try {
            if (c.isValid() && board.infoSlot(c).isFree()) {
                board.infoSlot(c).occupy(this.getPlayer(playerIndex).getWorker(workerIndex));
                this.getPlayer(playerIndex).getWorker(workerIndex).setPosition(c);
            } else return false;
            notifyChanges();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void addModelListener(PropertyChangeListener listener) {
        modelListeners.addPropertyChangeListener(listener);
    }


    public void setCard(int playerPosition, String card) throws CloneNotSupportedException {
        this.getPlayer(playerPosition).setCard(card.toUpperCase());
    }


    public Player getPlayer(int id) {
        for (Player p : players) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public int getNumOfPlayers() {
        return this.players.size();
    }

    public void turnHandler(int idPlayerPlaying, Action message) {
        oldBoard = new VirtualBoard(board);
        try {
            this.getPlayer(idPlayerPlaying).turnHandler(this.board, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyChanges();
        if (this.getPlayer(idPlayerPlaying).hasDone()) {

        } else {

        }
        modelListeners.firePropertyChange("turnHandler", null, true);
    }

    public void notifyChanges() {
        VirtualSlot oldVSlot;
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {

                oldVSlot = oldBoard.getSlot(i, j);
                vSlot = new VirtualSlot(board.getSlot(i, j), new Coordinate(i, j));
                if (!oldVSlot.equals(vSlot)) {
                    System.out.println("CAMBIATO SLOT");
                    modelListeners.firePropertyChange("deltaUpdate", null, vSlot);
                }
                modelListeners.firePropertyChange("deltaUpdate", oldVSlot, vSlot);

            }
        }
        System.out.println("OLDBOARD:: " + oldBoard);
    }

    public boolean buildPlayerTree(int i) {
        try {
            this.getPlayer(i).playerTreeSetup(board);
            verifyTree(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return getPlayer(i).treesAreLeaf();
    }

    //It invokes specialRule on oppent gods'
    public void verifyTree(int currPlayer) {
        for (int i = 0; i < players.size(); i++) {
            if (currPlayer != i) {
                this.getPlayer(i).getCard().specialRule(this.getPlayer(currPlayer).getTrees(), this.getPlayer(currPlayer).getHashList(), board);
            }
        }
    }

    public void selectPlayerPlaying(int id) {
        ActionMessage message = this.getPlayer(id).getAvailableAction();
        if (message.getActionsAvailable().equals(ActionsEnum.BUILD)) {
            modelListeners.firePropertyChange("sendAction",
                    null, message);
        } else {
            modelListeners.firePropertyChange("sendChoice",
                    null, new ChoiceMessage(message));
        }
    }

    //It modifies the action tree and returns true when player continues his turn, false when he want to end the turn
    public boolean selectChoice(ChoiceMessage message) {
        ActionMessage actionMessage = this.getPlayer(message.getId()).modifierTree(message.getMessage());
        if (actionMessage != null) {
            modelListeners.firePropertyChange("sendAction",
                    null, actionMessage);
            return true;
        }
        return false;
    }

    public boolean checkWinner(int id) {
        if (this.getPlayer(id).getCard().winningCondition(this.getPlayer(id).getActualWorker(), board, oldBoard)) {
            modelListeners.firePropertyChange("winnerDetected", null, new WinnerMessage(id, this.getPlayer(id).getNickName()));
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

    public void endGameForNoAvailableMoves(int id) {
        int winnerID = -1;
        for (Player player : players) {
            if (player.getId() != id) winnerID = player.getId();
        }
        modelListeners.firePropertyChange("winnerDetected", null, new WinnerMessage(winnerID, this.getPlayer(winnerID).getNickName()));

    }

    public void notifyPlayerHasLost(int id) {
        modelListeners.firePropertyChange("playerLostDetected", null, new GenericMessage(id, this.getPlayer(id).getNickName()," has no more available actions!"));

    }
}
