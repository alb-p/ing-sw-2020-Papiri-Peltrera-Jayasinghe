package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.network.SocketClientConnection;

import java.io.Serializable;
import java.util.ArrayList;

public class GodMessage implements Message, Serializable {

    String message;
    String god;
    int id;

    public GodMessage(int i,String god){
        this.message = "god selected from id "+i+": "+god;
        this.god=god;
        this.id=i;
    }


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
