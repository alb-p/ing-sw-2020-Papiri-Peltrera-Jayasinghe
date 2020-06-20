package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.VirtualSlot;

import javax.swing.*;
import java.awt.*;

public class TileButton extends JButton {

    private Coordinate coordinate;
    private String color;
    private int level = 0;
    private Image worker;
    private Image moment;
    private Image blue;
    private Image red;
    private Image white;
    private VirtualSlot vSlot;
    private JPanel panel;
    int i = 0;

    private Image uno ;
    private Image due;
    private Image tre;
    private Image dome;
    private Image floor;



    public TileButton(int row, int col, JPanel panel) {
        coordinate = new Coordinate(row, col);
        vSlot = new VirtualSlot(coordinate);
        setBorder(null);
        red = new ImageIcon(GUI.class.getResource("/WorkersAnimation/red.png")).getImage();
        blue = new ImageIcon(GUI.class.getResource("/WorkersAnimation/blue.png")).getImage();
        white = new ImageIcon(GUI.class.getResource("/WorkersAnimation/tan.png")).getImage();
        uno = new ImageIcon(GUI.class.getResource("/Buildings/1.jpg")).getImage();
        due = new ImageIcon(GUI.class.getResource("/Buildings/2.jpg")).getImage();
        tre = new ImageIcon(GUI.class.getResource("/Buildings/3.jpg")).getImage();
        dome = new ImageIcon(GUI.class.getResource("/Buildings/4.jpg")).getImage();

        if (!coordinate.equals(new Coordinate(-1, -1))) {
            //this.setText("      "); //debug
            this.setPreferredSize(new Dimension(120, 120));
            this.setMinimumSize(new Dimension(120, 120));
        } else {
            setPreferredSize(new Dimension(130, 200));
        }
        this.panel = panel;
    }

    public void setWorker(Image worker) {
        this.worker = worker;
    }

    public void rebaseWorker() {
        if (vSlot.getColor() == null) {
            worker = null;
        } else if (vSlot.getColor().equals(Color.BLUE)) {
            worker = blue;
        } else if (vSlot.getColor().equals(Color.RED)) {
            worker = red;
        } else if (vSlot.getColor().equals(Color.WHITE)) {
            worker = white;
        }
    }

    public void rebaseFloor(){
        if(vSlot.getLevel()==1){
            floor=uno;
        }else if(vSlot.getLevel()==2){
            floor=due;
        }else if(vSlot.getLevel()==3){
            floor=tre;
        } if(vSlot.hasDome()){
            floor=dome;
        }

    }

    public Image getFloor(){
        return floor;
    }

    public Image getWorker() {
        return worker;
    }


    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public void setColor(String c) {
        this.color = c;
        if (level == 0) this.setText(color);
        else this.setText(color + level);
    }

    public void setLevel(int level) {
        this.level = level;
        if (level == 0) this.setText(color);
        else this.setText(color + level);
    }

    @Override
    public String toString() {
        return "tilebutton";
    }

    @Override
    public void repaint() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        g.drawImage(this.getFloor(), 3, 3, panel);
        g.drawImage(this.getWorker(), 17, 17, panel);
    }


    public void updateView(VirtualSlot vSlot) {
        // rebase the image on vSlot
        this.vSlot = vSlot;
        rebaseWorker();
        rebaseFloor();
    }
}

