package it.polimi.ingsw.utils;

import java.io.Serializable;

public enum ActionsEnum implements Serializable {

    MOVE("MOVE"), BUILD("BUILD"), BOTH("BOTH");

    private String name;

    private ActionsEnum(String name){

        this.name = name();
    }

    public String getName(){
        return this.name;
    }
}
