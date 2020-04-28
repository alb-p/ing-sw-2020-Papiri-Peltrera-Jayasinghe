package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.Worker;

import java.io.Serializable;

public class WorkerMessage implements Message, Serializable {
    int workerNumber;
    String message;
    Coordinate coordinate;
    int id;


    public WorkerMessage(int ID, int n) {
        this.id = ID;
        this.workerNumber=n;
        this.message = "Posiziona il worker n."+workerNumber;
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

    public int getWorkerNumber(){
        return this.workerNumber;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
