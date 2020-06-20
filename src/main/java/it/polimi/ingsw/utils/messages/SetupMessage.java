package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class SetupMessage implements Message, Serializable {

    private static final long serialVersionUID = -7411184900368970103L;
    private String message = "How many players for the game?";
    private int field = 0;


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setId(int i) {

    }

    public void setField(int field) {
        this.field = field;
    }
    public int getField(){
        return field;
    }

    @Override
    public int getId() {
        return 0;
    }
}
