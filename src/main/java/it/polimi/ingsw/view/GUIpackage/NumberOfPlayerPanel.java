package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.view.GUIpackage.Components.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class NumberOfPlayerPanel extends JPanel implements ActionListener {
    private PropertyChangeSupport numberOfPlayerPanelListeners = new PropertyChangeSupport(this);
    private Image image;
    private JButton twoPlayers;
    private JButton threePlayers;

    public NumberOfPlayerPanel() throws IOException, FontFormatException {
        Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")); //carica font

        this.image = new ImageIcon(this.getClass().getResource("/Home/HomeBG.png")).getImage();
        JLabel label=new JLabel("Select Number of Players");
        label.setFont(font.deriveFont(Font.PLAIN,20)); //imposta font liscio e dimensione 20
        this.add(label);
        //twoPlayers = new CustomButton("twoPlayers");
        twoPlayers = new JButton("twoPlayers");
        //threePlayers = new CustomButton("threePlayers");
        threePlayers = new JButton("threePlayers");
        twoPlayers.addActionListener(this);
        threePlayers.addActionListener(this);

        this.add(twoPlayers);
        this.add(threePlayers);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(twoPlayers)){
            numberOfPlayerPanelListeners.firePropertyChange("numberOfPlayers",null, 2);
        }else if(actionEvent.getSource().equals(threePlayers)){
            numberOfPlayerPanelListeners.firePropertyChange("numberOfPlayers",null, 3);
        }

    }

    public void addHomePanelListener(PropertyChangeListener listener) {
        numberOfPlayerPanelListeners.addPropertyChangeListener(listener);
    }
}
