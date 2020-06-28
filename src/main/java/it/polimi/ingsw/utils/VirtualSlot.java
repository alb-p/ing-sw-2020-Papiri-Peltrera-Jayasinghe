package it.polimi.ingsw.utils;

import java.io.Serializable;

/**
 * The type Virtual slot represent a slot for the view
 */
public class VirtualSlot implements Serializable {

    private static final long serialVersionUID = -2883461245726706010L;
    private final Color color;
    private int level = 0;
    private final boolean dome;
    private final Coordinate coordinate;

    /**
     * Instantiates a new Virtual slot.
     *
     * @param color the color
     * @param level the level
     * @param dome  the dome
     * @param c     the coordinate
     */
    public VirtualSlot(Color color, int level, boolean dome, Coordinate c) {
        this.color = color;
        this.level = level;
        this.dome = dome;
        this.coordinate = c;
    }

    /**
     * Instantiates a new Virtual slot.
     *
     * @param c the c
     */
    public VirtualSlot(Coordinate c){
        this.color = null;
        this.level = 0;
        this.dome = false;
        this.coordinate = c;
    }


    /**
     * Gets coordinate.
     *
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets color.
     *
     * @return the color of the worker
     */
    public Color getColor() {
        return color;
    }

    /**
     * Has worker boolean.
     *
     * @return true if a worker occupies the vslot
     */
    public boolean hasWorker() {
        return color != null;
    }

    /**
     * Is free boolean.
     *
     * @return true if there's no worker neither a dome
     */
    public boolean isFree(){
        return color == null && !dome;
    }

    /**
     * Has dome boolean.
     *
     * @return true if the vslot has a dome
     */
    public boolean hasDome(){
        return dome;
    }

    /**
     * Equals boolean.
     *
     * @param that the that
     * @return the boolean
     */
    @Override
    public boolean equals(Object that) {
        if (that instanceof VirtualSlot)
            return this.toString().equals(that.toString());
        return false;
    }


    /**
     * To string string.
     *
     * @return the string
     */
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
