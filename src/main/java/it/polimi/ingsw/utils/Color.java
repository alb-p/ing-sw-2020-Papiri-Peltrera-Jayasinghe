package it.polimi.ingsw.utils;


/**
 * The enum Color.
 */
public enum Color{
    RED("R", "RED", ANSIColor.BACK_RED.toString()+ANSIColor.BLACK.toString()+ANSIColor.BOLD),
    WHITE("W", "WHITE", ANSIColor.BACK_WHITE.toString()+ANSIColor.BLACK.toString()+ANSIColor.BOLD),
    BLUE("B", "BLUE", ANSIColor.BACK_BLUE.toString()+ANSIColor.WHITE.toString()+ANSIColor.BOLD);

    private final String symbol;
    private final String name;

    private final String solidColor;

    /**
     * Instantiates a new Color.
     *
     * @param symbol     the symbol
     * @param name       the name
     * @param solidColor the solid color
     */
    Color(String symbol, String name, String solidColor){
        this.symbol=symbol;
        this.name = name;
        this.solidColor = solidColor;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString(){
        return (solidColor + symbol);
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    public String getName(){
        return this.name;
    }


    /**
     * Colorized text string.
     *
     * @param text the text
     * @return the string painted
     */
    public String colorizedText(String text){
        return (this.solidColor + text + ANSIColor.RESET);
    }

}