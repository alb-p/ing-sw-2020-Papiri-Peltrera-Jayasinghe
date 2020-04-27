package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.Worker;

public class WorkerMessage implements Message {
    String message = "Aspettate voialtri stolti"; //usato per la waiting
    Worker worker;
    int id;
    Coordinate coordinate;

    public WorkerMessage(int lastPlayerID) {
        this.id = lastPlayerID;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
