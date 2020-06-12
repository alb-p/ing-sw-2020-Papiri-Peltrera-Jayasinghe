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
    private JLabel title;
    private JLabel description;
    private JButton submit;
    private int playerPerGame = 0;
    private ImageIcon imagedx;
    private ImageIcon imagesx;
    private Dimension frameDimension;
    private JPanel paneldx;


    public GeneralGodsSelectionPanel(ModelView model) {

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

        JPanel godsList = new JPanel();
        Font font;
        title = new JLabel("Select gods");
        description = new JLabel();
        FlowLayout flow = new FlowLayout(FlowLayout.CENTER, 30,30);
        submit = new CustomButton("/GodSelection/next/next");



        submit.setName("submit");
        playerPerGame = model.getPlayers().size();
        gods = model.getGods();
        godsList.setLayout(flow);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        panelsx.setLayout(new BoxLayout(panelsx, BoxLayout.Y_AXIS));
        frameDimension=GUI.getDimension();
        submit.setEnabled(false);
        godsList.setOpaque(false);
        submit.addActionListener(this);
        paneldx.setPreferredSize(new Dimension((int)(frameDimension.width/2.823),frameDimension.height));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelsx.setPreferredSize(new Dimension((int)(frameDimension.width/1.548),frameDimension.height));


        for (String[] s : gods) {
            String god = s[0];
            GodsButton button = new GodsButton(god);
            button.setName(god);
            godsButton.add(button);
            button.addActionListener(this);
            button.addMouseListener(this);
        }




        for (JButton b : godsButton){
            godsList.add(b);
        }


        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")).deriveFont(Font.PLAIN, 40); //carica font
        }catch(IOException | FontFormatException e){
            font = title.getFont();
        }

        description.setFont(font);
        title.setFont(font.deriveFont(Font.PLAIN,frameDimension.width/15));







        godsList.add(submit);
        paneldx.add(description);
        panelsx.add(Box.createRigidArea(new Dimension(0,(int) (frameDimension.height/30))));
        panelsx.add(title);
        panelsx.add(Box.createRigidArea(new Dimension(0,(int) (frameDimension.height/18))));
        panelsx.add(godsList);
        this.add(panelsx);
        this.add(paneldx);
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
