package it.polimi.ingsw.model;


import java.io.Serializable;

public class BuildDome extends Build implements Serializable {

    private static final long serialVersionUID = -7456987556548235970L;

    public BuildDome(Coordinate start, Coordinate end) {
        super( start, end);
    }

    @Override
    public String getActionName() {
        return  "Build a dome";
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof BuildDome)) return false;
        return(this.getStart().equals(((BuildDome) o).getStart()) &&
                this.getEnd().equals(((BuildDome) o).getEnd()));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

