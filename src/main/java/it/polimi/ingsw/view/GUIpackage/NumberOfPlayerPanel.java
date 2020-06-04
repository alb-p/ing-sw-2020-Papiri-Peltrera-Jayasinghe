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
    private JLabel label;
    private Dimension frameDimension;


    public NumberOfPlayerPanel(Dimension d) throws IOException, FontFormatException {
        frameDimension=d;

        JPanel innerPanel =new JPanel(){
            Image image= new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance((int) (frameDimension.width/1.5),(int) (frameDimension.height/1.95),Image.SCALE_SMOOTH);
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.image, 0, 0, this);
            }
        };





        label=new JLabel("Select Number of Players");
        twoPlayers = new CustomButton("/SelectPlayers/2");
        threePlayers = new CustomButton("/SelectPlayers/3");
        this.image = new ImageIcon(this.getClass().getResource("/Home/HomeBG.jpg")).getImage();
        Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")); //carica font
        JPanel innerPanel2=new JPanel();



        this.setLayout(new GridBagLayout());
        innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
        innerPanel2.setLayout(new BoxLayout(innerPanel2,BoxLayout.X_AXIS));
        label.setFont(font.deriveFont(Font.PLAIN,frameDimension.width/18)); //imposta font liscio e dimensione 45
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        twoPlayers.addActionListener(this);
        threePlayers.addActionListener(this);











        innerPanel2.setOpaque(false);
        innerPanel.setOpaque(false);

        innerPanel.setPreferredSize(new Dimension((int) (frameDimension.width/1.5), (int) (frameDimension.height/1.95)));
        innerPanel.add(Box.createRigidArea(new Dimension(0,(int) (frameDimension.height/15))));
        innerPanel.add(label);
        innerPanel.add(Box.createRigidArea(new Dimension(0,(int) (frameDimension.height/12))));
        innerPanel.add(innerPanel2);


        innerPanel2.add(twoPlayers);
        innerPanel2.add(Box.createRigidArea(new Dimension((int) (frameDimension.width/16),0)));
        innerPanel2.add(threePlayers);

        this.add(innerPanel);

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
