package it.polimi.ingsw.model;

import java.io.Serializable;

public class Build extends Action implements Serializable {

    public Build(Coordinate start, Coordinate end){
        super("build",start, end);

    }
    
    @Override
    public boolean equals(Object that) {
        if(!(that instanceof Build)) return false;
        return(this.getStart().equals(((Build) that).getStart()) &&
                this.getEnd().equals(((Build) that).getEnd()));
    }

    @Override
    public Action clone() {
        return new Build(null, null);
    }
}
