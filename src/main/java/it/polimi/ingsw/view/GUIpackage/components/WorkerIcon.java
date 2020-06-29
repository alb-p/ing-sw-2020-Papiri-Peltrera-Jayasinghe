package it.polimi.ingsw.view.GUIpackage.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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

    int posX;
    int posY;
    int finalX;
    int finalY;

    float deltaX;
    float deltaY;

    float exactX;
    float exactY;


    /**
     * Instantiates a new Worker icon.
     *
     * @param color the color
     */
    public WorkerIcon(String color){
        if(color.equalsIgnoreCase("white")) {
            this.color = "tan";
        } else this.color=color;
        ImageIcon workerFrames= new ImageIcon(this.getClass().getResource("/WorkersAnimation/"+this.color+"/"+this.color+(currentFrame) + ".png"));
        this.setIcon(workerFrames);
        setBounds(0, 0, 67, 192);
    }


    /**
     * Start animation with timer.
     *
     * @param start the start
     * @param end   the end
     */
    public synchronized void startTransition(Point start,Point end) {
        posX =start.x+7;
        exactX= posX;
        posY =start.y-55;
        exactY= posY;

        finalX =end.x+7;
        finalY =end.y-55;

        deltaX = (finalX - posX)/23;
        deltaY=(finalY - posY)/23;

        setLocation(posX, posY);
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

        } else {
            this.setIcon(frames);
            exactX=exactX+ deltaX;
            posX = (int) exactX;

            exactY=exactY+deltaY;
            posY = (int) exactY;

            setLocation(posX, posY);
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
