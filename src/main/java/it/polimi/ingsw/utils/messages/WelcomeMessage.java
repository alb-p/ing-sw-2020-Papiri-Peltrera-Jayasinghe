package it.polimi.ingsw.utils.messages;

public class WelcomeMessage implements Message {

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
