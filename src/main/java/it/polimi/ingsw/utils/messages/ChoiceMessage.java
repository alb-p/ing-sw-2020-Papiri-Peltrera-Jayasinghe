package it.polimi.ingsw.utils.messages;

import java.io.Serializable;
import java.util.ArrayList;

public class ChoiceMessage implements Message, Serializable {

    private String message; //it contains player choice
    private String nickname;
    private int id;
    private ArrayList<String> choices;
/*
    public ChoiceMessage(ActionMessage message){
        this.choices = message.getChoices();
        this.id = message.getId();
        this.nickname = message.getNickname();
    }
*/
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public ArrayList<String> getChoices() {
        return this.choices;
    }

    public String getNickname() {
        return nickname;
    }
}
