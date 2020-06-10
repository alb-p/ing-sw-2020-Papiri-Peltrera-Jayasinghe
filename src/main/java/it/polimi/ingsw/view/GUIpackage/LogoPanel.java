package it.polimi.ingsw.view.GUIpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static java.lang.Thread.sleep;

public class LogoPanel extends JPanel implements ActionListener {


    ImageIcon[] frames = new ImageIcon[90];
    private Timer timer;
    int currentFrame = 0;

    private PropertyChangeSupport lista;


    public LogoPanel() {

        lista = new PropertyChangeSupport(this);
        Image imageScaled;
        ImageIcon originalImage;

        for (int i = 0; i < 90; i++) {
            frames[i] = new ImageIcon(this.getClass().getResource("/Logo/Logo" + (i + 1) + ".jpg"));
            //frames[i] = new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png"));
        }

    }

    public void startTransition() {

        timer = new Timer(30, this);
        timer.start();

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        frames[this.currentFrame].paintIcon(this, g, 0, 0);
        //frames[this.currentFrame].paintIcon(this, g, currentFrame, currentFrame);
        this.currentFrame++;


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.currentFrame == frames.length-1) {
            timer.stop();
            lista.firePropertyChange("logoTransitionEnded",null,true);//attiva propertyChange dei listeners con passando in questo caso delle stringhe

        } else {
            repaint();
            Toolkit.getDefaultToolkit().sync();
        }


    }

    public void addPropertyChangeListener(PropertyChangeListener listener) { //aggiunge listener alla lista
        lista.addPropertyChangeListener(listener);
    }
}