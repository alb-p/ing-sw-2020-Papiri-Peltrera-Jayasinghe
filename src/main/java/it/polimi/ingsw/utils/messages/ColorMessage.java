package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.SocketClientConnection;

import java.io.Serializable;
import java.util.ArrayList;

public class ColorMessage implements Message, Serializable {

    private String message;
    private Color color;
    private int id;


    public ColorMessage(int id){
        this.id = id;
    }



    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void setId(int i) {
        this.id = i;
    }

    @Override
    public int getId() {
        return this.id;
    }

}
