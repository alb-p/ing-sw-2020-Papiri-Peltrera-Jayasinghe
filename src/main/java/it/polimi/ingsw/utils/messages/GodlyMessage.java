package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

/**
 * The type Godly message.
 */
public class GodlyMessage implements Message, Serializable {

    private static final long serialVersionUID = -9126000827412025725L;
    private int id;

    /**
     * Instantiates a new Godly message.
     *
     * @param id the id
     */
    public GodlyMessage(int id){
        this.id=id;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    @Override
    public String getMessage() {
        return null;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    @Override
    public void setId(int id) {
        this.id = id;
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
