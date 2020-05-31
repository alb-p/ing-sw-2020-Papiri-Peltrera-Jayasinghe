package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.SocketServerConnection;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.ModelView;
import it.polimi.ingsw.view.RemoteView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;


public class GUI extends RemoteView implements Runnable, PropertyChangeListener {
    private ModelView modelView;
    private SocketServerConnection connection;
    private MainJFrame window;
    private Integer numOfPlayers = new Integer(1);

    public GUI(SocketServerConnection connection) {
        super(connection);
        this.connection = connection;
        this.modelView = getModelView();

    }

    @Override
    protected void playerHasLost(GenericMessage newValue) {

    }

    @Override
    protected void winnerDetected(WinnerMessage newValue) {

    }

    @Override
    protected void endTurn(NicknameMessage message) {

    }

    @Override
    protected void setWorker(WorkerMessage message) {

    }

    @Override
    protected void setFirstPlayer(NicknameMessage message) {

    }

    @Override
    protected void actionsAvailable(ActionMessage newValue) {

    }

    @Override
    protected void assignedGod(GodMessage message) {

    }

    @Override
    protected void chosenGods(InitialCardsMessage newValue) {

    }

    @Override
    protected void colorReceived(ColorMessage message) {
        super.colorReceived(message);
        if(message.getId() == getPlayerId()){
            System.out.println("RICEVUTO MIO COLORE");
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
        }
    }

    @Override
    protected void nicknameReceived(NicknameMessage message) {
        super.nicknameReceived(message);
        if (message.getId() == getPlayerId()) {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "ColorPanel");//color panel
        }


    }
//GODLY RECEIVED

    @Override
    protected void gameReady() {
        ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "NicknamePanel");

    }

    @Override
    protected synchronized SetupMessage chooseNumberOfPlayers() {
        SetupMessage message = new SetupMessage();
        ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "chooseNumberOfPlayers");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        message.setField(numOfPlayers);

        return message;
    }

    @Override
    public void run() {
        //create and show gui
        try {
            window = new MainJFrame(this, modelView);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        window.setVisible(true);
        window.layout.show(window.getContentPane(), "LogoPanel");
        window.startLogo();
    }

    public void changeicon() {
        window.layout.show(window.contentPane, "HomePanel");
        System.out.println("SI HO HOME");
    }

    public void changedualicon() {
        window.layout.show(window.contentPane, "InitialWaitingPanel");
        System.out.println("SI HO WAITING");
    }

    public void changetripleicon() {
        window.layout.show(window.contentPane, "LogoPanel");
        window.startLogo();

        System.out.println("SI HO icon");
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("play")) {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
            getConnection().start();
        } else if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("numberOfPlayers")) {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
            synchronized (this) {
                numOfPlayers = (Integer) propertyChangeEvent.getNewValue();
                notify();
            }
        } else if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("nicknameReceived")) {
            NicknameMessage message = new NicknameMessage();
            message.setNickname((String) propertyChangeEvent.getNewValue());
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
            connection.sendEvent(new PropertyChangeEvent(this, "notifyNickname", null, message));

        } else if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("colorReceived")){
            ColorMessage message = new ColorMessage(getPlayerId());
            message.setColor((Color)propertyChangeEvent.getNewValue());
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
            connection.sendEvent(new PropertyChangeEvent(this,"notifyColor", null ,message));

        }
    }
}
