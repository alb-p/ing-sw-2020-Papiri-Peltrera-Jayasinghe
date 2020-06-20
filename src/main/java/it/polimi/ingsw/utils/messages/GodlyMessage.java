package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class GodlyMessage implements Message, Serializable {

    private static final long serialVersionUID = -9126000827412025725L;
    private int id;

    public GodlyMessage(int id){
        this.id=id;
    }

    @Override
    public String getMessage() {
        return null;
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
