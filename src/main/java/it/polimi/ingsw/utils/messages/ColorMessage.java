package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.SocketClientConnection;

import java.io.Serializable;
import java.util.ArrayList;

public class ColorMessage implements Message, Serializable {

    private String message;
    private ArrayList<Color> colors = new ArrayList<>();
    private Color color;
    private int id;



    public ColorMessage(int i, ArrayList<Color> colors) {
        this.message = "Inserisci il colore ";
        this.colors.addAll(colors);
        id = i;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public ArrayList<Color> getColors() {
        return colors;
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
