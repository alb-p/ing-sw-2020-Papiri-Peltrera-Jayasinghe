package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

/**
 * The type Godly message is a message
 * that vehicles the godly id information.
 */
public class GodlyMessage implements Message, Serializable {

    private static final long serialVersionUID = -9126000827412025725L;
    private int id;

    /**
     * Instantiates a new Godly message.
     *
     * @param id the id of the godly player
     */
    public GodlyMessage(int id){
        this.id=id;
    }

    /**
     *
     * @return null
     */
    @Override
    public String getMessage() {
        return null;
    }

    /**
     * Sets id.
     *
     * @param id the id of the godly player
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id of the godly player
     */
    @Override
    public int getId() {
        return this.id;
    }
}
