package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.view.GUIpackage.panel.*;
import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * The type Main Jframe.
 * it contains all the various screens of the game
 */
public class MainJFrame extends JFrame implements PropertyChangeListener {

    private final MainPanel contentPane;
    private final CardLayout layout;
    private final LogoPanel logo;
    private final IslandAnimationPanel islandAnimationPanel;
    private final PlayPanel playPanel;
    private final HomePanel homePanel;

    /**
     * Instantiates a new Main j frame.
     *
     * @param gui       the gui
     * @param modelView the model view
     * @throws IOException         the io exception
     * @throws FontFormatException the font format exception
     */
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
        contentPane.add( "InitialWaitingPanel", new WaitingPanel("Waiting for players...",gui));
        homePanel =  new HomePanel(this.getSize());
        homePanel.addHomePanelListener(gui);
        contentPane.add( "HomePanel",homePanel);
        NumberOfPlayerPanel numberOfPlayerPanel = new NumberOfPlayerPanel(this.getSize());
        numberOfPlayerPanel.addHomePanelListener(gui);
        contentPane.add("chooseNumberOfPlayers", numberOfPlayerPanel);
        NicknamePanel nickPane= new NicknamePanel(gui.getPlayerId());
        contentPane.add("NicknamePanel",nickPane);
        nickPane.addNicknamePanelListener(gui);
        modelView.addNicknameListener(nickPane);
        EndGamePanel endGamePanel = new EndGamePanel();
        contentPane.add(endGamePanel, "endGamePanel");
        ChooseColorPanel colorPanel = new ChooseColorPanel(gui.getPlayerId(),this.getSize());
        colorPanel.addColorPanelListener(gui);
        modelView.addColorListener(colorPanel);
        contentPane.add("ColorPanel", colorPanel);
        GeneralGodsSelectionPanel generalGodsSelectionPanel = new GeneralGodsSelectionPanel(modelView);
        generalGodsSelectionPanel.addGeneralGodSelectionListener(gui);
        contentPane.add( "GodlySelectingWaitingPanel", new WaitingPanel("Godly is selecting gods...",gui));
        contentPane.add("GeneralGodsSelectionPanel", generalGodsSelectionPanel);
        modelView.addNicknameListener(generalGodsSelectionPanel); //to know how many players in the game
        GodSelectionPanel godSelectionPanel = new GodSelectionPanel();
        gui.addGuiListener(godSelectionPanel);
        modelView.addSelectedGodsListener(godSelectionPanel);
        modelView.addNicknameListener(godSelectionPanel);
        modelView.addSelectedSingleGodListener(godSelectionPanel);
        godSelectionPanel.addGodSelectionListener(gui);
        contentPane.add("GodSelectionPanel", godSelectionPanel);
        contentPane.add( "GodSelectionWaitingPanel", new WaitingPanel("Opponent is selecting gods...",gui));
        FirstPlayerSelectionPanel firstPlayerSelectionPanel = new FirstPlayerSelectionPanel();
        gui.addGuiListener(firstPlayerSelectionPanel);
        modelView.addSelectedSingleGodListener(firstPlayerSelectionPanel);
        firstPlayerSelectionPanel.addFirstPlayerSelectionListener(gui);
        contentPane.add("FirstPlayerSelectionPanel", firstPlayerSelectionPanel);
        playPanel = new PlayPanel(modelView,homePanel);
        modelView.addBoardListener(playPanel);
        modelView.addFirstPlayerListener(playPanel);
        modelView.addActionListener(playPanel);
        gui.addGuiListener(playPanel);
        playPanel.addPlayPanelListener(gui);
        contentPane.add("PlayPanel", playPanel);
        islandAnimationPanel= new IslandAnimationPanel();
        contentPane.add("IslandAnimationPanel", islandAnimationPanel);

    }

    /**
     * Start logo animation.
     */
    public void startLogo(){
        layout.show(getContentPane(),"LogoPanel");
        this.logo.addPropertyChangeListener(this);
        this.logo.startTransition();
    }

    /**
     * Start island animation and musics
     */
    public void startIslandAnimation(){
        layout.show(getContentPane(),"IslandAnimationPanel");
        this.islandAnimationPanel.addPropertyChangeListener(this);
        this.islandAnimationPanel.startTransition();
        homePanel.stopMusic();
        playPanel.startSounds();

    }

    /**
     * Handle the events launched
     * by LogoPanel and IslandAnimationPanel
     *
     * @param propertyChangeEvent the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("logoTransitionEnded")){
            layout.show(getContentPane(),"HomePanel");
            homePanel.startMusic();

        } else if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("islandTransitionEnded")){
            layout.show(getContentPane(),"PlayPanel");
            playPanel.startAnimation();
        }
    }

}
