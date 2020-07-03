package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.utils.Coordinate;

import java.io.Serializable;

/**
 * The type Worker message is a message
 *  * that vehicles worker information..
 */
public class WorkerMessage implements Message, Serializable {
    private static final long serialVersionUID = 6592641812520811496L;
    private final int workerNumber;
    private final String message;
    private Coordinate coordinate;
    private int id;


    /**
     * Instantiates a new Worker message.
     *
     * @param id the id
     * @param n  the worker number
     */
    public WorkerMessage(int id, int n) {
        this.id = id;
        this.workerNumber=n;
        this.message = "";
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Get worker number int.
     *
     * @return the int
     */
    public int getWorkerNumber(){
        return this.workerNumber;
    }

    /**
     * Gets coordinate.
     *
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Sets coordinate.
     *
     * @param coordinate the coordinate
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
