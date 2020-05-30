package it.polimi.ingsw.view.GUIpackage;

import javax.swing.*;
import java.awt.*;

public class InitialWaitingPanel extends JPanel {

    ImageIcon image;
    Image background;

    public InitialWaitingPanel(){

        this.image=new ImageIcon(this.getClass().getResource("/SelectPlayers/waiting.png"));
        this.background= this.image.getImage().getScaledInstance(400,232,Image.SCALE_SMOOTH);

        this.setOpaque(false);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.background, 0, 0, this);
    }
}