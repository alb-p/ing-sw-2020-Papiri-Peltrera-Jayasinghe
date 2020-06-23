package it.polimi.ingsw.view.GUIpackage.Components;

import it.polimi.ingsw.view.GUIpackage.MakeSound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomButton extends JButton implements MouseListener{

    ImageIcon normal;
    ImageIcon onMouse;
    ImageIcon pressed;
    MakeSound music;

        public CustomButton(String name){

        this.music= new MakeSound();
        this.setBorder(null);
        this.setContentAreaFilled(false);//trasparenza
        this.setAlignmentX(Component.CENTER_ALIGNMENT);


        this.normal=new ImageIcon(this.getClass().getResource(name+"_normal.png"));
        this.onMouse=new ImageIcon(this.getClass().getResource(name+"_onmouse.png"));
        this.pressed=new ImageIcon(this.getClass().getResource(name+"_pressed.png"));

        this.setIcon(this.normal);
        this.addMouseListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }


    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        music.playSound("/Sounds/click.wav",-5f,false);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this.setIcon(this.normal);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        this.setIcon(this.onMouse);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        this.setIcon(this.normal);
    }


}