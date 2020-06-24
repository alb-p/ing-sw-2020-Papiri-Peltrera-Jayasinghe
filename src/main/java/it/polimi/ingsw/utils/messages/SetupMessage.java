package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

/**
 * The type Setup message.
 */
public class SetupMessage implements Message, Serializable {

    private static final long serialVersionUID = -7411184900368970103L;
    private String message = "How many players for the game?";
    private int field = 0;


    /**
     * Gets message.
     *
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Sets id.
     *
     * @param i the
     */
    @Override
    public void setId(int i) {

    }

    /**
     * Sets field.
     *
     * @param field the field
     */
    public void setField(int field) {
        this.field = field;
    }

    /**
     * Get field int.
     *
     * @return the int
     */
    public int getField(){
        return field;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Override
    public int getId() {
        return 0;
    }
}
