package it.polimi.ingsw.model;

import java.io.Serializable;

public class FirstBuild extends Build implements Serializable {

    public FirstBuild(Coordinate start, Coordinate end) {
        super( start, end);
    }


    @Override
    public String getActionName() {
        return "build";
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
