package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.VirtualSlot;

import javax.swing.*;
import java.awt.*;

/**
 * The type Tile button.
 * is the tile that makes up the IslanBoard
 */
public class TileButton extends JButton {

    private final Coordinate coordinate;
    private String color;
    private int level = 0;
    private Image worker;
    private final Image blue;
    private final Image red;
    private final Image white;
    private VirtualSlot vSlot;
    private final JPanel panel;

    private final Image first;
    private final Image second;
    private final Image third;
    private final Image dome;
    private Image floor;


    /**
     * Instantiates a new Tile button.
     *
     * @param row   the row of the table
     * @param col   the col of the tabel
     * @param panel the parent's panel
     */
    public TileButton(int row, int col, JPanel panel) {
        coordinate = new Coordinate(row, col);
        vSlot = new VirtualSlot(coordinate);
        setBorder(null);
        red = new ImageIcon(GUI.class.getResource("/WorkersAnimation/red.png")).getImage();
        blue = new ImageIcon(GUI.class.getResource("/WorkersAnimation/blue.png")).getImage();
        white = new ImageIcon(GUI.class.getResource("/WorkersAnimation/tan.png")).getImage();
        first = new ImageIcon(GUI.class.getResource("/Buildings/1.jpg")).getImage();
        second = new ImageIcon(GUI.class.getResource("/Buildings/2.jpg")).getImage();
        third = new ImageIcon(GUI.class.getResource("/Buildings/3.jpg")).getImage();
        dome = new ImageIcon(GUI.class.getResource("/Buildings/DomeMEDIA.png")).getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH);

        if (!coordinate.equals(new Coordinate(-1, -1))) {
            //this.setText("      "); //debug
            this.setPreferredSize(new Dimension(120, 120));
            this.setMinimumSize(new Dimension(120, 120));
        } else {
            setPreferredSize(new Dimension(130, 200));
        }
        this.panel = panel;
    }

    /**
     * Sets worker.
     *
     * @param worker the worker
     */
    public void setWorker(Image worker) {
        this.worker = worker;
    }

    /**
     * assign the correct image
     * of the worker present in the respective vslot
     */
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

    /**
     * assign the correct image
     * of the building level present in
     * the respective vslot
     */
    public void rebaseFloor(){
        if(vSlot.getLevel()==1){
            floor= first;
        }else if(vSlot.getLevel()==2){
            floor= second;
        }else if(vSlot.getLevel()==3){
            floor= third;
        }

    }

    /**
     * Get floor image.
     *
     * @return the image
     */
    public Image getFloor(){
        return floor;
    }

    /**
     * Gets worker.
     *
     * @return the worker
     */
    public Image getWorker() {
        return worker;
    }


    /**
     * Gets coordinate.
     *
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    /**
     * Sets color.
     *
     * @param c the c
     */
    public void setColor(String c) {
        this.color = c;
        if (level == 0) this.setText(color);
        else this.setText(color + level);
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
        if (level == 0) this.setText(color);
        else this.setText(color + level);
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "tilebutton";
    }

    /**
     * Repaint.
     */
    @Override
    public void repaint() {
    }

    /**
     * Paint component.
     * paint the image of the building
     * and worker in this specific tile
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(this.getFloor(), 3, 3, panel);
        g.drawImage(this.getWorker(), 17, 17, panel);
        if(vSlot.hasDome()) g.drawImage(this.dome, 3,3, panel);
    }


    /**
     * Update the information of this tile
     *
     * @param vSlot the v slot
     */
    public void updateView(VirtualSlot vSlot) {
        // rebase the image on vSlot
        this.vSlot = vSlot;
        rebaseWorker();
        rebaseFloor();
    }
}

