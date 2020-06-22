package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.view.GUIpackage.Components.CustomButton;
import it.polimi.ingsw.view.GUIpackage.Components.GodsButton;

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

    private final PropertyChangeSupport godSelectionListeners = new PropertyChangeSupport(this);
    private int playersPerGame = 0;
    private final ArrayList<String[]> selectedGods = new ArrayList<>();
    private final ArrayList<GodsButton> godsButton = new ArrayList<>();
    private final JPanel buttonPanel;
    private final CustomButton submit;
    private final JLabel updates;
    private String selectedGod = "";
    private boolean myTurn = false;
    private JLabel label;

    private ImageIcon imagedx;
    private ImageIcon imagesx;
    private ImageIcon imageInnerPanel;
    private Dimension frameDimension;
    private JPanel paneldx;
    Font font;

    public GodSelectionPanel() {

        imagedx=new ImageIcon(this.getClass().getResource("/GodSelection/paneldx.jpg"));
        imagesx=new ImageIcon(this.getClass().getResource("/GodSelection/panelsx.jpg"));
        imageInnerPanel=new ImageIcon(this.getClass().getResource("/GodSelection/innerpanel.png"));

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

        buttonPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                imageInnerPanel.paintIcon(this,g,0,0);
            }

        };

        JPanel innerPanel=new JPanel(new FlowLayout());
        JPanel innerPanel2=new JPanel(new FlowLayout());
        JPanel innerPanel3=new JPanel(new FlowLayout());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        panelsx.setLayout(new BoxLayout(panelsx, BoxLayout.Y_AXIS));
        frameDimension=GUI.getDimension();
        paneldx.setPreferredSize(new Dimension((int)(frameDimension.width/2.823),frameDimension.height));
        panelsx.setPreferredSize(new Dimension((int)(frameDimension.width/1.548),frameDimension.height));
        buttonPanel.setLayout(new GridBagLayout());



        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf"));
        }catch(IOException | FontFormatException e){
            font = label.getFont();
        }



        innerPanel.setOpaque(false);
        innerPanel2.setOpaque(false);
        innerPanel3.setOpaque(false);
        label = new JLabel("Wait, opponent player is selecting...");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setForeground(new Color(255, 235, 140));
        label.setFont(font.deriveFont(Font.PLAIN,40));
        buttonPanel.setOpaque(false);
        submit = new CustomButton("/GodSelection/next/next");
        submit.addActionListener(this);

        updates = new JLabel("");
        updates.setFont(font.deriveFont(Font.PLAIN,20));
        panelsx.add(Box.createRigidArea(new Dimension(50,50)));
        innerPanel3.add(label);
        panelsx.add(innerPanel3);
        panelsx.add(Box.createRigidArea(new Dimension(50,50)));
        panelsx.add(buttonPanel);
        panelsx.add(Box.createRigidArea(new Dimension(50,50)));
        innerPanel2.add(updates);
        panelsx.add(Box.createRigidArea(new Dimension(50,20)));
        panelsx.add(innerPanel2);
        innerPanel.add(submit);
        panelsx.add(innerPanel);
        panelsx.add(Box.createRigidArea(new Dimension(50,70)));


        this.add(panelsx);
        this.add(paneldx);
        submit.setEnabled(false);


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (myTurn) {
            if (actionEvent.getSource().equals(submit)) {
                godSelectionListeners.firePropertyChange("godSelected", false, selectedGod);
                myTurn = false;
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
                    b.setVisible(false);

                    String text = updates.getText();
                    String toAppend = evt.get(0) + " selected " + evt.get(1);
                    updates.setText(text + toAppend+"  ");
                }
            }
        } else if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("myTurn")){
            myTurn =true;
            label.setText("Select your god!");
            label.setFont(font.deriveFont(Font.PLAIN,50));

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
            buttonPanel.add(Box.createHorizontalStrut(50));
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
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
