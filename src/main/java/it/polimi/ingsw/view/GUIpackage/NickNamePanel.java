package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.utils.messages.NicknameMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class NickNamePanel extends JPanel implements ActionListener , PropertyChangeListener{

    private JTextField nickToFill;
    private JButton submitButton;
    private JLabel textLabel;
    private int playerId;
    private String attemptedNick = "";
    private PropertyChangeSupport nickPanelListener = new PropertyChangeSupport(this);
    private ArrayList<String> invalidNicknames = new ArrayList<>();

    public NickNamePanel(int id) {
        nickToFill = new JTextField();
        nickToFill.setToolTipText("insert nickname");
        JToolTip tool = new JToolTip();
        textLabel = new JLabel();
        submitButton = new JButton("SUBMIT");
        textLabel.setText("NONE");
        playerId = id;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(220));
        this.add(nickToFill);
        this.add(Box.createVerticalStrut(40));
        this.add(submitButton);
        this.add(Box.createVerticalStrut(40));
        this.add(textLabel);
        submitButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(submitButton)) {
            System.out.println("BUTTON PRESSED");
            textLabel.setText(nickToFill.getText());
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
            }
            else {
                nickToFill.setToolTipText("insert nickname, invalid nick selected");
                nickToFill.setText("");
            }

        }
    }

    public void addNicknamePanelListener(PropertyChangeListener listener) {
        nickPanelListener.addPropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("nicknameConfirm")){
            this.textLabel.setText(((NicknameMessage)propertyChangeEvent.getNewValue()).getNickname());
            NicknameMessage message  = (NicknameMessage) propertyChangeEvent.getNewValue();
            invalidNicknames.add(message.getNickname());
            if(message.getNickname().equals(attemptedNick) && playerId!=message.getId()){
                submitButton.setEnabled(true);
                ((CardLayout)this.getParent().getLayout()).show(this.getParent(),"NicknamePanel");
            }
        }
    }
}
