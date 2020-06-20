package it.polimi.ingsw.view.GUIpackage.Components;

import javax.swing.*;
import java.awt.*;

public class GodsButton extends JButton {

    ImageIcon glow;
    ImageIcon godMiniature;
    boolean selected= false;
    String name;


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



    public void setSelected(){
        selected = true;
        repaint();

    }

    public void setUnselected(){
        selected = false;
        repaint();
    }


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
