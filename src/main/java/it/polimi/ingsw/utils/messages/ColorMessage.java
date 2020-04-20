package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Color;

import java.io.Serializable;

public class ColorMessage implements Message, Serializable {

    private String message = "Inserisci il colore";
    private String color;


    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public String getMessage() {
        return this.message;
    }

}
