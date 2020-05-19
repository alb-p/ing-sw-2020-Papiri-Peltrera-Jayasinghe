package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class PingMessage implements Message, Serializable {

    private int id;

    public PingMessage() {
    }
    public PingMessage(int id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "PingMessage";
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
