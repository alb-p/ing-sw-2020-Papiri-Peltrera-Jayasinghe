package it.polimi.ingsw.model;

import java.io.Serializable;

public class FirstBuild extends Action implements Serializable {

    private static final long serialVersionUID = 8864784887990391217L;

    public FirstBuild(Coordinate start, Coordinate end) {
        super("build", start, end);
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
    public int hashCode() {
        return super.hashCode();
    }
}
