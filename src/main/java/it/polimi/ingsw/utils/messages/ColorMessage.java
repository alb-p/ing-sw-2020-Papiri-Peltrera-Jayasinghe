package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.SocketClientConnection;

import java.io.Serializable;
import java.util.ArrayList;

public class ColorMessage implements Message, Serializable {

    private String message;
    private String color;
    private SocketClientConnection scc;

    public ColorMessage(){
        this.message = "Inserisci il colore";
        this.color=null;
        this.scc=null;
    }

    public ColorMessage(SocketClientConnection socket,ArrayList<String> colors){
        this.message = "Inserisci il colore "+color;
        this.color=null;
        this.scc=socket;
    }


    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public String getMessage() {
        return this.message;
    }

    public SocketClientConnection getScc() {
        return scc;
    }

}
