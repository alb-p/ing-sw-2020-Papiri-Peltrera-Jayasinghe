package it.polimi.ingsw.view.GUIpackage.panel;

import it.polimi.ingsw.utils.messages.NicknameMessage;
import it.polimi.ingsw.view.GUIpackage.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The type First player selection panel.
 * it is the screen where the godly chooses
 * who is the player who starts the turn
 */
public class FirstPlayerSelectionPanel extends JPanel implements ActionListener, PropertyChangeListener {
    private final PropertyChangeSupport firstPlayerSelectionListener = new PropertyChangeSupport(this);
    private final Dimension frameDimension;
    private final Image image;
    private final JPanel innerPanel;
    Font customFont;


    /**
     * Instantiates a new First player selection panel
     * and all its components
     *
     * @throws IOException         the io exception
     * @throws FontFormatException the font format exception
     */
    public FirstPlayerSelectionPanel() throws IOException, FontFormatException {
        frameDimension= GUI.getDimension();

        innerPanel =new JPanel(){
            Image image= new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance((int) (frameDimension.width/1.5),(int) (frameDimension.height/1.95),Image.SCALE_SMOOTH);
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.image, 0, 0, this);
            }
        };


        this.image = new ImageIcon(this.getClass().getResource("/Name/setupBG.jpg")).getImage();
        JLabel title = new JLabel("Choose first player: ");
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")); //carica font




        innerPanel.setOpaque(false);
        innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
        innerPanel.setPreferredSize(new Dimension((int) (frameDimension.width/1.5), (int) (frameDimension.height/1.95)));
        innerPanel.add(Box.createRigidArea(new Dimension(0,(int) (frameDimension.height/15))));
        innerPanel.add(title);
        innerPanel.add(Box.createRigidArea(new Dimension(0,(int) (frameDimension.height/20))));

        title.setFont(customFont.deriveFont(Font.PLAIN,frameDimension.width/18));

        this.setLayout(new GridBagLayout());
        this.add(innerPanel);




    }


    /**
     * when the button on which the fist player's
     * name is written is pressed, a notify is
     * sent to the GUI class
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            firstPlayerSelectionListener.firePropertyChange("firstPlayerSelected",
                        null, new NicknameMessage(0, ((JButton) e.getSource()).getName()));
        }
    }

    /**
     * Handles the events coming from ModelView.
     * After your god god have been chosen,
     * the first player can be chosen
     *
     * @param evt the property change event
     *
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equalsIgnoreCase("notifyGodSelected")){
            JButton choosePlayer = new JButton(((ArrayList<String>) evt.getNewValue()).get(0));
            choosePlayer.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    choosePlayer.setForeground(new Color(255, 235, 140)); //yellow? new Color(223, 202, 181)
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    choosePlayer.setForeground(Color.BLACK);
                }
            });
            choosePlayer.setName(((ArrayList<String>) evt.getNewValue()).get(0));
            choosePlayer.addActionListener(this);
            choosePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
            choosePlayer.setBorder(null);
            choosePlayer.setContentAreaFilled(false);//trasparenza
            choosePlayer.setFont(customFont.deriveFont(Font.PLAIN,frameDimension.width/30));
            innerPanel.add(choosePlayer);
            innerPanel.add(Box.createRigidArea(new Dimension(0,(int) (frameDimension.height/25))));
        }
    }

    /**
     * Add first player selection listener.
     *
     * @param listener the listener
     */
    public void addFirstPlayerSelectionListener (PropertyChangeListener listener){
        firstPlayerSelectionListener.addPropertyChangeListener(listener);
    }

    /**
     * Paint component. set the background
     * image of the panel
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
    }
}
