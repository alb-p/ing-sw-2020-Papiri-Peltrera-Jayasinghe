package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ANSIColor;

import java.io.IOException;

/**
 * The type Slot represent a box
 * on the board.
 */
public class Slot {

    private Construction buildings[] = new Construction[4];
    private boolean free;
    private Worker worker;

    /**
     * Instantiates a new Slot.
     */
    public Slot() {
        this.free = true;
        this.worker = null;
    }

    /**
     * Return true if the slot has not a
     * player or a dome in itself.
     *
     * @return the boolean
     */
    public boolean isFree() {
        return free;
    }

    /**
     * Construct.
     *
     * @param c the c
     * @throws Exception the exception
     */
    public boolean construct(Construction c){
        for (int i = 0; i < buildings.length; i++) {
            if (buildings[i] == null && this.isFree() && !(c.equals(Construction.DOME))) {
                if (i == 0 || !(buildings[i - 1].equals(Construction.DOME))) {
                    buildings[i] = c;
                    return true;
                }
            }
            if (buildings[i] == null && c.equals(Construction.DOME) && this.isFree()) {
                this.free = false;
                buildings[i] = c;
                return true;

            }
        }
        return false;
    }

    /**
     * Get construction level
     *
     * @return the construction level
     */
    public int getConstructionLevel() {
        int level = 0;
        for (int i = 0; i < buildings.length && buildings[i] != null; i++) {
            if (buildings[i].equals(Construction.FLOOR)) level++;
        }
        return level;
    }

    /**
     * Has a dome boolean.
     *
     * @return the boolean
     */
    public boolean hasADome() {
        for (int i = 0; i < buildings.length && buildings[i] != null; i++) {
            if (buildings[i].equals(Construction.DOME)) return true;
        }
        return false;
    }

    /**
     * A worker occupies the slot.
     *
     * @param w the w
     */
    public void occupy(Worker w) {
        if (this.isFree()) {
            this.worker = w;
        } else {
            return;
        }
        this.free = false;
    }

    /**
     * Worker on slot boolean.
     *
     * @return the boolean
     */
    public boolean workerOnSlot() {
        if (worker == null) return false;
        return true;
    }

    /**
     * Frees the slot;
     */
    public void free() {
        this.worker = null;
        this.free = true;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    public String toString() {
        String color, floor;
        if (this.worker == null) color = " ";
        else color = this.worker.toString();
        if (hasADome()) floor = "D";
        else if (this.worker == null && getConstructionLevel() == 0) {
            floor = " ";
        } else {
            floor = String.valueOf(getConstructionLevel());
        }
        return (color + floor + ANSIColor.RESET);
    }

    /**
     * Gets worker.
     *
     * @return the worker in the slot.
     */
    public Worker getWorker() {
        return this.worker;
    }


}