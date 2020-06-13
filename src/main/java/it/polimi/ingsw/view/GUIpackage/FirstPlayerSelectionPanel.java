package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.utils.messages.NicknameMessage;
import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class FirstPlayerSelectionPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private JPanel panel = new JPanel();
    private JButton submit;
    private JButton playerSelected;
    private PropertyChangeSupport firstPlayerSelectionListener = new PropertyChangeSupport(this);

    public FirstPlayerSelectionPanel(){

        JLabel title = new JLabel("Choose first player: ");

        submit = new JButton("submit");
        submit.addActionListener(this);
        submit.setEnabled(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(panel);
        this.add(submit);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            if((e.getSource()).equals(submit)){
                firstPlayerSelectionListener.firePropertyChange("firstPlayerSelected",
                        null, new NicknameMessage(0, playerSelected.getName()));
            } else{
                submit.setEnabled(true);
                if(playerSelected != null) playerSelected.setForeground(Color.BLACK);
                playerSelected = ((JButton) e.getSource());
                ((JButton) e.getSource()).setForeground(Color.ORANGE);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equalsIgnoreCase("notifyGodSelected")){
            JButton choosePlayer = new JButton(((ArrayList<String>) evt.getNewValue()).get(0));
            choosePlayer.setName(((ArrayList<String>) evt.getNewValue()).get(0));
            choosePlayer.addActionListener(this);
            panel.add(choosePlayer);
        }
    }

    public void addFirstPlayerSelectionListener (PropertyChangeListener listener){
        firstPlayerSelectionListener.addPropertyChangeListener(listener);
    }
}
