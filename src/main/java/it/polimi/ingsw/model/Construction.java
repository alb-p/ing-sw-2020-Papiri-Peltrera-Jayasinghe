package it.polimi.ingsw.model;

public enum Construction {
    FLOOR("F"), DOME("D");

    private String symbol;

    private Construction(String symbol){
        this.symbol=symbol;
    }

    public String toString(){return this.symbol;}
}
