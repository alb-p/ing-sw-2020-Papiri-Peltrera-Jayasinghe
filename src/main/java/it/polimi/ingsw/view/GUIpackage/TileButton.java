package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.Coordinate;

import javax.swing.*;

public class TileButton extends JButton {

    private Coordinate coordinate;
    private boolean occupied=false;
    private String color;
    private int level=0;


    public TileButton(int row, int col){
        coordinate=new Coordinate(row,col);
        this.setText(coordinate.toString()); //debug
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public void setColor(String c) {
        this.color = c;
        if(level==0) this.setText(color);
        else this.setText(color+level);
    }

    public void setLevel(int level) {
        this.level = level;
        if(level==0) this.setText(color);
        else this.setText(color+level);
    }

    @Override
    public String toString() {
        return "tilebutton";
    }
}

