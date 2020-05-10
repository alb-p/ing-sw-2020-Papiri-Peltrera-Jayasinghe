package it.polimi.ingsw.model;


import java.io.Serializable;

public class BuildDome extends Build implements Serializable {

    public BuildDome( Coordinate start, Coordinate end) {
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
    public Action clone() {
        return new BuildDome(null, null);
    }

}

