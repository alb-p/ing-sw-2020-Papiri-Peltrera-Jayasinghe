package it.polimi.ingsw.utils.messages;

/**
 * The interface Message represents how
 * information needs to be transferred
 * between client and server.
 */
public interface Message{

    /**
     * Gets message.
     *
     * @return the message
     */
    String getMessage();

    /**
     * Sets id.
     *
     * @param id the id
     */
    void setId(int id);

    /**
     * Gets id.
     *
     * @return the id
     */
    int getId();
}
