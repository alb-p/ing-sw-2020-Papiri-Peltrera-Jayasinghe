package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class GeneralGodsSelectionPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private PropertyChangeSupport generalGodSelectionListener = new PropertyChangeSupport(this);
    private ArrayList<String[]> gods;
    private ArrayList<JButton> godsButton = new ArrayList<>();
    private ArrayList<String> selectedGods = new ArrayList<>();
    private JButton submit;
    private int playerPerGame;

    public GeneralGodsSelectionPanel(ModelView model) {
        gods = model.getGods();
        for (String[] s : gods) {
            String god = s[0];
            JButton button = new JButton(god);
            godsButton.add(button);
            button.addActionListener(this);
        }
        playerPerGame = model.getPlayers().size();
        submit = new JButton("Submit");
        this.setLayout(new FlowLayout());
        this.add(submit);
        for (JButton b : godsButton){
            this.add(b);
        }
        submit.setEnabled(false);


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(submit)){
            generalGodSelectionListener.firePropertyChange("godsSelected", false, selectedGods);
        } else {
            String buttonText = ((JButton) actionEvent.getSource()).getText();
            boolean found = false;
            for (String selected : selectedGods) {
                if (selected.equalsIgnoreCase(buttonText)) {
                    found = true;
                    ((JButton) actionEvent.getSource()).setForeground(Color.BLACK);
                    selectedGods.remove(selected);
                    break;
                }
            }
            if (!found) {
                for (String[] s : gods) {
                    if (s[0].equalsIgnoreCase(buttonText)) {
                        selectedGods.add(s[0]);
                        ((JButton) actionEvent.getSource()).setForeground(Color.RED);
                        if (selectedGods.size() > playerPerGame) {
                            for (JButton b : godsButton) {
                                if (b.getText().equalsIgnoreCase(selectedGods.get(0))) {
                                    b.setForeground(Color.BLACK);
                                }
                            }
                            selectedGods.remove(0);
                        }
                    }
                }
            }
        }
        submit.setEnabled(selectedGods.size() == playerPerGame);
        System.out.println(selectedGods);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

    }


    public void addGeneralGodSelectionListener(PropertyChangeListener listener) {
        generalGodSelectionListener.addPropertyChangeListener(listener);
    }

}
