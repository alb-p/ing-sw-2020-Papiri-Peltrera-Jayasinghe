package it.polimi.ingsw.view.GUIpackage.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class WorkerIcon extends JLabel implements ActionListener {


    private ImageIcon frames;
    private Timer timer;
    private int currentFrame = 1;
    private final String color;
    private final PropertyChangeSupport workerIconListeners = new PropertyChangeSupport(this);

    int x,y,xfinale,yfinale;


    public WorkerIcon(String color){

        this.color=color;
        System.out.println("/WorkersAnimation/"+this.color+"/"+this.color+(currentFrame) + ".png");
        ImageIcon frames= new ImageIcon(this.getClass().getResource("/WorkersAnimation/"+this.color+"/"+this.color+(currentFrame) + ".png"));
        this.setIcon(frames);
        setBounds(0, 0, 67, 192);

    }




    public synchronized void startTransition(Point start,Point end) {
        x=start.x;
        y=start.y-50;

        xfinale=end.x;
        yfinale=end.y-50;
        setLocation(x,y);
        timer = new Timer(70, this){
            @Override
            public void start() {
                super.start();
                WorkerIcon.this.setVisible(true);
            }
        };
        timer.start();


    }



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
            x=x+((xfinale-x)/(24-currentFrame));
            y=y+((yfinale-y)/(24-currentFrame));
            setLocation(x,y);
            this.currentFrame++;
            frames= new ImageIcon(this.getClass().getResource("/WorkersAnimation/"+this.color+"/"+this.color+(currentFrame) + ".png"));
        }


    }

    public void addWorkerIconListener(PropertyChangeListener listener){
        workerIconListeners.addPropertyChangeListener(listener);
    }

}

/*

ESEMPIO DI UTILIZZO

JPanel test= new JPanel();
        test.setLayout(null);
        test.setBounds(0, 0, GUI.getDimension().width,GUI.getDimension().height);
        test.setOpaque(false);
        layeredPane.add(test,JLayeredPane.DEFAULT_LAYER);
        WorkerIcon icon= new WorkerIcon("blue");
        test.add(icon);
        icon.startTransition(e.getPoint(),new Point(133,133));
 */