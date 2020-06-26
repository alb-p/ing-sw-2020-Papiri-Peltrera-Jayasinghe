package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.SocketClientConnection;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Color message is a message
 * that vehicles color information.
 */
public class ColorMessage implements Message, Serializable {

    private static final long serialVersionUID = -4150974628324626570L;
    private Color color;
    private int id;


    /**
     * Instantiates a new Color message.
     *
     * @param id the id
     */
    public ColorMessage(int id){
        this.id = id;
    }


    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @return null
     */
    public String getMessage() {
        return null;
    }

    /**
     * Sets id.
     *
     * @param i the id
     */
    @Override
    public void setId(int i) {
        this.id = i;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Override
    public int getId() {
        return this.id;
    }

}
