package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Coordinate implements Serializable {
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
        return "\nRow :"+this.row+"\nCol :"+this.col;
    }


    public boolean equals(Coordinate that){
        if(this.getCol()==that.getCol() && this.getRow() == that.getRow()){
            return true;
        }
        return false;

    }


    public ArrayList<Coordinate> getAdiacentCoords(){        //it returns a list of adiacent coordinates
        ArrayList<Coordinate> list= new ArrayList<>();
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                if(this.getCol()+i<5 && this.getCol()+i>=0
                        && this.getRow()+j<5 && this.getRow()+j>=0
                        && (i!=0 || j!=0))
                    list.add(new Coordinate(this.getRow()+j,this.getCol()+i));
            }
        }

        return list;
    }



}