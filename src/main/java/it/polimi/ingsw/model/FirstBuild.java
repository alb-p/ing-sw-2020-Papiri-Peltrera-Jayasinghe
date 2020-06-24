package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * The type First build.
 */
public class FirstBuild extends Action implements Serializable {

    private static final long serialVersionUID = 8864784887990391217L;

    /**
     * Instantiates a new First build.
     *
     * @param start the start
     * @param end   the end
     */
    public FirstBuild(Coordinate start, Coordinate end) {
        super("build", start, end);
    }


    /**
     * Gets action name.
     *
     * @return the action name
     */
    @Override
    public String getActionName() {
        return "build";
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof FirstBuild)) return false;
        return(this.getStart().equals(((FirstBuild) o).getStart()) &&
                this.getEnd().equals(((FirstBuild) o).getEnd()));
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
