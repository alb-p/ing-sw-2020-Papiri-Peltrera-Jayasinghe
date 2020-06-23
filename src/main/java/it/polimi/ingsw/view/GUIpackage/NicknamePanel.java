package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.utils.messages.NicknameMessage;
import it.polimi.ingsw.view.GUIpackage.Components.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class NicknamePanel extends JPanel implements ActionListener , PropertyChangeListener{

    private Image image;
    private Image imageNick ;
    private JPanel nicknamePanel;
    private JTextField nickToFill;
    private JButton submitButton;
    // private CustomButton submitButton;
    private JLabel textLabel;
    private JLabel invalidNickLabel;
    private int playerId;
    private String attemptedNick = "";
    private PropertyChangeSupport nickPanelListener = new PropertyChangeSupport(this);
    private ArrayList<String> invalidNicknames = new ArrayList<>();

    public NicknamePanel(int id) {
        imageNick  = new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance((int)(0.82*GUI.getDimension().height),(int)(0.625*GUI.getDimension().height),Image.SCALE_SMOOTH);

        this.image = new ImageIcon(this.getClass().getResource("/Name/setupBG.jpg")).getImage();
        //this.imageNick = new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage();
        Font font;
        Font labelFont;
        Font errorFont;
        textLabel = new JLabel();
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")).deriveFont(Font.PLAIN, 40); //carica font
            labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")).deriveFont(Font.PLAIN, 60); //carica font
            errorFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")).deriveFont(Font.PLAIN, 30); //carica font
        }catch(IOException | FontFormatException e){
            font = textLabel.getFont();
            labelFont = textLabel.getFont();
            errorFont = textLabel.getFont();
        }
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        textLabel.setFont(labelFont);
        textLabel.setText("Enter your nickname");
        textLabel.setForeground(Color.WHITE);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nickToFill = new JTextField();
        nickToFill.setFont(font);
        nickToFill.setHorizontalAlignment(JTextField.CENTER);
        nickToFill.setForeground(Color.WHITE);
        invalidNickLabel = new JLabel();
        invalidNickLabel.setFont(errorFont);
        invalidNickLabel.setForeground(new Color(241, 204, 118, 255));
        nickToFill.setOpaque(false);
        nicknamePanel = new JPanel(new BorderLayout());
        nicknamePanel.setOpaque(false);
        nicknamePanel.add(nickToFill, BorderLayout.NORTH);
        nicknamePanel.add(invalidNickLabel, BorderLayout.CENTER);
        nicknamePanel.setBorder(BorderFactory.createEmptyBorder((int)(GUI.getDimension().height/14.4),(int)(GUI.getDimension().width/4.8),(int)(GUI.getDimension().height/9),(int)(GUI.getDimension().width/4.8)));
        submitButton = new CustomButton("/Name/submit");
        nickToFill.setBorder(null);
        invalidNickLabel.setHorizontalAlignment(0);
        playerId = id;
        this.add(Box.createVerticalStrut((GUI.getDimension().height)/4));
        this.add(textLabel);
        this.add(Box.createVerticalStrut(0));
        this.add(nicknamePanel);
        this.add(Box.createVerticalStrut(0));
        nicknamePanel.add(submitButton, BorderLayout.SOUTH);
        this.add(Box.createVerticalStrut(80));
        submitButton.addActionListener(this);
        invalidNickLabel.setText(" ");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
        g.drawImage(this.imageNick, (this.getWidth()-imageNick.getWidth(this))/2, (this.getHeight()-imageNick.getHeight(this))/2, this);
        g.setColor(new Color(223, 202, 181));
        g.fillRect((this.getWidth()/4), this.getHeight() /2,(this.getWidth()/2),5);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(submitButton)) {
            boolean valid = true;
            for (String s : invalidNicknames) {
                if (s.equals(nickToFill.getText())) {
                    valid = false;
                    break;
                }
            }
            if(valid && !nickToFill.getText().equalsIgnoreCase("")){
                nickPanelListener.firePropertyChange("nicknameReceived", null, nickToFill.getText());
                submitButton.setEnabled(false);
                attemptedNick = nickToFill.getText();
            } else {
                nickToFill.setText("");
                new MakeSound().playSound("/Sounds/warn.wav",-10f,false);
                invalidNickLabel.setText("nickname already assigned!");
                invalidNickLabel.setVisible(true);
            }

        } else if(actionEvent.getSource().equals(nickToFill)){
            invalidNickLabel.setText(" ");
        }
    }

    public void addNicknamePanelListener(PropertyChangeListener listener) {
        nickPanelListener.addPropertyChangeListener(listener);
    }

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
