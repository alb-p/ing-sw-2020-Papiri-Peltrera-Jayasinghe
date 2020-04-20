package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.network.SocketClientConnection;

public class GodMessage implements Message{

    String message;
    String god;
    int id;

    public GodMessage(){
        this.message = "Inserisci divinità";
        this.god=null;
    }

    public GodMessage(int i){
        this.message = "Inserisci divinità";
        this.god=null;
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
