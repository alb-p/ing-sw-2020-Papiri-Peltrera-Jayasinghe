package it.polimi.ingsw.view.GUIpackage.components;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The type Custom button.
 * component that allows you to create a button
 * that changes its characteristics based on mouse actions
 */
public class CustomButton extends JButton implements MouseListener{

    ImageIcon normal;
    ImageIcon onMouse;
    ImageIcon pressed;
    MakeSound music;

    /**
     * Instantiates a new Custom button.
     *
     * @param name the name
     */
    public CustomButton(String name){

        this.music= new MakeSound();
        this.setBorderPainted(false);
        this.setBorder(null);
        this.setContentAreaFilled(false);//trasparenza
        this.setOpaque(false);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);


        this.normal=new ImageIcon(this.getClass().getResource(name+"_normal.png"));
        this.onMouse=new ImageIcon(this.getClass().getResource(name+"_onmouse.png"));
        this.pressed=new ImageIcon(this.getClass().getResource(name+"_pressed.png"));
        this.setBackground(new Color(0,0,0,0));
        this.setIcon(this.normal);
        this.addMouseListener(this);

    }

    /**
     * Mouse clicked.
     *
     * @param mouseEvent the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }


    /**
     * Mouse pressed. Start click sound
     *
     * @param mouseEvent the mouse event
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        music.playSound("/Sounds/click.wav",-5f,false);
    }

    /**
     * Mouse released. change its icon
     *
     * @param mouseEvent the mouse event
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this.setIcon(this.normal);
    }

    /**
     * Mouse entered. change its icon
     *
     * @param mouseEvent the mouse event
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        this.setIcon(this.onMouse);
    }

    /**
     * Mouse exited. change its icon
     *
     * @param mouseEvent the mouse event
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        this.setIcon(this.normal);
    }


}