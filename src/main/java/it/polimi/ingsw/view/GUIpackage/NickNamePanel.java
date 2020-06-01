package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.utils.messages.NicknameMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class NickNamePanel extends JPanel implements ActionListener , PropertyChangeListener{

    private Image image;
    private Image imageNick  = new ImageIcon(this.getClass().getResource("/SelectPlayers/panel.png")).getImage().getScaledInstance(600,455,1);
    private JPanel nickpanel;
    private JTextField nickToFill;
    private JButton submitButton;
    private JLabel textLabel;
    private JLabel invalidNickLabel;
    private int playerId;
    private String attemptedNick = "";
    private PropertyChangeSupport nickPanelListener = new PropertyChangeSupport(this);
    private ArrayList<String> invalidNicknames = new ArrayList<>();

    public NickNamePanel(int id) {
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
        textLabel.setText("Type your nickname");
        textLabel.setForeground(Color.WHITE);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nickToFill = new JTextField();
        nickToFill.setFont(font);
        nickToFill.setHorizontalAlignment(JTextField.CENTER);
        invalidNickLabel = new JLabel();
        invalidNickLabel.setFont(errorFont);
        invalidNickLabel.setForeground(Color.RED);
        nickpanel = new JPanel(new BorderLayout());
        nickpanel.setBackground(new Color(0, 0, 0, 0));
        nickpanel.add(nickToFill, BorderLayout.NORTH);
        nickpanel.add(invalidNickLabel, BorderLayout.CENTER);
        nickpanel.setBorder(BorderFactory.createEmptyBorder(50,200,60,200));
        submitButton = new JButton("Submit");
        submitButton.setFont(font);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setVerticalAlignment(0);
        submitButton.setSize(80,40);
        invalidNickLabel.setHorizontalAlignment(0);
        playerId = id;
        this.add(Box.createVerticalStrut(120));
        this.add(textLabel);
        this.add(Box.createVerticalStrut(0));
        this.add(nickpanel);
        this.add(Box.createVerticalStrut(0));
        //this.add(submitButton);
        nickpanel.add(submitButton, BorderLayout.SOUTH);
        this.add(Box.createVerticalStrut(80));
        submitButton.addActionListener(this);
        //nickToFill.addActionListener(this);
        invalidNickLabel.setText(" ");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
        g.drawImage(this.imageNick, 100, 72, this);
        //g.drawImage(this.imagePanel, 100, 90, this);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(submitButton)) {
            invalidNickLabel.setText("nickname already assigned!");

            System.out.println("BUTTON PRESSED");
            boolean valid = true;
            for (String s : invalidNicknames) {
                if (s.equals(nickToFill.getText())) {
                    valid = false;
                    break;
                }
            }
            if(valid){
                nickPanelListener.firePropertyChange("nicknameReceived", null, nickToFill.getText());
                submitButton.setEnabled(false);
                attemptedNick = nickToFill.getText();
            } else {
                nickToFill.setText("");
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
