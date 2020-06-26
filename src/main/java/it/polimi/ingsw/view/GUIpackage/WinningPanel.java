package it.polimi.ingsw.view.GUIpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

/**
 * it is the panel that displays
 * the victory message for the winner
 */
public class WinningPanel extends JPanel implements ActionListener {


    ImageIcon frames;
    private Timer timer;
    int currentFrame = 1;


    public WinningPanel() {
        setOpaque(false);
        setBounds(0,0,GUI.getDimension().width,GUI.getDimension().height);
        frames = new ImageIcon(this.getClass().getResource("/Win/Win" + (currentFrame) + ".png"));

    }

    /**
     * starts the timer
     */

    public void startTransition() {

        timer = new Timer(30, this);
        timer.start();

    }


    /**
     * updates the background image
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        frames.paintIcon(this, g, 0, 0);

        this.currentFrame++;
        frames=new ImageIcon(this.getClass().getResource("/Win/Win" + (currentFrame) + ".png"));


    }

    /**
     * action triggered by the timer
     *
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.currentFrame == 200) {
            timer.stop();
            frames=null;
        } else {
            repaint();
            Toolkit.getDefaultToolkit().sync();
        }


    }



}