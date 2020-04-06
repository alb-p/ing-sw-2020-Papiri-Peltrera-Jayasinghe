package it.polimi.ingsw.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Observable;

public class Model {


    private ArrayList<Player> players = new ArrayList<Player>();
    private IslandBoard board = new IslandBoard();
    private ViewBoard oldBoard;
    ViewSlot vSlot;

    private PropertyChangeSupport modelListeners = new PropertyChangeSupport(this);


    public void addPlayer(Player p) {
        players.add(p);
    }

    public void addWorker(int playerPosition, Coordinate c, int workerPosition) throws Exception {

        board.infoSlot(c).occupy(players.get(playerPosition).getWorker(workerPosition));
        players.get(playerPosition).getWorker(workerPosition).setPosition(c);

        vSlot = new ViewSlot(board.infoSlot(c), c);

        modelListeners.firePropertyChange("deltaUpdate", null, vSlot);

    }


    public void addModelListener(PropertyChangeListener listener) {
        modelListeners.addPropertyChangeListener(listener);
    }


    public void setCard(int playerPosition, String card) throws CloneNotSupportedException {

        players.get(playerPosition).setCard(card.toUpperCase());
        modelListeners.firePropertyChange("turnHandler", null, true);

    }


    public Player getPlayer(int i) {
        return this.players.get(i);
    }


    public void turnHandler(int i, String message) throws Exception {
        oldBoard = new ViewBoard(board);
        players.get(i).turnHandler(this.board, message);
        notifyChanges();

        modelListeners.firePropertyChange("turnHandler", null, true);


    }

    private void notifyChanges() {

        ViewSlot oldVSlot;

        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {

                oldVSlot = oldBoard.getSlot(i, j);
                vSlot = new ViewSlot(board.getSlot(i, j), new Coordinate(i, j));

                modelListeners.firePropertyChange("deltaUpdate", oldVSlot, vSlot);
            }
        }

    }


}
