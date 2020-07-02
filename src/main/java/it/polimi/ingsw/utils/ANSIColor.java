package it.polimi.ingsw.utils;

/**
 * The enum Ansi color.
 */
public enum ANSIColor {
    BLACK ("\u001B[30m"),
    RED ("\u001B[31m"),
    GREEN ("\u001B[32m"),
    YELLOW ("\u001B[33m"),
    BLUE ("\u001B[34m"),
    MAGENTA ("\u001B[35m"),
    CYAN ("\u001B[36m"),
    WHITE ("\u001B[37m"),
    BACK_BLACK ("\u001B[40m"),
    BACK_RED ("\u001B[41m"),
    BACK_GREEN ("\u001B[42m"),
    BACK_YELLOW ("\u001B[43m"),
    BACK_BLUE ("\u001B[44m"),
    BACK_MAGENTA ("\u001B[45m"),
    BACK_CYAN ("\u001B[46m"),
    BACK_WHITE ("\u001B[47m"),
    BOLD("\u001B[1m"),
    UNDERLINE("\u001B[4m"),
    REVERSED("\u001B[7m"),
    RESET("\u001B[0m");

    private final String escape;

    /**
     * Instantiates a new Ansi color.
     * If OS is Windows it will not use escapes.
     *
     * @param escape the escape
     */
    ANSIColor(String escape) {
        if(System.getProperty("os.name").startsWith("Windows")){
            this.escape = "";
        }
        else{
            this.escape = escape;
        }

    }


    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        return escape;
    }

}
