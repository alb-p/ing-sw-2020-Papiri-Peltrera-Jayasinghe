package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ANSIColor;

public enum Color{
    GRAY(ANSIColor.BACK_RED.toString()+ANSIColor.WHITE.toString()+ANSIColor.BOLD+"G", "GRAY"),
    WHITE(ANSIColor.BACK_WHITE.toString()+ANSIColor.BLACK.toString()+ANSIColor.BOLD+"W", "WHITE"),
    BLUE(ANSIColor.BACK_BLUE.toString()+ANSIColor.WHITE.toString()+ANSIColor.BOLD+"B", "BLUE");

    private String symbol;
    private String name;

    private Color(String symbol, String name){
        this.symbol=symbol;

        this.name = name();
    }

    public String toString(){
        return (this.symbol);
    }
    public String getName(){
        return this.name;
    }
}