package it.polimi.ingsw.utils;

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
    BOLD("\u001b[1m"),
    UNDERLINE("\u001b[4m"),
    REVERSED("\u001b[7m");


    public static final String RESET = "\u001B[0m";
    private String escape;

    ANSIColor(String escape)
    {
        this.escape = escape;
    }

    public String getEscape()
    {
        return escape;
    }

    @Override
    public String toString()
    {
        return escape;
    }

}
