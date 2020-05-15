package it.polimi.ingsw.model;


public class Worker {

    private Color color;
    private Coordinate[] coord;


    public Worker(int row, int col, String color) {
        this.coord = new Coordinate[3];
        this.coord[0] = new Coordinate(row, col);
        this.coord[1] = new Coordinate(row, col);
        this.coord[2] = new Coordinate(row, col);
        this.color = Enum.valueOf(Color.class, color.toUpperCase());
    }

    public Worker(Coordinate coordinate, String color) {
        this.coord = new Coordinate[3];
        this.coord[0] = coordinate;
        this.coord[1] = coordinate;
        this.coord[2] = coordinate;
        this.color = Enum.valueOf(Color.class, color.toUpperCase());
    }

    public Worker(Coordinate coordinate, Color color) {
        this.coord = new Coordinate[3];
        this.coord[0] = coordinate;
        this.coord[1] = coordinate;
        this.coord[2] = coordinate;
        this.color = color;
    }


    public Coordinate getPosition() {
        return this.coord[0];
    }

    public Coordinate getOldPosition() {
        return this.coord[1];
    }

    public void setPosition(Coordinate coordinate) {
        this.coord[2] = this.coord[1];
        this.coord[1] = this.coord[0];
        this.coord[0] = coordinate;
    }

    public String toString() {
        return this.color.toString();
    }

    public Color getColor() {
        return this.color;
    }

}

