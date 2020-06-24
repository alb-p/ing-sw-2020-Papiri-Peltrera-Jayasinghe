package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * The type Action.
 */
public abstract class Action implements Serializable {

    private static final long serialVersionUID = 7260812342692728917L;
    private Coordinate start;
    private final String actionName;
    private Coordinate end;

    /**
     * Instantiates a new Action.
     *
     * @param actionName the action name
     * @param start      the start
     * @param end        the end
     */
    public Action(String actionName, Coordinate start, Coordinate end){
        this.start = start;
        this.end = end;
        this.actionName = actionName;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(Coordinate start) {
        this.start = start;
    }

    /**
     * Set end.
     *
     * @param end the end
     */
    public void setEnd(Coordinate end){
        this.end = end;
    }

    /**
     * Get action name string.
     *
     * @return the string
     */
    public String getActionName(){return this.actionName;}

    /**
     * Get start coordinate.
     *
     * @return the coordinate
     */
    public Coordinate getStart(){
        return this.start;
    }

    /**
     * Get end coordinate.
     *
     * @return the coordinate
     */
    public Coordinate getEnd(){
        return this.end;
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    public abstract boolean equals(Object o);

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
