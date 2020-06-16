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


    public TileButton(int row, int col, JPanel panel) {
        coordinate = new Coordinate(row, col);
        vSlot = new VirtualSlot(coordinate);
        red = new ImageIcon(GUI.class.getResource("/Colors/red_normal.png")).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);;
        blue = new ImageIcon(GUI.class.getResource("/Colors/blue_normal.png")).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);;
        white = new ImageIcon(GUI.class.getResource("/Colors/white_normal.png")).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);;
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(this.getFloor(), 0, 0, panel);
        g.drawImage(this.getWorker(), 0, 0, panel);

    }

    public void updateView(VirtualSlot vSlot) {
        // rebase the image on vSlot
        this.vSlot = vSlot;
        rebaseWorker();
    }
}

