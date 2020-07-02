package it.polimi.ingsw.view.GUIpackage.panel;

import it.polimi.ingsw.utils.messages.NicknameMessage;
import it.polimi.ingsw.view.GUIpackage.components.CustomButton;
import it.polimi.ingsw.view.GUIpackage.GUI;
import it.polimi.ingsw.view.GUIpackage.components.MakeSound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Nickname panel.
 * is the screen that allows the
 * nickname to be submitted
 */
public class NicknamePanel extends JPanel implements ActionListener , PropertyChangeListener{

    private final Image image;
    private final Image imageNick ;
    private final JPanel nicknameJPanel;
    private final JTextField nickToFill;
    private final JButton submitButton;
    private final JLabel textLabel;
    private final JLabel invalidNickLabel;
    private final int playerId;
    private String attemptedNick = "";
    private final PropertyChangeSupport nickPanelListener = new PropertyChangeSupport(this);
    private final ArrayList<String> invalidNicknames = new ArrayList<>();

    /**
     * Instantiates a new Nickname panel.
     *
     * @param id the id of the player
     */
    public NicknamePanel(int id) {
        imageNick  = new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance((int)(0.82* GUI.getDimension().height),(int)(0.625*GUI.getDimension().height),Image.SCALE_SMOOTH);

        this.image = new ImageIcon(this.getClass().getResource("/Name/setupBG.jpg")).getImage();
        Font font;
        textLabel = new JLabel();
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")).deriveFont(Font.PLAIN, 40); //upload font
        }catch(IOException | FontFormatException e){
            font = textLabel.getFont();
        }
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        textLabel.setFont(font.deriveFont(Font.PLAIN,60));
        textLabel.setText("Enter your nickname");
        textLabel.setForeground(Color.WHITE);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nickToFill = new JTextField();
        nickToFill.setFont(font);
        nickToFill.setHorizontalAlignment(JTextField.CENTER);
        nickToFill.setForeground(Color.WHITE);
        invalidNickLabel = new JLabel();
        invalidNickLabel.setFont(font.deriveFont(Font.PLAIN,30));
        invalidNickLabel.setForeground(new Color(241, 204, 118, 255));
        nickToFill.setOpaque(false);
        nicknameJPanel = new JPanel(new BorderLayout());
        nicknameJPanel.setOpaque(false);
        nicknameJPanel.add(nickToFill, BorderLayout.NORTH);
        nicknameJPanel.add(invalidNickLabel, BorderLayout.CENTER);
        nicknameJPanel.setBorder(BorderFactory.createEmptyBorder((int)(GUI.getDimension().height/14.4),(int)(GUI.getDimension().width/4.8),(GUI.getDimension().height/9),(int)(GUI.getDimension().width/4.8)));
        submitButton = new CustomButton("/Name/submit");
        nickToFill.setBorder(null);
        invalidNickLabel.setHorizontalAlignment(0);
        playerId = id;
        this.add(Box.createVerticalStrut((GUI.getDimension().height)/4));
        this.add(textLabel);
        this.add(Box.createVerticalStrut(0));
        this.add(nicknameJPanel);
        this.add(Box.createVerticalStrut(0));
        nicknameJPanel.add(submitButton, BorderLayout.SOUTH);
        this.add(Box.createVerticalStrut(80));
        submitButton.addActionListener(this);
        invalidNickLabel.setText(" ");
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
        g.drawImage(this.imageNick, (this.getWidth()-imageNick.getWidth(this))/2, (this.getHeight()-imageNick.getHeight(this))/2, this);
        g.setColor(new Color(223, 202, 181));
        g.fillRect((this.getWidth()/4), this.getHeight() /2,(this.getWidth()/2),5);
    }


    /**
     * manages interactions with clicks
     * on the buttons on this panel
     *
     * @param actionEvent the action event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(submitButton)) {
            String checkSpaces = (nickToFill.getText()).replace(" ", "");
            boolean valid = true;
            for (String s : invalidNicknames) {
                if (s.equals(nickToFill.getText())) {
                    valid = false;
                    break;
                }
            }
            if(valid && !checkSpaces.equalsIgnoreCase("") && nickToFill.getText().length() <= 20){
                nickPanelListener.firePropertyChange("nicknameReceived", null, nickToFill.getText());
                submitButton.setEnabled(false);
                attemptedNick = nickToFill.getText();
            } else {
                nickToFill.setText("");
                new MakeSound().playSound("/Sounds/warn.wav",-10f,false);
                if(!valid) invalidNickLabel.setText("nickname already assigned!");
                else invalidNickLabel.setText("invalid nickname");
                invalidNickLabel.setVisible(true);
            }

        } else if(actionEvent.getSource().equals(nickToFill)){
            invalidNickLabel.setText(" ");
        }
    }

    /**
     * Add nickname panel listener.
     *
     * @param listener the listener
     */
    public void addNicknamePanelListener(PropertyChangeListener listener) {
        nickPanelListener.addPropertyChangeListener(listener);
    }

    /**
     * Handle event from ModelView
     *
     * @param propertyChangeEvent the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("nicknameConfirm")){
            NicknameMessage message  = (NicknameMessage) propertyChangeEvent.getNewValue();
            invalidNicknames.add(message.getNickname());
            if(message.getNickname().equals(attemptedNick) && playerId!=message.getId()){
                submitButton.setEnabled(true);
                ((CardLayout)this.getParent().getLayout()).show(this.getParent(),"NicknamePanel");
            }
        }
    }
}
