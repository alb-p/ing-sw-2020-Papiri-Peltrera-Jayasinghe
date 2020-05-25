package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ANSIColor;

public enum Color{
    RED("R", "RED", ANSIColor.BACK_RED.toString()+ANSIColor.BLACK.toString()+ANSIColor.BOLD),
    WHITE("W", "WHITE", ANSIColor.BACK_WHITE.toString()+ANSIColor.BLACK.toString()+ANSIColor.BOLD),
    BLUE("B", "BLUE", ANSIColor.BACK_BLUE.toString()+ANSIColor.BLACK.toString()+ANSIColor.BOLD);

    private String symbol;
    private String name;

    private String solidColor;

    private Color(String symbol, String name, String solidColor){
        this.symbol=symbol;
        this.name = name();
        this.solidColor = solidColor;
    }

    public String toString(){
        return (solidColor + symbol);
    }
    public String getName(){
        return this.name;
    }

    public String getSolidColor() {
        return solidColor;
    }

    public String colorizedText(String text){
        return (this.solidColor + text + ANSIColor.RESET);
    }

}