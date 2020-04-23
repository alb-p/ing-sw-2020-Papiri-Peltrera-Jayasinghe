package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class WinnerMessage implements Message, Serializable {

    private int id;
    private String message;
    public WinnerMessage(int id, String nickname){
        this.id = id;
        this.message = nickname+ " has won!";
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setId(int i) {

    }

    @Override
    public int getId() {
        return id;
    }
}
