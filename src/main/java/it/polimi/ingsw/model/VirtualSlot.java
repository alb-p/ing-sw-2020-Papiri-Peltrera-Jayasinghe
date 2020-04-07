package it.polimi.ingsw.model;

public class VirtualSlot {


    private String content;
    private Coordinate coord;

    public VirtualSlot(Slot s, Coordinate c) {

        this.content = s.toString();
        this.coord = c;
    }


    public VirtualSlot() {
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

        if (that instanceof VirtualSlot)
            return this.content.equals(((VirtualSlot) that).content);

        return false;
    }
}
