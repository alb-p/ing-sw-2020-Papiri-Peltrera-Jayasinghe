package it.polimi.ingsw.view.GUIpackage;

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

public class GeneralGodsSelectionPanel extends JPanel implements ActionListener, MouseListener, PropertyChangeListener {

    private PropertyChangeSupport generalGodSelectionListener = new PropertyChangeSupport(this);
    private ArrayList<String[]> gods;
    private ArrayList<GodsButton> godsButton = new ArrayList<>();
    private ArrayList<String> selectedGods = new ArrayList<>();
    JLabel title;
    JLabel description;
    private JButton submit;
    private int playerPerGame = 0;

    public GeneralGodsSelectionPanel(ModelView model) {
        JPanel sxPanel = new JPanel();
        JPanel godsList = new JPanel();
        JPanel selectedGod = new JPanel();
        selectedGod.addMouseListener(this);
        Font font;
        title = new JLabel();
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")).deriveFont(Font.PLAIN, 40); //carica font
        }catch(IOException | FontFormatException e){
            font = title.getFont();
        }

        description = new JLabel();
        description.setFont(font);
        selectedGod.setPreferredSize(new Dimension(200,600));
        selectedGod.add(description);


        title.setFont(font);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));


        sxPanel.setPreferredSize(new Dimension(600,600));
        sxPanel.setLayout(new BoxLayout(sxPanel, BoxLayout.Y_AXIS));
        sxPanel.add(title);


        gods = model.getGods();
        for (String[] s : gods) {
            String god = s[0];
            GodsButton button = new GodsButton(god);
            button.setName(god);
            godsButton.add(button);
            button.addActionListener(this);
            button.addMouseListener(this);
        }
        playerPerGame = model.getPlayers().size();
        submit = new CustomButton("/Home/help");
        submit.setName("submit");
        FlowLayout flow = new FlowLayout(FlowLayout.CENTER, 10,10);
        godsList.setLayout(flow);

        for (JButton b : godsButton){
            godsList.add(b);
        }
        godsList.add(submit);
        submit.setEnabled(false);

        submit.addActionListener(this);
        sxPanel.add(godsList);
        this.add(sxPanel);
        this.add(selectedGod);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(submit)){
            generalGodSelectionListener.firePropertyChange("godsSelected", false, selectedGods);
        } else {
            String buttonText = ((JButton) actionEvent.getSource()).getName();
            boolean found = false;
            for (String selected : selectedGods) {
                if (selected.equalsIgnoreCase(buttonText)) {
                    found = true;
                    ((GodsButton) actionEvent.getSource()).setUnselected();
                    selectedGods.remove(selected);
                    break;
                }
            }
            if (!found) {
                for (String[] s : gods) {
                    if (s[0].equalsIgnoreCase(buttonText)) {
                        selectedGods.add(s[0]);
                        ((GodsButton) actionEvent.getSource()).setSelected();
                        if (selectedGods.size() > playerPerGame) {
                            for (GodsButton b : godsButton) {
                                if (b.getName().equalsIgnoreCase(selectedGods.get(0))) {
                                    b.setUnselected();
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
        if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("nicknameConfirm")) {
            playerPerGame++;
            title.setText("Select "+ playerPerGame + " gods");
        }
    }


    public void addGeneralGodSelectionListener(PropertyChangeListener listener) {
        generalGodSelectionListener.addPropertyChangeListener(listener);
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
        System.out.println("MOUSEENTERED!!");
        if(e.getSource() instanceof GodsButton) {
            description.setText(((GodsButton) e.getSource()).getName());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
