package it.polimi.ingsw.view.GUIpackage.panel;

import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.view.GUIpackage.components.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Choose color panel.
 * the screen where each player
 * chooses his own color
 */
public class ChooseColorPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final int playerId;
    private Color attemptedColor = null;
    private final JButton colorButton1;
    private final JButton colorButton2;
    private final JButton colorButton3;
    private final ArrayList<Color> colors = new ArrayList<>();
    private final PropertyChangeSupport colorPanelListener = new PropertyChangeSupport(this);
    private final Dimension frameDimension;
    Image colorBackground;

    /**
     * Instantiates a new Choose color panel.
     *
     * @param id the id
     * @param d  the d
     */
    public ChooseColorPanel(int id, Dimension d) {
        frameDimension=d;

        colorBackground = new ImageIcon(this.getClass().getResource("/Colors/BG.jpg")).getImage();
        colorButton1 = new CustomButton("/Colors/blue");
        colorButton1.addActionListener(this);
        colorButton2 = new CustomButton("/Colors/red");
        colorButton2.addActionListener(this);
        colorButton3 = new CustomButton("/Colors/tan");
        colorButton3.addActionListener(this);


        Collections.addAll(colors, Color.values());
        playerId = id;
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.add(Box.createRigidArea(new Dimension((int) (frameDimension.width/20),0)));
        this.add(colorButton1);
        this.add(Box.createRigidArea(new Dimension((int) (frameDimension.width/10),0)));
        this.add(colorButton2);
        this.add(Box.createRigidArea(new Dimension((int) (frameDimension.width/12),0)));
        this.add(colorButton3);



    }

    /**
     * manages interactions with clicks
     * on the buttons on this screen
     *
     * @param actionEvent the action event
     */
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

    /**
     * Handle events fire from ModelView.
     *
     * @param propertyChangeEvent the property change event
     */
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

    /**
     * return the button associated
     * with the respective color
     *
     * @param c the c
     * @return the j button
     */
    private JButton pickButton(Color c) {
        if (c.equals(Color.BLUE)) {
            return colorButton1;
        } else if (c.equals(Color.RED)) {
            return colorButton2;
        } else {
            return colorButton3;
        }
    }


    /**
     * Add color panel listener.
     *
     * @param listener the listener
     */
    public void addColorPanelListener(PropertyChangeListener listener) {
        colorPanelListener.addPropertyChangeListener(listener);
    }

    /**
     * Paint component.
     * Set the background image
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(this.colorBackground, 0, 0, this);
    }
}
