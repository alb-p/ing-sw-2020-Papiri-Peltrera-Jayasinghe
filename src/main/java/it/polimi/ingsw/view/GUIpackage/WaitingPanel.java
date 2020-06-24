package it.polimi.ingsw.view.GUIpackage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The type Waiting panel.
 */
public class WaitingPanel extends JPanel {


    Image homeBackground;
    private final JLabel label;
    JLabel animation;
    private final Dimension frameDimension;

    /**
     * Instantiates a new Waiting panel.
     *
     * @param message the message
     * @throws IOException         the io exception
     * @throws FontFormatException the font format exception
     */
    public WaitingPanel(String message) throws IOException, FontFormatException{
        frameDimension=GUI.getDimension();

        JPanel innerPanel =new JPanel(){
            Image image= new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance((int) (frameDimension.width/1.5),(int) (frameDimension.height/1.95),Image.SCALE_SMOOTH);
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.image, 0, 0, this);
            }
        };


        animation=new JLabel();
        this.homeBackground =new ImageIcon(this.getClass().getResource("/Home/HomeBG.jpg")).getImage();
        label=new JLabel(message);
        Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")); //carica font


        innerPanel.setOpaque(false);
        innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));
        this.setLayout(new GridBagLayout());
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

        this.add(innerPanel);
    }


    /**
     * Paint component.
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.homeBackground, 0, 0, this);
    }

}