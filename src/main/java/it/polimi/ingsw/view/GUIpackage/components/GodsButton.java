package it.polimi.ingsw.view.GUIpackage.components;

import javax.swing.*;
import java.awt.*;

/**
 * The type Gods button.
 * a button used to select the gods for a match
 */
public class GodsButton extends JButton {

    ImageIcon glow;
    ImageIcon godMiniature;
    boolean selected= false;
    String name;


    /**
     * Instantiates a new Gods button.
     *
     * @param god the god
     */
    public GodsButton(String god){
        this.name=god;
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.glow=new ImageIcon(this.getClass().getResource("/GodSelection/gold.png"));//overlay glow
        this.godMiniature=new ImageIcon(this.getClass().getResource("/GodSelection/"+this.name.toLowerCase()+".png"));
        this.setIcon(godMiniature);
    }


    /**
     * Set selected. the button is painted is yellow
     */
    public void setSelected(){
        selected = true;
        repaint();

    }

    /**
     * Set unselected. cancel the yellow selection
     */
    public void setUnselected(){
        selected = false;
        repaint();
    }


    /**
     * Paint component.
     * apply icon to button
     *
     *
     * @param g the g
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(selected){
            godMiniature.paintIcon(this, g, 0,0);
            g.drawImage(this.glow.getImage(), 0, 0, this);
        }else godMiniature.paintIcon(this, g, 0,0);
    }
}
