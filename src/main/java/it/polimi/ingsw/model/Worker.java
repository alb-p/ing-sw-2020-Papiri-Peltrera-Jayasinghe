package it.polimi.ingsw.model;


/**
 * The type Worker.
 */
public class Worker {

    private Color color;
    private Coordinate[] coord;


    /**
     * Instantiates a new Worker.
     *
     * @param row   the row
     * @param col   the col
     * @param color the color
     */
    public Worker(int row, int col, String color) {
        this.coord = new Coordinate[3];
        this.coord[0] = new Coordinate(row, col);
        this.coord[1] = new Coordinate(row, col);
        this.coord[2] = new Coordinate(row, col);
        this.color = Enum.valueOf(Color.class, color.toUpperCase());
    }

    /**
     * Instantiates a new Worker.
     *
     * @param coordinate the coordinate
     * @param color      the color
     */
    public Worker(Coordinate coordinate, String color) {
        this.coord = new Coordinate[3];
        this.coord[0] = coordinate;
        this.coord[1] = coordinate;
        this.coord[2] = coordinate;
        this.color = Enum.valueOf(Color.class, color.toUpperCase());
    }

    /**
     * Instantiates a new Worker.
     *
     * @param coordinate the coordinate
     * @param color      the color
     */
    public Worker(Coordinate coordinate, Color color) {
        this.coord = new Coordinate[3];
        this.coord[0] = coordinate;
        this.coord[1] = coordinate;
        this.coord[2] = coordinate;
        this.color = color;
    }


    /**
     * Gets position.
     *
     * @return the position
     */
    public Coordinate getPosition() {
        return this.coord[0];
    }

    /**
     * Gets old position.
     *
     * @return the old position
     */
    public Coordinate getOldPosition() {
        return this.coord[1];
    }

    /**
     * Sets position.
     *
     * @param coordinate the coordinate
     */
    public void setPosition(Coordinate coordinate) {
        this.coord[2] = this.coord[1];
        this.coord[1] = this.coord[0];
        this.coord[0] = coordinate;
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

