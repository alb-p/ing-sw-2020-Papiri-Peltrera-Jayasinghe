package it.polimi.ingsw.view.GUIpackage.panel;

import it.polimi.ingsw.view.GUIpackage.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

/**
 * The type Waiting panel.
 */
public class WaitingPanel extends JPanel implements ActionListener {


    private final Image homeBackground;
    private final JLabel label;
    private final JLabel animation;
    private final Dimension frameDimension;
    private final JLayeredPane layeredPane;
    private final JButton exit;
    private final JPanel waitingContainer;

    private PropertyChangeSupport waitingListeners = new PropertyChangeSupport(this);


    /**
     * Instantiates a new Waiting panel.
     *
     * @param message the message
     * @throws IOException         the io exception
     * @throws FontFormatException the font format exception
     */
    public WaitingPanel(String message, GUI gui) throws IOException, FontFormatException{
        frameDimension=GUI.getDimension();
        addWaitingListener(gui);

        JPanel innerPanel =new JPanel(){
            Image image= new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance((int) (frameDimension.width/1.5),(int) (frameDimension.height/1.95),Image.SCALE_SMOOTH);
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.image, 0, 0, this);
            }
        };

        layeredPane=new JLayeredPane();
        layeredPane.setPreferredSize(GUI.getDimension());
        waitingContainer=new JPanel();
        waitingContainer.setLayout(new GridBagLayout());
        waitingContainer.setBounds(0, 0, GUI.getDimension().width, GUI.getDimension().height);
        waitingContainer.setOpaque(false);
        exit=new JButton();

        JPanel exitPanel=new JPanel(null);
        exitPanel.setBounds(0, 0, GUI.getDimension().width, GUI.getDimension().height);
        exitPanel.setOpaque(false);
        exit.setIcon(new ImageIcon(this.getClass().getResource("/Gameplay/exit.png")));
        exit.setBounds(0, 0, 21, 20);
        exit.setContentAreaFilled(false);
        exit.setOpaque(false);
        exit.setLocation(new Point(935, 0));
        exit.setBorder(null);
        exit.setName("exit");
        exit.addActionListener(this);
        exitPanel.add(exit);



        animation=new JLabel();
        this.homeBackground =new ImageIcon(this.getClass().getResource("/Home/HomeBG.jpg")).getImage();
        label=new JLabel(message);
        Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")); //carica font


        innerPanel.setOpaque(false);
        innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
        label.setFont(font.deriveFont(Font.PLAIN,frameDimension.width/18)); //imposta font liscio e dimensione
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.setPreferredSize(new Dimension((int) (frameDimension.width/1.5),(int) (frameDimension.height/1.95)));
        animation.setAlignmentX(Component.CENTER_ALIGNMENT);
        animation.setIcon(new ImageIcon(this.getClass().getResource("/SelectPlayers/loading.gif")));





        innerPanel.add(Box.createRigidArea(new Dimension(0,frameDimension.height/15)));
        innerPanel.add(label);
        innerPanel.add(Box.createRigidArea(new Dimension(0,frameDimension.height/20)));

        innerPanel.add(animation);

        waitingContainer.add(innerPanel);
        layeredPane.add(exitPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(waitingContainer, JLayeredPane.DEFAULT_LAYER);
        this.add(layeredPane);
    }


    /**
     *
     * Paint component. set the background
     * image of the panel
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.homeBackground, 0, 0, this);
    }


    public void addWaitingListener(PropertyChangeListener listener) {
        waitingListeners.addPropertyChangeListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        waitingListeners.firePropertyChange("PlayerToDisconnect", false, true);
        if(this.label.getText().equalsIgnoreCase("Waiting connections..."))
            System.exit(0);

    }
}