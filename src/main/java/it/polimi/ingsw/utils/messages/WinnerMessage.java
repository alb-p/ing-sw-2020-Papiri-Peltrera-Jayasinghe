package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

/**
 * The type Winner message is a message
 * that vehicles  the infotmation of
 * the winner player.
 */
public class WinnerMessage implements Message, Serializable {

    private static final long serialVersionUID = 7815827983162006979L;
    private int id;
    private final String message;

    /**
     * Instantiates a new Winner message.
     *
     * @param id       the id
     * @param nickname the nickname
     */
    public WinnerMessage(int id, String nickname){
        this.id = id;
        this.message = nickname;
    }

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
        return id;
    }
}
