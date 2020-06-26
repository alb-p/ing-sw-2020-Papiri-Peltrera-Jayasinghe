package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

/**
 * The type Nickname message is a message
 * that vehicles the nickname information.
 */
public class NicknameMessage implements Message , Serializable {

    private static final long serialVersionUID = -7571629895902903270L;
    private String message;
    private String nickname;
    private int id;

    /**
     * Instantiates a new Nickname message.
     */
    public NicknameMessage() {
        this.message = "Choose a nickname:";
        this.nickname = null;

    }

    /**
     * Instantiates a new Nickname message.
     *
     * @param id the id
     */
    public NicknameMessage(int id) {
        this.message = "Choose a nickname:";
        this.nickname = null;
        this.id = id;
    }

    /**
     * Instantiates a new Nickname message.
     *
     * @param id       the id
     * @param nickname the nickname
     */
    public NicknameMessage(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.message = "default";
    }

    /**
     * Sets nickname.
     *
     * @param nickname the nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Gets nickname.
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
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

    /**
     * Set message.
     *
     * @param message the message
     */
    public void setMessage(String message){
        this.message = message;
    }
}
