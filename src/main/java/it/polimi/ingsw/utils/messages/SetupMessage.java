package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

/**
 * The type Setup message is used to vehicle
 * the request of how many players will
 * take part to the game.
 */
public class SetupMessage implements Message, Serializable {

    private static final long serialVersionUID = -7411184900368970103L;
    private String message = "How many players for the game?";
    private int field = 0;
    private int id;


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
     * Never used because players don't already exist
     *
     * @param id the id
     */
    @Override
    public void setId(int id) {
        this.id  = id;
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
