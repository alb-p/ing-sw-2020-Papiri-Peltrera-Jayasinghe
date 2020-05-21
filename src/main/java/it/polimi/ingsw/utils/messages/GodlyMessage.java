package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class GodlyMessage implements Message, Serializable {

    private String message;
    private int id;

    public GodlyMessage(int i){
        this.id=i;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return this.id;
    }
}
