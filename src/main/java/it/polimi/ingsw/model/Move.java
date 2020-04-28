package it.polimi.ingsw.model;

import java.io.Serializable;

public class Move extends Action implements Serializable {

    public Move(Coordinate start, Coordinate end){
        super(start, end);
    }

    @Override
    public boolean equals(Object that) {
        if(!(that instanceof Move)) return false;
        return(this.getStart().equals(((Move) that).getStart()) &&
                this.getEnd().equals(((Move) that).getEnd()));
    }
}

