package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.view.GUIpackage.Components.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * The type Home panel.
 */
public class HomePanel extends JPanel implements ActionListener {
    private PropertyChangeSupport homePanelListeners = new PropertyChangeSupport(this);
    private Image image;
    private CustomButton playButton;
    private CustomButton helpButton;
    private CustomButton exitButton;
    private CustomButton settingsButton;
    private JLayeredPane layeredPane;
    private JPanel home;
    private JPanel settings;
    Dimension frameDimension;
    MakeSound music;


    /**
     * Instantiates a new Home panel.
     *
     * @param d the d
     */
    public HomePanel(Dimension d) {
        this.setLayout(new BorderLayout());
        layeredPane=new JLayeredPane();
        home=new JPanel();
        home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));
        home.setBounds(0,0,GUI.getDimension().width,GUI.getDimension().height);
        home.setOpaque(false);
        music=new MakeSound();







        frameDimension=d;

        this.image = new ImageIcon(this.getClass().getResource("/Home/Home_title.jpg")).getImage();


        playButton = new CustomButton("/Home/play");

        helpButton = new CustomButton("/Home/help");
        settingsButton = new CustomButton("/Home/settings");
        exitButton = new CustomButton("/Home/exit");
        playButton.addActionListener(this);
        helpButton.addActionListener(this);
        settingsButton.addActionListener(this);
        exitButton.addActionListener(this);
        home.add(Box.createVerticalStrut((int) (frameDimension.height/2)));
        home.add(playButton);
        home.add(Box.createVerticalStrut(frameDimension.height/24));
        home.add(helpButton);
        home.add(Box.createVerticalStrut(frameDimension.height/24));
        home.add(settingsButton);
        home.add(Box.createVerticalStrut(frameDimension.height/24));
        home.add(exitButton);

        layeredPane.add(home,JLayeredPane.DEFAULT_LAYER);
        this.add(layeredPane);
    }

    /**
     * Paint component.
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
    }



    /**
     * Action performed.
     *
     * @param actionEvent the action event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(playButton)){
            homePanelListeners.firePropertyChange("play", null,true);
        } else if (actionEvent.getSource().equals(exitButton)){
            System.exit(0);
        }else if (actionEvent.getSource().equals(helpButton)){
            if (Desktop.isDesktopSupported()) {
                try {
                    Path tempOutput = Files.createTempFile("rules_tempFile", ".pdf");
                    tempOutput.toFile().deleteOnExit();
                    try (InputStream file = getClass().getResourceAsStream("/Home/rules.pdf")) {
                        Files.copy(file, tempOutput, StandardCopyOption.REPLACE_EXISTING);
                    }
                    Desktop.getDesktop().open(tempOutput.toFile());

                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }
        }else if (actionEvent.getSource().equals(settingsButton)) {
            settings=new Settings();
            this.layeredPane.add(settings, JLayeredPane.MODAL_LAYER);
        }
        }

    /**
     * Add home panel listener.
     *
     * @param listener the listener
     */
    public void addHomePanelListener(PropertyChangeListener listener) {
        homePanelListeners.addPropertyChangeListener(listener);
    }

    public void startMusic(){
        music.playSound("/Sounds/track1.wav",-20f,true);
    }

    public void stopMusic() {
        music.stopSound();
    }


    public class Settings extends JPanel implements ActionListener{

        Font font;

        Image background;
        JButton musicButton;
        JButton animationButton;
        CustomButton confirm;


        public Settings(){
            this.setBounds(200,300,559,371);
            this.background = new ImageIcon(this.getClass().getResource("/Home/settings.png")).getImage();
            this.setLayout(new GridLayout(3,2,0,30));
            this.setOpaque(false);
            this.setBorder(BorderFactory.createEmptyBorder(50,40,30,40));

            try {
                font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/CustomFont.otf")); //upload font
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            musicButton =new JButton(new ImageIcon(this.getClass().getResource("/Home/OnSounds.png")));
            musicButton.setContentAreaFilled(false);//trasparenza
            musicButton.setOpaque(false);
            musicButton.setBorder(null);
            musicButton.setName("music on");
            animationButton =new JButton(new ImageIcon(this.getClass().getResource("/Home/OnAnimation.png")));
            animationButton.setContentAreaFilled(false);//trasparenza
            animationButton.setOpaque(false);
            animationButton.setBorder(null);
            animationButton.setName("animation on");
            confirm=new CustomButton("/GodSelection/next/next");

            confirm.addActionListener(this);
            musicButton.addActionListener(this);
            animationButton.addActionListener(this);

            JLabel musicLabel=new JLabel("Music");
            musicLabel.setFont(font.deriveFont(Font.PLAIN,40));
            JLabel animationLabel=new JLabel("Sea animation");
            animationLabel.setFont(font.deriveFont(Font.PLAIN,40));



            this.add(musicLabel);
            this.add(musicButton);
            this.add(animationLabel);
            this.add(animationButton);
            this.add(confirm);



        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.background, 0, 0, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(confirm)){
                layeredPane.remove(settings);
                home.repaint();
            } else if (e.getSource().equals(musicButton)){
                if(musicButton.getName().equalsIgnoreCase("music on")){
                    musicButton.setName("music off");
                    musicButton.setIcon(new ImageIcon(this.getClass().getResource("/Home/OffSounds.png")));
                    music.stopSound();
                    homePanelListeners.firePropertyChange("SoundsOff", null,true);
                }else
                {
                    musicButton.setName("music on");
                    musicButton.setIcon(new ImageIcon(this.getClass().getResource("/Home/OnSounds.png")));
                    music.playSound("/Sounds/track1.wav",-20f,true);
                    homePanelListeners.firePropertyChange("SoundsOn", null,true);
                }
            }else if (e.getSource().equals(animationButton)){
                if(animationButton.getName().equalsIgnoreCase("animation on")){
                    animationButton.setName("animation off");
                    animationButton.setIcon(new ImageIcon(this.getClass().getResource("/Home/OffAnimation.png")));
                    homePanelListeners.firePropertyChange("SeaAnimation", null,false);

                }else
                {
                    animationButton.setName("animation on");
                    animationButton.setIcon(new ImageIcon(this.getClass().getResource("/Home/OnAnimation.png")));
                    homePanelListeners.firePropertyChange("SeaAnimation", null,true);

                }
            }


        }
    }
}