package it.polimi.ingsw.model;

import it.polimi.ingsw.view.View;

public class ViewSlot {


    private String content;
    private Coordinate coord;

    public ViewSlot(Slot s, Coordinate c) {

        this.content = s.toString();
        this.coord = c;
    }


    public ViewSlot() {
        this.coord = null;
        this.content = "  ";
    }

    @Override
    public String toString() {
        return this.content;
    }

    public Coordinate getCoord() {
        return this.coord;
    }


    @Override
    public boolean equals(Object that) {

        if (that instanceof ViewSlot)
            return this.content.equals(((ViewSlot) that).content);

        return false;
    }
}
