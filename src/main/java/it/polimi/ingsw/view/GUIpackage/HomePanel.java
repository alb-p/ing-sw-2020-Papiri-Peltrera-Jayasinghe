package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.view.GUIpackage.Components.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HomePanel extends JPanel implements ActionListener {
    private PropertyChangeSupport homePanelListeners = new PropertyChangeSupport(this);
    private Image image;
    private CustomButton playButton;
    private CustomButton helpButton;
    private CustomButton exitButton;
    Dimension frameDimension;



    public HomePanel(Dimension d) {

        frameDimension=d;

        this.image = new ImageIcon(this.getClass().getResource("/Home/Home_title.jpg")).getImage();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        playButton = new CustomButton("/Home/play");
        helpButton = new CustomButton("/Home/help");
        exitButton = new CustomButton("/Home/exit");
        playButton.addActionListener(this);
        helpButton.addActionListener(this);
        exitButton.addActionListener(this);
        this.add(Box.createVerticalStrut((int) (frameDimension.height/1.875)));
        this.add(playButton);
        this.add(Box.createVerticalStrut(frameDimension.height/24));
        this.add(helpButton);
        this.add(Box.createVerticalStrut(frameDimension.height/24));
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
        if (actionEvent.getSource().equals(exitButton)){
            System.exit(0);
        }
        if (actionEvent.getSource().equals(helpButton)){
            if (Desktop.isDesktopSupported()) {
                try {
                    URL resource = this.getClass().getResource("/Home/rules.pdf");
                    Desktop.getDesktop().browse(new URI(String.valueOf(resource)));

                } catch (IOException | URISyntaxException ex) {
                    // no application registered for PDFs
                }
            }
        }
    }

    public void addHomePanelListener(PropertyChangeListener listener) {
        homePanelListeners.addPropertyChangeListener(listener);
    }
}