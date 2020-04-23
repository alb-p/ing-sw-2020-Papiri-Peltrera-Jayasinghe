package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class StartGameMessage implements Message, Serializable {
    @Override
    public String getMessage() {
        return "The game is ready to START!!";
    }

    @Override
    public void setId(int i) {
    }

    @Override
    public int getId() {
        return -1;
    }
}
