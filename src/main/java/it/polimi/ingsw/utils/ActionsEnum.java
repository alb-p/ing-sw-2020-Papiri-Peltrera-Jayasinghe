package it.polimi.ingsw.utils;

public enum ActionsEnum {

    MOVE("MOVE"), BUILD("BUILD"), BOTH("BOTH");

    private String name;

    private ActionsEnum(String name){

        this.name = name();
    }

    public String getName(){
        return this.name;
    }
}
