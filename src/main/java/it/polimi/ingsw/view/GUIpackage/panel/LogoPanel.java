package it.polimi.ingsw.view.GUIpackage.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static java.lang.Thread.sleep;

/**
 * is the initial screen where the logo is shown
 */
public class LogoPanel extends JPanel implements ActionListener {


    private Image frames;
    private Timer timer;
    private int currentFrame = 1;

    private final PropertyChangeSupport list;


    /**
     * Instantiates a new Logo panel.
     */
    public LogoPanel() {

        list = new PropertyChangeSupport(this);
        frames=new ImageIcon(this.getClass().getResource("/Logo/Logo" + (currentFrame) + ".jpg")).getImage();



    }

    /**
     * Start timer.
     */
    public void startTransition() {

        timer = new Timer(30, this);
        timer.start();

    }


    /**
     * updates the background image
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(frames,0,0,this);

        frames=new ImageIcon(this.getClass().getResource("/Logo/Logo" + (currentFrame) + ".jpg")).getImage();

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
        if (this.currentFrame == 90) {
            timer.stop();
            list.firePropertyChange("logoTransitionEnded",null,true);//attiva propertyChange dei listeners con passando in questo caso delle stringhe
        } else {
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
        list.addPropertyChangeListener(listener);
    }
}