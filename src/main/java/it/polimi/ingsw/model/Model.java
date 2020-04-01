package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {


    private ArrayList<Player> players = new ArrayList<Player>();
    private IslandBoard board = new IslandBoard();


    public void addPlayer(Player p) {
        players.add(p);
    }

    public void addWorker(int playerPosition, Coordinate c, int workerPosition) throws Exception {

        board.infoSlot(c).occupy(players.get(playerPosition).getWorker(workerPosition));
        players.get(playerPosition).getWorker(workerPosition).setPosition(c);

    }

    public void setCard(int playerPosition, String card) throws CloneNotSupportedException {

        players.get(playerPosition).setCard(card.toUpperCase());
        setChanged();
        notifyObservers(board.clone());

    }
    public Player getPlayer(int i){
        return this.players.get(i);
    }

    public void turnHandler(int i, String message) throws  Exception{

        players.get(i).turnHandler(this.board, message);
        setChanged();
        notifyObservers(board);

    }
}
