package it.polimi.ingsw.model;

import java.io.Serializable;

public abstract class Action implements Serializable {

    private static final long serialVersionUID = 7260812342692728917L;
    private Coordinate start;
    private final String actionName;
    private Coordinate end;

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

    public abstract boolean equals(Object o);

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
