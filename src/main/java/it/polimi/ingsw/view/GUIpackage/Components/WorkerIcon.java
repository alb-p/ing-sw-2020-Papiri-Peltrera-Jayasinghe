package it.polimi.ingsw.view.GUIpackage.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * The type Worker icon.
 * icon used to animate worker movement
 */
public class WorkerIcon extends JLabel implements ActionListener {


    private ImageIcon frames;
    private Timer timer;
    private int currentFrame = 1;
    private final String color;
    private final PropertyChangeSupport workerIconListeners = new PropertyChangeSupport(this);

    int x,
    y,
    xfinale,
    yfinale;
    float deltax,
    deltay;

    float exactX,
    exactY;


    /**
     * Instantiates a new Worker icon.
     *
     * @param color the color
     */
    public WorkerIcon(String color){
        if(color.equalsIgnoreCase("white")) {
            this.color = "tan";
        } else this.color=color;
        System.out.println("/WorkersAnimation/"+this.color+"/"+this.color+(currentFrame) + ".png");
        ImageIcon frames= new ImageIcon(this.getClass().getResource("/WorkersAnimation/"+this.color+"/"+this.color+(currentFrame) + ".png"));
        this.setIcon(frames);
        setBounds(0, 0, 67, 192);

    }


    /**
     * Start animation with timer.
     *
     * @param start the start
     * @param end   the end
     */
    public synchronized void startTransition(Point start,Point end) {
        x=start.x+7;
        exactX=x;
        y=start.y-55;
        exactY=y;

        xfinale=end.x+7;
        yfinale=end.y-55;

        deltax= (xfinale-x)/23;
        deltay=(yfinale-y)/23;

        setLocation(x,y);
        timer = new Timer(50, this){
            @Override
            public void start() {
                super.start();
                WorkerIcon.this.setVisible(true);
            }
        };
        timer.start();


    }


    /**
     * Action performed.
     * triggered by the timer. set the correct icon for the animation
     *
     * @param e the e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.currentFrame == 23) {

            currentFrame=1;
            timer.stop();
            frames=null;
            workerIconListeners.firePropertyChange("movementTransitionEnded", false, true);
            System.gc();//consiglia di far partire il garbage collector

        } else {
            this.setIcon(frames);
            exactX=exactX+deltax;
            x= (int) exactX;

            exactY=exactY+deltay;
            y= (int) exactY;

            setLocation(x,y);
            this.currentFrame++;
            frames= new ImageIcon(this.getClass().getResource("/WorkersAnimation/"+this.color+"/"+this.color+(currentFrame) + ".png"));
        }

    }

    /**
     * Add worker icon listener.
     *
     * @param listener the listener
     */
    public void addWorkerIconListener(PropertyChangeListener listener){
        workerIconListeners.addPropertyChangeListener(listener);
    }

}
