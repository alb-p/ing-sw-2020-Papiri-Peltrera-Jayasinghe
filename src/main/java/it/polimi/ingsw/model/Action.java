package it.polimi.ingsw.model;

import java.util.Objects;

public abstract class Action {

    private Coordinate start;
    private Coordinate end;

    public Action(Coordinate start, Coordinate end){
        this.start = start;
        this.end = end;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }
    public void setEnd(Coordinate end){
        this.end = end;
    }

    public Coordinate getStart(){
        return this.start;
    }
    public Coordinate getEnd(){
        return this.end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Objects.equals(start, action.start) &&
                Objects.equals(end, action.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
