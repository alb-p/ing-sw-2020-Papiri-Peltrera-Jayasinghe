package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class WelcomeMessage implements Message, Serializable {

    String message;


    public WelcomeMessage(String s){
        this.message=s;

    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setId(int i) {

    }

    @Override
    public int getId() {
        return -1;
    }

}
