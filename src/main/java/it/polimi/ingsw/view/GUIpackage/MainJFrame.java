package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.view.GUIpackage.Components.ChooseColorPanel;
import it.polimi.ingsw.view.GUIpackage.MainPanel;
import it.polimi.ingsw.view.ModelView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainJFrame extends JFrame implements PropertyChangeListener {

    MainPanel contentPane;
    CardLayout layout;
    LogoPanel logo;

    public MainJFrame(GUI gui, ModelView modelView){

        this.setDefaultCloseOperation(MainJFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
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
        contentPane.add( "InitialWaitingPanel", new InitialWaitingPanel());
        HomePanel homePanel =  new HomePanel();
        homePanel.addHomePanelListener(gui);
        contentPane.add( "HomePanel",homePanel);
        NumberOfPlayerPanel numberOfPlayerPanel = new NumberOfPlayerPanel();
        numberOfPlayerPanel.addHomePanelListener(gui);
        contentPane.add("chooseNumberOfPlayers", numberOfPlayerPanel);
        NickNamePanel nickPane= new NickNamePanel(gui.getPlayerId());
        contentPane.add("NicknamePanel",nickPane);
        nickPane.addNicknamePanelListener(gui);
        modelView.addNicknameListener(nickPane);
        ChooseColorPanel colorPanel = new ChooseColorPanel(gui.getPlayerId());
        colorPanel.addColorPanelListener(gui);
        modelView.addColorListener(colorPanel);
        contentPane.add("ColorPanel", colorPanel);


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
