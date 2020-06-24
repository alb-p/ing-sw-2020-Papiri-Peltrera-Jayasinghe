package it.polimi.ingsw.utils;

import java.io.Serializable;

/**
 * The enum Actions enum.
 */
public enum ActionsEnum implements Serializable {

    MOVE("MOVE"),
    BUILD("BUILD"),
    BOTH("BOTH");

    private String name;

    /**
     * Instantiates a new Actions enum.
     *
     * @param name the name
     */
    private ActionsEnum(String name){

        this.name = name();
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    public String getName(){
        return this.name;
    }
}
