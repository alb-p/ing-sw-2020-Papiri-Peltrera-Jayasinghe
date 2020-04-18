package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Color;

import java.io.Serializable;

public class ColorMessage implements Message, Serializable {

    private String message ;
    private Color color;

    public ColorMessage(String message, Color color){
        this.message = message;
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }

    public String getMessage(){
        return this.message;
    }

}
