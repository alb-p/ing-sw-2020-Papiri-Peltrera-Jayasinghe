package it.polimi.ingsw.utils.messages;

public class StartGameMessage implements Message {
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
