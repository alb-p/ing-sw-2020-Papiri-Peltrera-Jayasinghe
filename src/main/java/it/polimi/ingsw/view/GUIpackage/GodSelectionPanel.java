package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.view.GUIpackage.Components.CustomButton;
import it.polimi.ingsw.view.GUIpackage.Components.GodsButton;
import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class GodSelectionPanel extends JPanel implements ActionListener, MouseListener, PropertyChangeListener {

    private PropertyChangeSupport godSelectionListeners = new PropertyChangeSupport(this);
    private int playersPerGame = 0;
    private ArrayList<String[]> selectedGods = new ArrayList<>();
    private ArrayList<GodsButton> godsButton = new ArrayList<>();
    private JPanel buttonPanel;
    private CustomButton submit;
    private JLabel updates;
    private String selectedGod = "";
    private boolean myturn = false;
    private JLabel label;

    private JLabel description;
    private ImageIcon imagedx;
    private ImageIcon imagesx;
    private Dimension frameDimension;
    private JPanel paneldx;

    public GodSelectionPanel() {

        imagedx=new ImageIcon(this.getClass().getResource("/GodSelection/paneldx.jpg"));
        imagesx=new ImageIcon(this.getClass().getResource("/GodSelection/panelsx.jpg"));

        JPanel panelsx = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                imagesx.paintIcon(this,g,0,0);
            }


        };

        paneldx = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                imagedx.paintIcon(this,g,0,0);
            }
        };


        Font font;
        description = new JLabel();

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        panelsx.setLayout(new BoxLayout(panelsx, BoxLayout.Y_AXIS));
        frameDimension=GUI.getDimension();
        paneldx.setPreferredSize(new Dimension((int)(frameDimension.width/2.823),frameDimension.height));
        panelsx.setPreferredSize(new Dimension((int)(frameDimension.width/1.548),frameDimension.height));



        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")).deriveFont(Font.PLAIN, 40); //carica font
        }catch(IOException | FontFormatException e){
            font = label.getFont();
        }





        label = new JLabel("Opponent player is selecting...");
        label.setFont(font.deriveFont(Font.PLAIN,frameDimension.width/30));
        description.setFont(font);
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        submit = new CustomButton("/GodSelection/next/next");
        submit.addActionListener(this);
        updates = new JLabel("");
        panelsx.add(Box.createVerticalStrut(120));
        panelsx.add(label);
        panelsx.add(Box.createVerticalStrut(40));
        panelsx.add(buttonPanel);
        panelsx.add(Box.createVerticalStrut(40));
        panelsx.add(submit);
        panelsx.add(Box.createVerticalStrut(40));
        panelsx.add(updates);
        paneldx.add(description);
        this.add(panelsx);
        this.add(paneldx);
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
                    ((GodsButton) actionEvent.getSource()).setUnselected();
                } else {
                    if (!selectedGod.equals("")) {
                        for (GodsButton b : godsButton) {
                            if (b.getName().equals(selectedGod)) {
                                b.setUnselected();

                            }
                        }
                    }
                    selectedGod = ((JButton) actionEvent.getSource()).getName();
                    ((GodsButton) actionEvent.getSource()).setSelected();
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
            String godName = selectedGods.get(i)[0];
            GodsButton button = new GodsButton(godName);
            godsButton.add(button);
            button.addActionListener(this);
            button.addMouseListener(this);
            button.setName(godName);
            buttonPanel.add(button);
            button.setEnabled(true);
        }

    }

    public void addGodSelectionListener(PropertyChangeListener listener) {
        godSelectionListeners.addPropertyChangeListener(listener);


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() instanceof GodsButton) {

            imagedx=new ImageIcon(this.getClass().getResource("/GodSelection/"+((GodsButton) e.getSource()).getName().toLowerCase()+" info.jpg"));
            paneldx.repaint();
            description.setText(((GodsButton) e.getSource()).getName());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
