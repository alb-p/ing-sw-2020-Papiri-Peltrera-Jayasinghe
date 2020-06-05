package it.polimi.ingsw.view.GUIpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

import static java.lang.Thread.sleep;

public class MainPanel extends JPanel {
    private Point initialClick;

    public MainPanel(final JFrame parent){

//-----------------------------------------SI TIENE? BHOOOO-------------------------
        Random r=new Random();
        //bho
        if(r.nextInt(10)==4){
            Thread musicThread = new Thread(() -> {
                try {
                    sleep(2000);
                    MakeSound sound = new MakeSound();
                    sound.playSound("/Sounds/track1.wav");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            musicThread.start();
        }
//---------------------------------------------------------------------------------

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {

                // get location of Window
                int thisX = parent.getLocation().x;
                int thisY = parent.getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                parent.setLocation(X, Y);
            }
        });
    }
}