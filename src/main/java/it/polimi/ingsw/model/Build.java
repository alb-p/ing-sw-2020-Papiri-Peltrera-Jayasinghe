package it.polimi.ingsw.model;

import java.io.Serializable;

public class Build extends Action implements Serializable {

    private static final long serialVersionUID = -6681718982330941389L;

    public Build(Coordinate start, Coordinate end){
        super("build",start, end);

    }
    
    @Override
    public boolean equals(Object that) {
        if(!(that instanceof Build)) return false;
        return(this.getStart().equals(((Build) that).getStart()) &&
                this.getEnd().equals(((Build) that).getEnd()) && this.getActionName().equalsIgnoreCase(((Build) that).getActionName()));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
