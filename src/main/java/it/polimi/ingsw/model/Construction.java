package it.polimi.ingsw.model;

/**
 * The enum Construction.
 * Represent the buildings on the board
 */
public enum Construction {
    FLOOR("F"),
    DOME("D");

    private final String symbol;

    /**
     * Instantiates a new Construction.
     *
     * @param symbol the symbol
     */
    Construction(String symbol){
        this.symbol=symbol;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString(){return this.symbol;}
}
