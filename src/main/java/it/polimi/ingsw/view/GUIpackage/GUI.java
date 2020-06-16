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
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;


public class GUI extends RemoteView implements Runnable, PropertyChangeListener {
    private ModelView modelView;
    private SocketServerConnection connection;
    private MainJFrame window;
    private Integer numOfPlayers = 1;

    public static Dimension getDimension() {
        return new Dimension(960, 720);
    }

    private PropertyChangeSupport guiListeners = new PropertyChangeSupport(this);

    public GUI(SocketServerConnection connection) {
        super(connection);
        this.connection = connection;
        this.modelView = getModelView();

    }

    public void addGuiListener(PropertyChangeListener listener) {
        guiListeners.addPropertyChangeListener(listener);
    }

    @Override
    protected void playerHasLost(GenericMessage message) {
        super.playerHasLost(message);
    }

    @Override
    protected void winnerDetected(WinnerMessage message) {

    }

    @Override
    protected void endTurn(NicknameMessage message) {
        super.endTurn(message);
        guiListeners.firePropertyChange("endTurnConfirm", false ,true);
    }

    @Override
    protected void setWorker(WorkerMessage message) {
        super.setWorker(message);
        guiListeners.firePropertyChange("workerConfirm", null, message);
    }

    @Override
    protected void setFirstPlayer(NicknameMessage message) {
        super.setFirstPlayer(message);
        if(modelView.getGodlyId() == getPlayerId()){
            window.startIslandAnimation();
        }
    }

    @Override
    protected void actionsAvailable(ActionMessage message) {
        super.actionsAvailable(message);
    }

    @Override
    protected void assignedGod(GodMessage message) {
        super.assignedGod(message);

        if (modelView.getChosenGods().isEmpty()) {
            if(modelView.getGodlyId() == getPlayerId()){
                ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "FirstPlayerSelectionPanel");
            } else window.startIslandAnimation();
        } else if (modelView.getActualPlayerId() == getPlayerId()) {
            guiListeners.firePropertyChange("myTurn", false, true);
        }
    }

    @Override
    protected void chosenGods(InitialCardsMessage newValue) {
        super.chosenGods(newValue);
        System.out.println("RECEIVED GODS");
        if (modelView.getActualPlayerId() == getPlayerId()) {
            guiListeners.firePropertyChange("myTurn", false, true);
        }
        ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "GodSelectionPanel");

    }

    @Override
    protected void setGodly(GodlyMessage message) {
        super.setGodly(message);
        if (message.getId() == getPlayerId()) {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "GeneralGodsSelectionPanel");

        } else {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");

        }
    }

    @Override
    protected void colorReceived(ColorMessage message) {
        super.colorReceived(message);
        if (message.getId() == getPlayerId()) {
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
        guiListeners.firePropertyChange("playerID", null, getPlayerId());
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
        window.startLogo();
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

        } else if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("colorReceived")) {
            ColorMessage message = new ColorMessage(getPlayerId());
            message.setColor((Color) propertyChangeEvent.getNewValue());
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
            connection.sendEvent(new PropertyChangeEvent(this, "notifyColor", null, message));

        } else if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("godsSelected")) {
            InitialCardsMessage message = new InitialCardsMessage();
            ArrayList<String> selectedGods = (ArrayList<String>) propertyChangeEvent.getNewValue();
            for (String s : selectedGods) message.addToSelectedList(s);
            getConnection().sendEvent(new PropertyChangeEvent(this, "notify1ofNGod", false, message));
            System.out.println("SENDED GODS");
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");

        } else if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("godSelected")) {
            GodMessage message = new GodMessage();
            message.setGod((String) (propertyChangeEvent.getNewValue()));
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
            getConnection().sendEvent(new PropertyChangeEvent(this, "notifyGod", null, message));
        } else if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("firstPlayerSelected")) {
            NicknameMessage nicknameMessage;
            nicknameMessage =(NicknameMessage) propertyChangeEvent.getNewValue();
            getConnection().sendEvent(new PropertyChangeEvent(this, "firstPlayerSelected", null, nicknameMessage));
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
        } else if (propertyChangeEvent.getPropertyName().equalsIgnoreCase("workerReceived")) {
            WorkerMessage message = (WorkerMessage) propertyChangeEvent.getNewValue();
            getConnection().sendEvent(new PropertyChangeEvent(this, "notifyWorker", null, message));
        } else if(propertyChangeEvent.getPropertyName().equalsIgnoreCase("actionRequest")){
            connection.sendEvent(new PropertyChangeEvent(this, "actionsRequest",
                    null, new GenericMessage()));
        }
    }
}
