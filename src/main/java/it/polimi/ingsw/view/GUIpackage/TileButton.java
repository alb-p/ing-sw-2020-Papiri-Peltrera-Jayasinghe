package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.VirtualSlot;

import javax.swing.*;
import java.awt.*;

public class TileButton extends JButton {

    private Coordinate coordinate;
    private boolean occupied=false;
    private String color;
    private int level=0;
    private Image worker;
    private Point initialClick;


    public TileButton(int row, int col){
        coordinate=new Coordinate(row,col);
        this.setText(coordinate.toString()); //debug
    }

    public void setWorker(Image worker) {
        this.worker = worker;
        repaint();
    }
    public Image getWorker(){
        return worker;
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


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(worker!=null){
            g.drawImage(this.worker, 0,0,this);
        }
        Toolkit.getDefaultToolkit().sync();

    }


    public void updateView(VirtualSlot vSlot) {
        // rebase the image on vSlot
        this.setText(vSlot.toString());
    }
}

