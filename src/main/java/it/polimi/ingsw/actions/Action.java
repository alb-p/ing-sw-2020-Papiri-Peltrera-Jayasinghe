package it.polimi.ingsw.actions;

import it.polimi.ingsw.utils.Coordinate;

import java.io.Serializable;

/**
 * The type Action represent
 * the step operation the player
 * need to perform to play
 * the game
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
     * @param start      the start coordinate
     * @param end        the final coordinate
     */
    public Action(String actionName, Coordinate start, Coordinate end){
        this.start = start;
        this.end = end;
        this.actionName = actionName;
    }

    /**
     * Sets start coordinate.
     *
     * @param start  start
     */
    public void setStart(Coordinate start) {
        this.start = start;
    }

    /**
     * Set final coordinate.
     *
     * @param end the end
     */
    public void setEnd(Coordinate end){
        this.end = end;
    }

    /**
     * Get action name string.
     *
     * @return the name of the action in a string
     */
    public String getActionName(){return this.actionName;}

    /**
     * Get start coordinate.
     *
     * @return the start coordinate
     */
    public Coordinate getStart(){
        return this.start;
    }

    /**
     * Get end coordinate.
     *
     * @return the end coordinate
     */
    public Coordinate getEnd(){
        return this.end;
    }

    /**
     * Equals boolean.
     *
     * @param o the other object
     * @return true if the actions represent the same operation
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
