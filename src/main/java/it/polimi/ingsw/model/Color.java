package it.polimi.ingsw.model;

public enum Color{
    GRAY("G", "GRAY"), WHITE("W", "WHITE"), BLUE("B", "BLUE");

    private String symbol;
    private String name;

    private Color(String symbol, String name){
        this.symbol=symbol;

        this.name = name();
    }

    public String toString(){
        return this.symbol;
    }
    public String getName(){
        return this.name;
    }
}