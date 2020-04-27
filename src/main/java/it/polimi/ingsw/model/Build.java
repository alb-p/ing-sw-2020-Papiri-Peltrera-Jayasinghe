package it.polimi.ingsw.model;

public class Build extends Action {

    public Build(Coordinate start, Coordinate end){
        super(start, end);
    }
    
    @Override
    public boolean equals(Object that) {
        if(!(that instanceof Build)) return false;
        return(this.getStart().equals(((Build) that).getStart()) &&
                this.getEnd().equals(((Build) that).getEnd()));
    }
}
