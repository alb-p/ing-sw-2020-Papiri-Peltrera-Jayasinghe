package it.polimi.ingsw.utils.messages;


import java.io.Serializable;

public class GodMessage implements Message, Serializable {

    private static final long serialVersionUID = 1648566943652144363L;
    String message;
    String god;
    int id;



    public void setGod(String god) {
        this.god = god;
    }

    public String getGod() {
        return god;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void setId(int i) {
        this.id=i;
    }

    @Override
    public int getId() {
        return this.id;
    }

}
