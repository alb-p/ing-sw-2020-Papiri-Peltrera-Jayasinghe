package it.polimi.ingsw.view.GUIpackage.Components;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.utils.messages.ColorMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ChooseColorPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private int playerId;
    private Color attemptedColor = null;
    private JButton colorButton1;
    private JButton colorButton2;
    private JButton colorButton3;
    private ArrayList<Color> colors = new ArrayList<>();
    private PropertyChangeSupport colorPanelListener = new PropertyChangeSupport(this);


    public ChooseColorPanel(int id) {
        Collections.addAll(colors, Color.values());
        playerId = id;
        colorButton1 = new JButton("BLUE");
        colorButton1.addActionListener(this);
        colorButton2 = new JButton("RED");
        colorButton2.addActionListener(this);
        colorButton3 = new JButton("WHITE");
        colorButton3.addActionListener(this);

        this.add(colorButton1);
        this.add(colorButton2);
        this.add(colorButton3);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Color c = null;
        if (actionEvent.getSource().equals(colorButton1)){
            c = Color.BLUE;
        }else if (actionEvent.getSource().equals(colorButton2)){
            c = Color.RED;
        } else if (actionEvent.getSource().equals(colorButton3)){
            c = Color.WHITE;
        }
        attemptedColor = c;
        this.colorPanelListener.firePropertyChange("colorReceived", null, c);

    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("colorConfirm")) {
            ColorMessage message = (ColorMessage) propertyChangeEvent.getNewValue();
            colors.remove(message.getColor());
            pickButton(message.getColor()).setEnabled(false);
            if(message.getId()!=playerId && message.getColor().equals(attemptedColor)){
                ((CardLayout)this.getParent().getLayout()).show(this.getParent(), "ColorPanel");
            }
        }
    }

    private JButton pickButton(Color c) {
        if (c.equals(Color.BLUE)) {
            return colorButton1;
        } else if (c.equals(Color.RED)) {
            return colorButton2;
        } else {
            return colorButton3;
        }
    }


    public void addColorPanelListener(PropertyChangeListener listener) {
        colorPanelListener.addPropertyChangeListener(listener);
    }

}
