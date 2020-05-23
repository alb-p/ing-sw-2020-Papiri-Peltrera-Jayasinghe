package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ANSIColor;

import java.io.Serializable;

public class VirtualSlot implements Serializable {


    private final Color color;
    private int level = 0;
    private final boolean dome;
    private final Coordinate coordinate;

    public VirtualSlot(Color color, int level, boolean dome, Coordinate c) {
        this.color = color;
        this.level = level;
        this.dome = dome;
        this.coordinate = c;
    }

    public VirtualSlot(Coordinate c){
        this.color = null;
        this.level = 0;
        this.dome = false;
        this.coordinate = c;
    }


    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public int getLevel() {
        return level;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasWorker() {
        return color != null;
    }

    public boolean isFree(){
        return color == null && !dome;
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof VirtualSlot)
            return this.toString().equals(that.toString());
        return false;
    }



    @Override
    public String toString() {
        String colorString;
        String floor;
        if (!hasWorker()) {
            colorString = " ";
        } else {
            colorString = color.toString();
        }
        if (dome) {
            floor = "D";
        } else if (!hasWorker() && level == 0) {
            floor = " ";
        } else {
            floor = String.valueOf(level);
        }
        return (colorString + floor + ANSIColor.RESET);
    }
}
