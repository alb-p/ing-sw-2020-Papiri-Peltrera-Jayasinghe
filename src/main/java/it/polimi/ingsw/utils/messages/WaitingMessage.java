package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class WaitingMessage implements Message, Serializable {

    private  String message;

    public WaitingMessage(String playing){
        this.message = playing+" is playing... please wait your turn.";
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
        return 0;
    }
}
