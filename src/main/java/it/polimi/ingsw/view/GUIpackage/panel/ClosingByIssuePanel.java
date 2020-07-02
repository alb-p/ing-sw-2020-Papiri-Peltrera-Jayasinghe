package it.polimi.ingsw.view.GUIpackage.panel;

import it.polimi.ingsw.view.GUIpackage.components.CustomButton;
import it.polimi.ingsw.view.GUIpackage.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This panel shows up whenever,
 * during a setup, another player
 * leave the game.
 */
public class ClosingByIssuePanel extends JPanel implements ActionListener {

    private final Image image = new ImageIcon(this.getClass().getResource("/Home/HomeBG.jpg")).getImage();
    private final CustomButton exitButton;
    private final PropertyChangeSupport closingByIssuePanelListeners = new PropertyChangeSupport(this);

    public ClosingByIssuePanel(List<String> labelStrings){
        this.setLayout(new GridBagLayout());

        JPanel innerPanel =new JPanel(){
            final Image image= new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance((int) (GUI.getDimension().width/1.5),(int) (GUI.getDimension().height/1.95),Image.SCALE_SMOOTH);
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.image, 0, 0, this);
            }
        };
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")); //upload font
        } catch (FontFormatException | IOException e) {
            font = this.getFont();
        }

        List<JLabel> labels = new ArrayList<>();

        for(String s : labelStrings ){
            JLabel label = new JLabel();
            label.setFont(font.deriveFont(Font.PLAIN,(float)GUI.getDimension().width/18));
            label.setForeground(Color.WHITE);
            label.setText(s);
            labels.add(label);
        }


        exitButton = new CustomButton("/Home/exit");
        exitButton.addActionListener(this);
        this.setLayout(new GridBagLayout());
        innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.Y_AXIS));

        innerPanel.setPreferredSize(new Dimension((int) (GUI.getDimension().width/1.5), (int) (GUI.getDimension().height/1.95)));
        innerPanel.add(Box.createRigidArea(new Dimension(0, GUI.getDimension().height/12)));

        for(JLabel label : labels){
            innerPanel.add(label);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        innerPanel.add(Box.createRigidArea(new Dimension(0, GUI.getDimension().height/16)));
        innerPanel.add(exitButton);
        innerPanel.setOpaque(false);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(innerPanel);

    }

    public void addClosingByIssuePanelListener(PropertyChangeListener listener){
        closingByIssuePanelListeners.addPropertyChangeListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(exitButton)){
            closingByIssuePanelListeners.firePropertyChange("playerToDisconnect", false, true);
        }
    }
}
