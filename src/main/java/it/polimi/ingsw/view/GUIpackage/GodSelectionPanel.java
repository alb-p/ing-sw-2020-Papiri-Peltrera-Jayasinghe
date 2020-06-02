package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class GodSelectionPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private PropertyChangeSupport godSelectionListeners = new PropertyChangeSupport(this);
    private int playersPerGame = 0;
    private ArrayList<String[]> selectedGods = new ArrayList<>();
    private ArrayList<JButton> godsButton = new ArrayList<>();
    private JPanel buttonPanel;
    private JButton submit;
    private JLabel updates;
    private String selectedGod = "";
    private boolean myturn = false;
    private JLabel label;

    public GodSelectionPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel("Wait to select your god");
        buttonPanel = new JPanel(new FlowLayout());
        submit = new JButton("Submit");
        submit.addActionListener(this);
        updates = new JLabel("");
        this.add(Box.createVerticalStrut(120));
        this.add(label);
        this.add(Box.createVerticalStrut(40));
        this.add(buttonPanel);
        this.add(Box.createVerticalStrut(40));
        this.add(submit);
        this.add(Box.createVerticalStrut(40));
        this.add(updates);

        submit.setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (myturn) {
            if (actionEvent.getSource().equals(submit)) {
                godSelectionListeners.firePropertyChange("godSelected", false, selectedGod);
                myturn = false;
            } else {
                if (selectedGod.equals(((JButton) actionEvent.getSource()).getName())) {
                    selectedGod = "";
                    ((JButton) actionEvent.getSource()).setForeground(Color.BLACK);
                } else {
                    if (!selectedGod.equals("")) {
                        for (JButton b : godsButton) {
                            if (b.getName().equals(selectedGod)) {
                                b.setForeground(Color.BLACK);
                            }
                        }
                    }
                    selectedGod = ((JButton) actionEvent.getSource()).getName();
                    ((JButton) actionEvent.getSource()).setForeground(Color.RED);
                }
            }
            submit.setEnabled(!selectedGod.equals(""));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

        if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("nicknameConfirm")) {
            playersPerGame++;
        } else if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("notifySelectedGods")) {
            selectedGods.addAll((ArrayList<String[]>) propertyChangeEvent.getNewValue());
            setupButtons();
        } else if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("notifyGodSelected")) {
            ArrayList<String> evt = (ArrayList<String>) propertyChangeEvent.getNewValue();
            for (JButton b : godsButton) {
                if (b.getName().equals(evt.get(1))) {
                    b.setEnabled(false);
                    b.setForeground(Color.RED.brighter());
                    String text = updates.getText();
                    String toAppend = evt.get(0) + " selected " + evt.get(1);
                    updates.setText(text + "\n" + toAppend);
                }
            }
        } else if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("myTurn")){
            myturn=true;
            label.setText("Select your god!");
        }

    }


    private void setupButtons() {

        for (int i = 0; i < playersPerGame; i++) {
            JButton button = new JButton();
            godsButton.add(button);
            button.addActionListener(this);
            String godName = selectedGods.get(i)[0];
            button.setText(godName);
            button.setName(godName);
            buttonPanel.add(button);
            button.setEnabled(true);
        }

    }

    public void addGodSelectionListener(PropertyChangeListener listener) {
        godSelectionListeners.addPropertyChangeListener(listener);
    }
}
