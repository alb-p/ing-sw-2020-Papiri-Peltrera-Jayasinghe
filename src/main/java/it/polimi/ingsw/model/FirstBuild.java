package it.polimi.ingsw.model;

import java.io.Serializable;

public class FirstBuild extends Action implements Serializable {

    public FirstBuild(Coordinate start, Coordinate end) {
        super("First build", start, end);
    }


    @Override
    public boolean equals(Object o) {
        if(!(o instanceof FirstBuild)) return false;
        return(this.getStart().equals(((FirstBuild) o).getStart()) &&
                this.getEnd().equals(((FirstBuild) o).getEnd()));
    }

    @Override
    public Action clone() {
        return new FirstBuild(null, null);
    }

}
