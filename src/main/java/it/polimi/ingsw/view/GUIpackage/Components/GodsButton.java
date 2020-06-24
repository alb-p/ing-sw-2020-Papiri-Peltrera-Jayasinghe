package it.polimi.ingsw.view.GUIpackage.Components;

import javax.swing.*;
import java.awt.*;

/**
 * The type Gods button.
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
        this.setContentAreaFilled(false);//trasparenza
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        /*this.glow=new ImageIcon(this.getClass().getResource("/GodsMiniature/glow.png"));//overlay glow
        this.godMiniature=new ImageIcon(this.getClass().getResource("/GodsMiniature/"+god+".png"));*/
        this.glow=new ImageIcon(this.getClass().getResource("/GodSelection/gold.png"));//overlay glow
        this.godMiniature=new ImageIcon(this.getClass().getResource("/GodSelection/"+this.name.toLowerCase()+".png"));
        this.setIcon(godMiniature);
    }


    /**
     * Set selected.
     */
    public void setSelected(){
        selected = true;
        repaint();

    }

    /**
     * Set unselected.
     */
    public void setUnselected(){
        selected = false;
        repaint();
    }


    /**
     * Paint component.
     *
     * @param g the g
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
      //  g.drawImage(this.image, 0, 0, this);
        if(selected){
            godMiniature.paintIcon(this, g, 0,0);
            g.drawImage(this.glow.getImage(), 0, 0, this);
        }else godMiniature.paintIcon(this, g, 0,0);
    }
}
