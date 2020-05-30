package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.view.GUIpackage.Components.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HomePanel extends JPanel implements ActionListener {
    private PropertyChangeSupport homePanelListeners = new PropertyChangeSupport(this);
    private Image image;
    private CustomButton playButton;
    private CustomButton helpButton;
    private CustomButton exitButton;



    public HomePanel() {


        this.image = new ImageIcon(this.getClass().getResource("/Home/HomeBG.png")).getImage();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        playButton = new CustomButton("play");
        helpButton = new CustomButton("help");
        exitButton = new CustomButton("exit");
        playButton.addActionListener(this);
        helpButton.addActionListener(this);
        exitButton.addActionListener(this);
        this.add(Box.createVerticalStrut(320));
        this.add(playButton);
        this.add(Box.createVerticalStrut(25));
        this.add(helpButton);
        this.add(Box.createVerticalStrut(25));
        this.add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(playButton)){
            homePanelListeners.firePropertyChange("play", null,true);
        }
    }

    public void addHomePanelListener(PropertyChangeListener listener) {
        homePanelListeners.addPropertyChangeListener(listener);
    }
}