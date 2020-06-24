package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.view.GUIpackage.Components.ChooseColorPanel;
import it.polimi.ingsw.view.GUIpackage.Components.IslandAnimationPanel;
import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class MainJFrame extends JFrame implements PropertyChangeListener {

    private final MainPanel contentPane;
    private final CardLayout layout;
    private final LogoPanel logo;
    private final IslandAnimationPanel islandAnimationPanel;
    private final PlayPanel playPanel;
    private final MakeSound music= new MakeSound();

    public MainJFrame(GUI gui, ModelView modelView) throws IOException, FontFormatException {

        this.setDefaultCloseOperation(MainJFrame.EXIT_ON_CLOSE);
        this.setSize(GUI.getDimension());
        this.setUndecorated(true); //nasconde titlebar
        this.setResizable(true);
        // window.setBackground(new Color(0, 0, 0, 0)); //trasperenza
        this.setLocationRelativeTo(null); //posiziona al centro all'apertura
        layout = new CardLayout();
        contentPane = new MainPanel(this);
        contentPane.setLayout(layout);
        this.setContentPane(contentPane);
        logo = new LogoPanel();
        contentPane.add("LogoPanel", logo);
        contentPane.add( "InitialWaitingPanel", new WaitingPanel("Waiting for players..."));
        HomePanel homePanel =  new HomePanel(this.getSize());
        homePanel.addHomePanelListener(gui);
        contentPane.add( "HomePanel",homePanel);
        NumberOfPlayerPanel numberOfPlayerPanel = new NumberOfPlayerPanel(this.getSize());
        numberOfPlayerPanel.addHomePanelListener(gui);
        contentPane.add("chooseNumberOfPlayers", numberOfPlayerPanel);
        NicknamePanel nickPane= new NicknamePanel(gui.getPlayerId());
        contentPane.add("NicknamePanel",nickPane);
        nickPane.addNicknamePanelListener(gui);
        modelView.addNicknameListener(nickPane);
        ChooseColorPanel colorPanel = new ChooseColorPanel(gui.getPlayerId(),this.getSize());
        colorPanel.addColorPanelListener(gui);
        modelView.addColorListener(colorPanel);
        contentPane.add("ColorPanel", colorPanel);
        GeneralGodsSelectionPanel generalGodsSelectionPanel = new GeneralGodsSelectionPanel(modelView);
        generalGodsSelectionPanel.addGeneralGodSelectionListener(gui);
        contentPane.add( "GodlySelectingWaitingPanel", new WaitingPanel("Godly is selecting gods..."));
        contentPane.add("GeneralGodsSelectionPanel", generalGodsSelectionPanel);
        modelView.addNicknameListener(generalGodsSelectionPanel); //to know how many players in the game
        GodSelectionPanel godSelectionPanel = new GodSelectionPanel();
        gui.addGuiListener(godSelectionPanel);
        modelView.addSelectedGodsListener(godSelectionPanel);
        modelView.addNicknameListener(godSelectionPanel);
        modelView.addSelectedSingleGodListener(godSelectionPanel);
        godSelectionPanel.addGodSelectionListener(gui);
        contentPane.add("GodSelectionPanel", godSelectionPanel);
        contentPane.add( "GodSelectionWaitingPanel", new WaitingPanel("Opponent is selecting gods..."));
        FirstPlayerSelectionPanel firstPlayerSelectionPanel = new FirstPlayerSelectionPanel();
        gui.addGuiListener(firstPlayerSelectionPanel);
        modelView.addSelectedSingleGodListener(firstPlayerSelectionPanel);
        firstPlayerSelectionPanel.addFirstPlayerSelectionListener(gui);
        contentPane.add("FirstPlayerSelectionPanel", firstPlayerSelectionPanel);
        playPanel = new PlayPanel(modelView);
        modelView.addBoardListener(playPanel);
        modelView.addFirstPlayerListener(playPanel);
        modelView.addActionListener(playPanel);
        gui.addGuiListener(playPanel);
        playPanel.addPlayPanelListener(gui);
        contentPane.add("PlayPanel", playPanel);
        islandAnimationPanel= new IslandAnimationPanel();
        contentPane.add("IslandAnimationPanel", islandAnimationPanel);

    }
    public void startLogo(){
        layout.show(getContentPane(),"LogoPanel");
        this.logo.addPropertyChangeListener(this);
        this.logo.startTransition();
    }
    public void startIslandAnimation(){
        layout.show(getContentPane(),"IslandAnimationPanel");
        this.islandAnimationPanel.addPropertyChangeListener(this);
        this.islandAnimationPanel.startTransition();
        music.stopSound();
        music.playSound("/Sounds/environment.wav",-20f,true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("logoTransitionEnded")){
            layout.show(getContentPane(),"HomePanel");
            music.playSound("/Sounds/track1.wav",-20f,true);

        } else if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("islandTransitionEnded")){
            layout.show(getContentPane(),"PlayPanel");
            playPanel.startSounds();
            playPanel.startAnimation();
        }
    }
}
