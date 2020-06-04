package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.view.GUIpackage.Components.ChooseColorPanel;
import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class MainJFrame extends JFrame implements PropertyChangeListener {

    MainPanel contentPane;
    CardLayout layout;
    LogoPanel logo;

    public MainJFrame(GUI gui, ModelView modelView) throws IOException, FontFormatException {

        this.setDefaultCloseOperation(MainJFrame.EXIT_ON_CLOSE);
        this.setSize(960 ,720);
        this.setUndecorated(true); //nasconde titlebar
        this.setResizable(false);
        // window.setBackground(new Color(0, 0, 0, 0)); //trasperenza
        this.setLocationRelativeTo(null); //posiziona al centro all'apertura
        layout = new CardLayout();
        contentPane = new MainPanel(this);
        contentPane.setLayout(layout);
        this.setContentPane(contentPane);
        logo = new LogoPanel();
        contentPane.add("LogoPanel", logo);
        contentPane.add( "InitialWaitingPanel", new InitialWaitingPanel(this.getSize()));
        HomePanel homePanel =  new HomePanel(this.getSize());
        homePanel.addHomePanelListener(gui);
        contentPane.add( "HomePanel",homePanel);
        NumberOfPlayerPanel numberOfPlayerPanel = new NumberOfPlayerPanel(this.getSize());
        numberOfPlayerPanel.addHomePanelListener(gui);
        contentPane.add("chooseNumberOfPlayers", numberOfPlayerPanel);
        NicknamePanel nickPane= new NicknamePanel(gui.getPlayerId(),this.getSize());
        contentPane.add("NicknamePanel",nickPane);
        nickPane.addNicknamePanelListener(gui);
        modelView.addNicknameListener(nickPane);
        ChooseColorPanel colorPanel = new ChooseColorPanel(gui.getPlayerId());
        colorPanel.addColorPanelListener(gui);
        modelView.addColorListener(colorPanel);
        contentPane.add("ColorPanel", colorPanel);
        GeneralGodsSelectionPanel generalGodsSelectionPanel = new GeneralGodsSelectionPanel(modelView);
        generalGodsSelectionPanel.addGeneralGodSelectionListener(gui);
        contentPane.add("GeneralGodsSelectionPanel", generalGodsSelectionPanel);
        modelView.addNicknameListener(generalGodsSelectionPanel); //to know how many players in the game
        GodSelectionPanel godSelectionPanel = new GodSelectionPanel();
        gui.addGuiListener(godSelectionPanel);
        modelView.addSelectedGodsListener(godSelectionPanel);
        modelView.addNicknameListener(godSelectionPanel);
        modelView.addSelectedSingleGodListener(godSelectionPanel);
        godSelectionPanel.addGodSelectionListener(gui);
        contentPane.add("GodSelectionPanel", godSelectionPanel);
    }
    public void startLogo(){
        this.logo.addPropertyChangeListener(this);
        this.logo.startTransition();
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("logoTransitionEnded")){
            layout.show(getContentPane(),"HomePanel");
        }
    }
}
