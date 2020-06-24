package it.polimi.ingsw.view.GUIpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static java.lang.Thread.sleep;

/**
 * The type Logo panel.
 */
public class LogoPanel extends JPanel implements ActionListener {


    ImageIcon frames;
    private Timer timer;
    int currentFrame = 1;

    private PropertyChangeSupport lista;


    /**
     * Instantiates a new Logo panel.
     */
    public LogoPanel() {

        lista = new PropertyChangeSupport(this);

        frames = new ImageIcon(this.getClass().getResource("/Logo/Logo" + (currentFrame) + ".jpg"));

    }

    /**
     * Start transition.
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
        frames.paintIcon(this, g, 0, 0);

        this.currentFrame++;
        frames=new ImageIcon(this.getClass().getResource("/Logo/Logo" + (currentFrame) + ".jpg"));


    }

    /**
     * Action performed.
     *
     * @param e the e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.currentFrame == 90) {
            timer.stop();
            lista.firePropertyChange("logoTransitionEnded",null,true);//attiva propertyChange dei listeners con passando in questo caso delle stringhe
            frames=null;
            System.gc();//consiglia di far partire il garbage collector
        } else {
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