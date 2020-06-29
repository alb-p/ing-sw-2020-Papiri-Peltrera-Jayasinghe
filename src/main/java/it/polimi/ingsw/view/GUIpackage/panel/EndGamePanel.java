package it.polimi.ingsw.view.GUIpackage.panel;

import it.polimi.ingsw.view.GUIpackage.components.CustomButton;
import it.polimi.ingsw.view.GUIpackage.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This panel shows up whenever,
 * during a setup, another player
 * leave the game.
 */
public class EndGamePanel extends JPanel implements ActionListener {

    private final Image image = new ImageIcon(this.getClass().getResource("/Home/HomeBG.jpg")).getImage();
    private CustomButton exitButton;

    public EndGamePanel(){
        this.setLayout(new GridBagLayout());

        JPanel innerPanel =new JPanel(){
            Image image= new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance((int) (GUI.getDimension().width/1.5),(int) (GUI.getDimension().height/1.95),Image.SCALE_SMOOTH);
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.image, 0, 0, this);
            }
        };
        Font font;
        JLabel label = new JLabel();
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")); //upload font
        } catch (FontFormatException | IOException e) {
            font = label.getFont();
        }

        label.setFont(font.deriveFont(Font.PLAIN,GUI.getDimension().width/18));
        label.setForeground(Color.WHITE);
        label.setText("A player left the lobby,");
        JLabel label2 = new JLabel();
        label2.setFont(font.deriveFont(Font.PLAIN,GUI.getDimension().width/18));
        label2.setForeground(Color.WHITE);
        label2.setText("please restart the game");

        exitButton = new CustomButton("/Home/exit");
        exitButton.addActionListener(this);
        this.setLayout(new GridBagLayout());
        innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));

        innerPanel.setPreferredSize(new Dimension((int) (GUI.getDimension().width/1.5), (int) (GUI.getDimension().height/1.95)));

        innerPanel.add(Box.createRigidArea(new Dimension(0, GUI.getDimension().height/12)));
        innerPanel.add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(label2);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(Box.createRigidArea(new Dimension(0, GUI.getDimension().height/16)));
        innerPanel.add(exitButton);
        innerPanel.setOpaque(false);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(innerPanel);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(exitButton)){
            System.exit(0);
        }
    }
}
