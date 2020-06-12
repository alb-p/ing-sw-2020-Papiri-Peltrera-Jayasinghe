package it.polimi.ingsw.view.GUIpackage.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class IslandAnimationPanel extends JPanel implements ActionListener {


    ImageIcon frames;
    private Timer timer;
    int currentFrame = 1;

    private PropertyChangeSupport lista;


    public IslandAnimationPanel() {

        lista = new PropertyChangeSupport(this);

        frames = new ImageIcon(this.getClass().getResource("/IslandAnimation/island" + (currentFrame) + ".jpg"));

    }

    public void startTransition() {

        timer = new Timer(30, this);
        timer.start();

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        frames.paintIcon(this, g, 0, 0);

        this.currentFrame++;
        frames=new ImageIcon(this.getClass().getResource("/IslandAnimation/island" + (currentFrame) + ".jpg"));



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.currentFrame == 120) {
            timer.stop();
            lista.firePropertyChange("islandTransitionEnded",null,true);
            frames=null;
            System.gc();//consiglia di far partire il garbage collector
        } else {
            repaint();
            Toolkit.getDefaultToolkit().sync();
        }


    }

    public void addPropertyChangeListener(PropertyChangeListener listener) { //aggiunge listener alla lista
        lista.addPropertyChangeListener(listener);
    }
}