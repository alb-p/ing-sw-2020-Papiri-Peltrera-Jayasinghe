package it.polimi.ingsw.model;

import java.io.Serializable;

public class VirtualSlot implements Serializable {


    private String content;
    private int height = 0;
    private Coordinate coordinate;

    public VirtualSlot(Slot s, Coordinate c) {
        this.content = s.toString();
        this.coordinate = c;
        this.height =  s.getConstructionLevel();
    }
    public VirtualSlot(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.content = "  ";
    }

    public VirtualSlot() {
        this.coordinate = null;
        this.content = "  ";
    }

    @Override
    public String toString() {
        return this.content;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }


    @Override
    public boolean equals(Object that) {

        if (that instanceof VirtualSlot)
            return this.content.equals(((VirtualSlot) that).content);

        return false;
    }

    public int getHeight() {
        return height;
    }
}
