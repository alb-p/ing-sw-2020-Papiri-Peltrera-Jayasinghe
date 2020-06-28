package it.polimi.ingsw.actions;


import it.polimi.ingsw.utils.Coordinate;

import java.io.Serializable;

/**
 * The type Build dome represent the
 * building of a dome at any level.
 */
public class BuildDome extends Build implements Serializable {

    private static final long serialVersionUID = -7456987556548235970L;

    /**
     * Instantiates a new Build dome.
     *
     * @param start the start coordinate
     * @param end   the end coordinate
     */
    public BuildDome(Coordinate start, Coordinate end) {
        super(start, end);
    }

    /**
     * Gets action name.
     *
     * @return "Build a dome"
     */
    @Override
    public String getActionName() {
        return "Build a dome";
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BuildDome)) return false;
        return (this.getStart().equals(((BuildDome) o).getStart()) &&
                this.getEnd().equals(((BuildDome) o).getEnd()));
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