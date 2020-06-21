package it.polimi.ingsw.view.GUIpackage.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WorkerIcon extends JLabel implements ActionListener {


    ImageIcon frames;
    private Timer timer;
    int currentFrame = 1;
    String color;

    int x,y,xfinale,yfinale;


    public WorkerIcon(String color){

        this.color=color;
        ImageIcon frames= new ImageIcon(this.getClass().getResource("/WorkerAnimaton/"+this.color+"/"+this.color+(currentFrame) + ".png"));
        this.setIcon(frames);
        setBounds(0, 0, 67, 192);

    }




    public void startTransition(Point start,Point end) {

        x=start.x;
        y=start.y;

        xfinale=end.x;
        yfinale=end.y;

        timer = new Timer(70, this);
        timer.start();

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.currentFrame == 23) {
            currentFrame=1;
            timer.stop();
            frames=null;
            System.gc();//consiglia di far partire il garbage collector

        } else {
            this.setIcon(frames);
            x=x+((xfinale-x)/(24-currentFrame));
            y=y+((yfinale-y)/(24-currentFrame));
            setLocation(x,y);
            this.currentFrame++;
            frames= new ImageIcon(this.getClass().getResource("/WorkerAnimaton/"+this.color+"/"+this.color+(currentFrame) + ".png"));
        }


    }



}
