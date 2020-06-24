package it.polimi.ingsw.model;


import java.io.Serializable;

/**
 * The type Build dome.
 */
public class BuildDome extends Build implements Serializable {

    private static final long serialVersionUID = -7456987556548235970L;

    /**
     * Instantiates a new Build dome.
     *
     * @param start the start
     * @param end   the end
     */
    public BuildDome(Coordinate start, Coordinate end) {
        super( start, end);
    }

    /**
     * Gets action name.
     *
     * @return the action name
     */
    @Override
    public String getActionName() {
        return  "Build a dome";
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof BuildDome)) return false;
        return(this.getStart().equals(((BuildDome) o).getStart()) &&
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

