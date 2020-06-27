package it.polimi.ingsw.view.GUIpackage.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The type Island animation panel.
 * panel that allows the animation of
 * the island before starting the match
 */
public class IslandAnimationPanel extends JPanel implements ActionListener {


    Image frames;
    private Timer timer;
    int currentFrame = 1;

    private PropertyChangeSupport lista;


    /**
     * Instantiates a new Island animation panel.
     */
    public IslandAnimationPanel() {

        lista = new PropertyChangeSupport(this);

    }

    /**
     * Start timer.
     */
    public void startTransition() {

        timer = new Timer(30, this);
        timer.start();

    }


    /**
     * Paint component.
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(frames,0,0,this);

    }

    /**
     * is triggered by the timer. if the
     * transition is complete, a notification
     * is sent to the gui class
     *
     * @param e the e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.currentFrame == 120) {
            timer.stop();
            lista.firePropertyChange("islandTransitionEnded",null,true);
        } else {
            frames=new ImageIcon(this.getClass().getResource("/IslandAnimation/island" + (currentFrame) + ".jpg")).getImage();
            this.currentFrame++;
            repaint();
            Toolkit.getDefaultToolkit().sync();
        }


    }

    /**
     * Add property change listener.
     *
     * @param listener the listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) { //aggiunge listener alla lista
        lista.addPropertyChangeListener(listener);
    }
}