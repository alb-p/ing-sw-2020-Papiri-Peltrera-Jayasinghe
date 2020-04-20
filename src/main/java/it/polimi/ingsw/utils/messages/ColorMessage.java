package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.SocketClientConnection;

import java.io.Serializable;
import java.util.ArrayList;

public class ColorMessage implements Message, Serializable {

    private String message;
    private String color;
    private int id;

    public ColorMessage(){
        this.message = "Inserisci il colore";
        this.color=null;

    }

    public ColorMessage(int i,ArrayList<String> colors){
        this.message = "Inserisci il colore "+color;
        this.color=null;
        id=i;
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

    @Override
    public void setId(int i) {
        this.id=i;
    }

    @Override
    public int getId() {
        return this.id;
    }

}
