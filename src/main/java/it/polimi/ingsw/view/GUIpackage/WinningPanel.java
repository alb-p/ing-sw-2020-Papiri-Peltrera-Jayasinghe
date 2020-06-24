package it.polimi.ingsw.view.GUIpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class WinningPanel extends JPanel implements ActionListener {


    ImageIcon frames;
    private Timer timer;
    int currentFrame = 1;

    private PropertyChangeSupport lista;


    public WinningPanel() {

        setBounds(0,0,GUI.getDimension().width,GUI.getDimension().height);
        frames = new ImageIcon(this.getClass().getResource("/Win/Win" + (currentFrame) + ".png"));

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
        frames=new ImageIcon(this.getClass().getResource("/Win/Win" + (currentFrame) + ".png"));


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.currentFrame == 207) {
            timer.stop();
            frames=null;
            System.gc();//consiglia di far partire il garbage collector
        } else {
            repaint();
            Toolkit.getDefaultToolkit().sync();
        }


    }



}