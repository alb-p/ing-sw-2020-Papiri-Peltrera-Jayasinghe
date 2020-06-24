package it.polimi.ingsw.utils.messages;


import java.io.Serializable;

/**
 * The type God message.
 */
public class GodMessage implements Message, Serializable {

    private static final long serialVersionUID = 1648566943652144363L;
    String message;
    String god;
    int id;


    /**
     * Sets god.
     *
     * @param god the god
     */
    public void setGod(String god) {
        this.god = god;
    }

    /**
     * Gets god.
     *
     * @return the god
     */
    public String getGod() {
        return god;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets id.
     *
     * @param i the
     */
    @Override
    public void setId(int i) {
        this.id=i;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Override
    public int getId() {
        return this.id;
    }

}
