package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * The type Build.
 */
public class Build extends Action implements Serializable {

    private static final long serialVersionUID = -6681718982330941389L;

    /**
     * Instantiates a new Build.
     *
     * @param start the start
     * @param end   the end
     */
    public Build(Coordinate start, Coordinate end){
        super("build",start, end);

    }

    /**
     * Equals boolean.
     *
     * @param that the that
     * @return the boolean
     */
    @Override
    public boolean equals(Object that) {
        if(!(that instanceof Build)) return false;
        return(this.getStart().equals(((Build) that).getStart()) &&
                this.getEnd().equals(((Build) that).getEnd()) && this.getActionName().equalsIgnoreCase(((Build) that).getActionName()));
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
