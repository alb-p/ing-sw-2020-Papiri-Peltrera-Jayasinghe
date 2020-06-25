package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * The type Move represent the action of
 * the move on the board.
 */
public class Move extends Action implements Serializable {

    private static final long serialVersionUID = -2330388965632492463L;

    /**
     * Instantiates a new Move.
     *
     * @param start the start
     * @param end   the end
     */
    public Move(Coordinate start, Coordinate end){
        super("move",start, end);
    }

    /**
     * Equals boolean.
     *
     * @param that the that
     * @return the boolean
     */
    @Override
    public boolean equals(Object that) {
        if(!(that instanceof Move)) return false;
        return(this.getStart().equals(((Move) that).getStart()) &&
                this.getEnd().equals(((Move) that).getEnd()));
    }

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

