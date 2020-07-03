package it.polimi.ingsw.view.GUIpackage.panel;

import it.polimi.ingsw.view.GUIpackage.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is the panel that displays
 * the victory message for the winner
 */
public class WinningPanel extends JPanel implements ActionListener {


    private ImageIcon frames;
    private Timer timer;
    private int currentFrame = 1;
    private final JPanel playPanel;


    public WinningPanel(JPanel p, boolean onAnimation) {
        playPanel=p;
        setOpaque(false);
        setBounds(0,0, GUI.getDimension().width,GUI.getDimension().height);
        if(!onAnimation) currentFrame=200;
        frames = new ImageIcon(this.getClass().getResource("/Win/Win" + (currentFrame) + ".png"));

    }

    /**
     * starts the timer
     */

    public void startTransition() {

        currentFrame=1;
        timer = new Timer(30, this);
        timer.start();

    }


    /**
     * updates the background image
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(frames.getImage(),0,0,playPanel);
    }

    @Override
    public void repaint() {
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

        } else {
            this.currentFrame++;
            frames=new ImageIcon(this.getClass().getResource("/Win/Win" + (currentFrame) + ".png"));

            Toolkit.getDefaultToolkit().sync();
        }


    }




}



