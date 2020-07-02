package it.polimi.ingsw.model;


import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;

/**
 * The type Worker.
 */
public class Worker {

    private final Color color;
    private final Coordinate[] coordinates;


    /**
     * Instantiates a new Worker.
     *
     * @param row   the row
     * @param col   the col
     * @param color the color
     */
    public Worker(int row, int col, String color) {
        this.coordinates = new Coordinate[3];
        this.coordinates[0] = new Coordinate(row, col);
        this.coordinates[1] = new Coordinate(row, col);
        this.coordinates[2] = new Coordinate(row, col);
        this.color = Enum.valueOf(Color.class, color.toUpperCase());
    }

    /**
     * Instantiates a new Worker.
     *
     * @param coordinate the coordinate
     * @param color      the color
     */
    public Worker(Coordinate coordinate, String color) {
        this.coordinates = new Coordinate[3];
        this.coordinates[0] = coordinate;
        this.coordinates[1] = coordinate;
        this.coordinates[2] = coordinate;
        this.color = Enum.valueOf(Color.class, color.toUpperCase());
    }

    /**
     * Instantiates a new Worker.
     *
     * @param coordinate the coordinate
     * @param color      the color
     */
    public Worker(Coordinate coordinate, Color color) {
        this.coordinates = new Coordinate[3];
        this.coordinates[0] = coordinate;
        this.coordinates[1] = coordinate;
        this.coordinates[2] = coordinate;
        this.color = color;
    }


    /**
     * Gets position.
     *
     * @return the position
     */
    public Coordinate getPosition() {
        return this.coordinates[0];
    }

    /**
     * Gets old position.
     *
     * @return the old position
     */
    public Coordinate getOldPosition() {
        return this.coordinates[1];
    }

    /**
     * Sets position.
     *
     * @param coordinate the coordinate
     */
    public void setPosition(Coordinate coordinate) {
        this.coordinates[2] = this.coordinates[1];
        this.coordinates[1] = this.coordinates[0];
        this.coordinates[0] = coordinate;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    public String toString() {
        return this.color.toString();
    }

    /**
     * Gets the worker's color.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

}

