package it.polimi.ingsw.view.GUIpackage.panel;

import it.polimi.ingsw.view.GUIpackage.components.CustomButton;
import it.polimi.ingsw.view.GUIpackage.components.GodsButton;
import it.polimi.ingsw.view.GUIpackage.GUI;
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

/**
 * The type General gods selection panel.
 * it is the screen where the godly chooses
 * the god cards of the game
 */
public class GeneralGodsSelectionPanel extends JPanel implements ActionListener, MouseListener, PropertyChangeListener {

    private final PropertyChangeSupport generalGodSelectionListener = new PropertyChangeSupport(this);
    private final ArrayList<String[]> gods;
    private final ArrayList<GodsButton> godsButton = new ArrayList<>();
    private final ArrayList<String> selectedGods = new ArrayList<>();
    private final JLabel title;
    private final JButton submit;
    private int playerPerGame = 0;
    private ImageIcon imagedx;
    private final ImageIcon imagesx;
    private Dimension frameDimension;
    private final JPanel paneldx;


    /**
     * Instantiates a new General gods selection panel.
     *
     * @param model the model
     */
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
        title.setForeground(new Color(255, 235, 140));
        FlowLayout flow = new FlowLayout(FlowLayout.CENTER, 30,30);
        submit = new CustomButton("/GodSelection/next/next");
        submit.setName("submit");
        playerPerGame = model.getPlayers().size();
        gods = model.getGods();
        godsList.setLayout(flow);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        panelsx.setLayout(new BoxLayout(panelsx, BoxLayout.Y_AXIS));
        frameDimension= GUI.getDimension();
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

        title.setFont(font.deriveFont(Font.PLAIN,frameDimension.width/15));
        godsList.add(submit);
        panelsx.add(Box.createRigidArea(new Dimension(0,(int) (frameDimension.height/30))));
        panelsx.add(title);
        panelsx.add(Box.createRigidArea(new Dimension(0,(int) (frameDimension.height/18))));
        panelsx.add(godsList);
        this.add(panelsx);
        this.add(paneldx);
    }

    /**
     * when the submit button is pressed, a notify is
     * sent to the GUI class
     * @param actionEvent the action event
     */
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
    }

    /**
     * when all players have set the nickname a notification
     * is sent from the ModelView
     *
     * @param propertyChangeEvent the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("nicknameConfirm")) {
            playerPerGame++;
            title.setText("Select "+ playerPerGame + " gods");
        }
    }


    /**
     * Add general god selection listener.
     *
     * @param listener the listener
     */
    public void addGeneralGodSelectionListener(PropertyChangeListener listener) {
        generalGodSelectionListener.addPropertyChangeListener(listener);
    }

    /**
     * Mouse clicked.
     *
     * @param e the e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Mouse pressed.
     *
     * @param e the e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Mouse released.
     *
     * @param e the e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     *
     * when the mouse hovers over the
     * god icon, its characteristics are
     * shown in detail
     *
     * @param e the event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() instanceof GodsButton) {
            imagedx=new ImageIcon(this.getClass().getResource("/GodSelection/"+
                    ((GodsButton) e.getSource()).getName().toLowerCase()+" info.jpg"));
            paneldx.repaint();
        }
    }

    /**
     * Mouse exited.
     *
     * @param e the e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
