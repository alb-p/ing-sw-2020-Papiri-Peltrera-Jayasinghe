package it.polimi.ingsw.utils.messages;

public class WinnerMessage implements Message {

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
