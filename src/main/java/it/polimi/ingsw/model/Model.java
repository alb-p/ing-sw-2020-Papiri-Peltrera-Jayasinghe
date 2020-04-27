package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.messages.ActionMessage;
import it.polimi.ingsw.utils.messages.WinnerMessage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Model {


    private ArrayList<Player> players = new ArrayList<Player>();
    private IslandBoard board = new IslandBoard();
    private VirtualBoard oldBoard;
    private VirtualSlot vSlot;

    private PropertyChangeSupport modelListeners = new PropertyChangeSupport(this);


    public void addPlayer(Player p) {
        players.add(p);
    }

    public void addWorker(int playerIndex, Coordinate c, int workerIndex) throws Exception {
        board.infoSlot(c).occupy(players.get(playerIndex).getWorker(workerIndex));
        players.get(playerIndex).getWorker(workerIndex).setPosition(c);
        vSlot = new VirtualSlot(board.infoSlot(c), c);
        modelListeners.firePropertyChange("deltaUpdate", null, vSlot);

    }


    public void addModelListener(PropertyChangeListener listener) {
        modelListeners.addPropertyChangeListener(listener);
    }


    public void setCard(int playerPosition, String card) throws CloneNotSupportedException {
        players.get(playerPosition).setCard(card.toUpperCase());
        modelListeners.firePropertyChange("turnHandler", null, true);

    }


    public Player getPlayer(int id) {
        for (Player p : players) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public int getIndex(int id) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == id)
                return i;
        }
        return -1;
    }



    public int getNumOfPlayers() {
        return this.players.size();
    }

    public boolean nameDuplicate(String name) {
        for (Player p : players) {
            if (p.getNickName().equals(name)) return true;
        }
        return false;
    }

    public void turnHandler(int idPlayerPlaying, Action message)  {
        oldBoard = new VirtualBoard(board);
        try {
            players.get(idPlayerPlaying).turnHandler(this.board, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyChanges();
        if(players.get(idPlayerPlaying).hasDone()){

        }else{

        }
        modelListeners.firePropertyChange("turnHandler", null, true);
    }

    private void notifyChanges() {
        VirtualSlot oldVSlot;

        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {

                oldVSlot = oldBoard.getSlot(i, j);
                vSlot = new VirtualSlot(board.getSlot(i, j), new Coordinate(i, j));

                modelListeners.firePropertyChange("deltaUpdate", oldVSlot, vSlot);
            }
        }

    }

    public void buildPlayerTree(int i) {
        try {
            players.get(i).playerTreeSetup(board);
        } catch (Exception e) {
            e.printStackTrace();
        }
        verifyTree(i);
    }


    public void verifyTree(int currPlayer) {
        for(int i = 0 ; i<players.size(); i++){
            if(currPlayer != i) {
                players.get(i).getCard().specialRule(players.get(currPlayer).getTrees(),players.get(currPlayer).getHashList(),board);
            }
        }
    }

    public void selectPlayerPlaying(int id) {

        modelListeners.firePropertyChange("sendAction",
                null, new ActionMessage(id, players.get(id).getAvailableAction() ,
                        players.get(id).getNickName()));
    }

    public void checkWinner(int id) {
        if(players.get(id).getCard().winningCondition(players.get(id).getActualWorker())){
            modelListeners.firePropertyChange("winnerDetected", null, new WinnerMessage(id, players.get(id).getNickName()));
        }
    }
}
