package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.network.SocketClientConnection;

public class GodMessage implements Message{

    String message;
    String god;
    private SocketClientConnection scc;

    public GodMessage(){
        this.message = "Inserisci divinità";
        this.god=null;
        this.scc=null;
    }

    public GodMessage(SocketClientConnection socket){
        this.message = "Inserisci divinità";
        this.god=null;
        this.scc=socket;
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

}
