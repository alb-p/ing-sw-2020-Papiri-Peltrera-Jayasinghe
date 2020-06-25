package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Coordinate.
 * Represents the position of a slot on the board
 */
public class Coordinate implements Serializable {
    private int row;
    private int col;

    /**
     * Instantiates a new Coordinate.
     *
     * @param row the row
     * @param col the column
     */
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Instantiates a new Coordinate.
     */
    public Coordinate() {
        this.row = -1;
        this.col = -1;
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets col.
     *
     * @return the col
     */
    public int getCol() {
        return this.col;
    }


    /**
     * Is adjacent boolean.
     *
     * @param that the that
     * @return true if two coordinates are adjacent
     */
    public boolean isAdjacent(Coordinate that) {
        return (this.getCol() == that.getCol() || this.getCol() == that.getCol() + 1 || this.getCol() == that.getCol() - 1)
                && (this.getRow() == that.getRow() || this.getRow() == that.getRow() + 1 || this.getRow() == that.getRow() - 1)
                && (!(this.getCol() == that.getCol() && this.getRow() == that.getRow()));
    }

    /**
     * To string string.
     *
     * @return the string
     */
    public String toString() {
        return "\nRow :" + this.row + "\nCol :" + this.col;
    }


    /**
     * Equals boolean.
     *
     * @param that the that
     * @return the boolean
     */
    public boolean equals(Coordinate that) {
        if (this.getCol() == that.getCol() && this.getRow() == that.getRow()) {
            return true;
        }
        return false;

    }


    /**
     * Gets the list of the adjacent
     * coords.
     *
     * @return the adjacent coords
     */
    public ArrayList<Coordinate> getAdjacentCoords() {        //it returns a list of adjacent coordinates
        ArrayList<Coordinate> list = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (this.getCol() + i < 5 && this.getCol() + i >= 0
                        && this.getRow() + j < 5 && this.getRow() + j >= 0
                        && (i != 0 || j != 0)){
                    list.add(new Coordinate(this.getRow() + j, this.getCol() + i));

                }
            }
        }

        return list;
    }


    /**
     * Is valid boolean (values 0-4).
     *
     * @return the boolean
     */
    public boolean isValid() {
        if (((this.getRow() < 0 || this.getRow() > 4) || (this.getCol() < 0 || this.getCol() > 4))) {
            return false;
        }
        return true;
    }
}