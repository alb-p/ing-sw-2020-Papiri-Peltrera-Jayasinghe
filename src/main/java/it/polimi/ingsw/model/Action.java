package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Action implements Serializable {

    private Coordinate start;
    private String actionName;
    private Coordinate end;
    private boolean option = false; //da usare come yesno

    public Action(String actionName, Coordinate start, Coordinate end){
        this.start = start;
        this.end = end;
        this.actionName = actionName;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }
    public void setEnd(Coordinate end){
        this.end = end;
    }
    public String getActionName(){return this.actionName;}
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

    public boolean isOption() {
        return option;
    }

    public void setOption(boolean option) {
        this.option = option;
    }

    public abstract Action clone();
}
