package it.polimi.ingsw.model;

public class Coordinate{
    private int row;
    private int col;

    public Coordinate(int row, int col){
        this.row = row;
        this.col = col;
    }
    public Coordinate(){
        this.row=-1;
        this.col=-1;
    }

    public int getRow(){
        return this.row;
    }
    public int getCol(){
        return this.col;
    }


    public void setCoord(int row, int col){
        this.row = row;
        this.col = col;
    }

    public boolean isAdjacent(Coordinate that){
        return (this.getCol() == that.getCol() || this.getCol() == that.getCol() + 1 || this.getCol() == that.getCol() - 1)
                && (this.getRow() == that.getRow() || this.getRow() == that.getRow() + 1 || this.getRow() == that.getRow() - 1)
                && (!(this.getCol() == that.getCol() && this.getRow() == that.getRow()));
    }

    public String toString(){
        return "\nRow_:"+this.row+"\nCol :"+this.col;
    }


    public boolean equals(Coordinate that){
        if(this.getCol()==that.getCol() && this.getRow() == that.getRow()){
            return true;
        }
        return false;

    }

}