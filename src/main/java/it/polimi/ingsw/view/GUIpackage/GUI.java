package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.Action;
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


/**
 * The type Gui.
 */
public class GUI extends RemoteView implements Runnable, PropertyChangeListener {
    private ModelView modelView;
    private SocketServerConnection connection;
    private MainJFrame window;
    private Integer numOfPlayers = 1;

    /**
     * Gets dimension.
     *
     * @return the dimension
     */
    public static Dimension getDimension() {
        return new Dimension(960, 720);
    }

    private PropertyChangeSupport guiListeners = new PropertyChangeSupport(this);

    /**
     * Instantiates a new Gui.
     *
     * @param connection the connection
     */
    public GUI(SocketServerConnection connection) {
        super(connection);
        this.connection = connection;
        this.modelView = getModelView();

    }

    /**
     * Add gui listener.
     *
     * @param listener the listener
     */
    public void addGuiListener(PropertyChangeListener listener) {
        guiListeners.addPropertyChangeListener(listener);
    }

    /**
     * Player has lost.
     *
     * @param message the message
     */
    @Override
    protected void playerHasLost(GenericMessage message) {
        super.playerHasLost(message);
        guiListeners.firePropertyChange("playerHasLost", null , message);
    }

    /**
     * Winner detected.
     *
     * @param message the message
     */
    @Override
    protected void winnerDetected(WinnerMessage message) {
        guiListeners.firePropertyChange("winnerDetected", false, message);
    }

    /**
     * End turn.
     *
     * @param message the message
     */
    @Override
    protected void endTurn(NicknameMessage message) {
        super.endTurn(message);
        guiListeners.firePropertyChange("endTurnConfirm", false, true);
    }

    /**
     * Sets worker.
     *
     * @param message the message
     */
    @Override
    protected void setWorker(WorkerMessage message) {
        super.setWorker(message);
        guiListeners.firePropertyChange("workerConfirm", null, message);
    }

    /**
     * Sets first player.
     *
     * @param message the message
     */
    @Override
    protected void setFirstPlayer(NicknameMessage message) {
        super.setFirstPlayer(message);
        if (modelView.getGodlyId() == getPlayerId()) {
            window.startIslandAnimation();
        }
    }

    /**
     * Actions available.
     *
     * @param message the message
     */
    @Override
    protected void actionsAvailable(ActionMessage message) {
        super.actionsAvailable(message);
    }

    /**
     * Assigned god.
     *
     * @param message the message
     */
    @Override
    protected void assignedGod(GodMessage message) {
        super.assignedGod(message);
        if (modelView.getChosenGods().isEmpty()) {
            if (modelView.getGodlyId() == getPlayerId()) {
                ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "FirstPlayerSelectionPanel");
            } else window.startIslandAnimation();
        } else if (modelView.getActualPlayerId() == getPlayerId()) {
            guiListeners.firePropertyChange("myTurn", false, true);
        }
    }

    /**
     * Chosen gods.
     *
     * @param newValue the new value
     */
    @Override
    protected void chosenGods(InitialCardsMessage newValue) {
        super.chosenGods(newValue);
        if (modelView.getActualPlayerId() == getPlayerId()) {
            guiListeners.firePropertyChange("myTurn", false, true);
        }
        ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "GodSelectionPanel");

    }

    /**
     * Sets godly.
     *
     * @param message the message
     */
    @Override
    protected void setGodly(GodlyMessage message) {
        super.setGodly(message);
        if (message.getId() == getPlayerId()) {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "GeneralGodsSelectionPanel");

        } else {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "GodlySelectingWaitingPanel");

        }
    }

    /**
     * Color received.
     *
     * @param message the message
     */
    @Override
    protected void colorReceived(ColorMessage message) {
        super.colorReceived(message);
        if (message.getId() == getPlayerId()) {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
        }
    }

    /**
     * Nickname received.
     *
     * @param message the message
     */
    @Override
    protected void nicknameReceived(NicknameMessage message) {
        super.nicknameReceived(message);
        if (message.getId() == getPlayerId()) {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "ColorPanel");//color panel
        }


    }
//GODLY RECEIVED

    /**
     * Game ready.
     */
    @Override
    protected void gameReady() {
        ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "NicknamePanel");
        guiListeners.firePropertyChange("playerID", null, getPlayerId());
    }

    /**
     * Choose number of players setup message.
     *
     * @return the setup message
     */
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

    /**
     * Run.
     */
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

    /**
     * Property change.
     *
     * @param evt the evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("play")) {
           ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
            new Thread(getConnection()).start();
        } else if (evt.getPropertyName().equalsIgnoreCase("numberOfPlayers")) {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
            synchronized (this) {
                numOfPlayers = (Integer) evt.getNewValue();
                notify();
            }
        } else if (evt.getPropertyName().equalsIgnoreCase("nicknameReceived")) {
            NicknameMessage message = new NicknameMessage();
            message.setNickname((String) evt.getNewValue());
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
            connection.sendEvent(new PropertyChangeEvent(this, "notifyNickname", null, message));

        } else if (evt.getPropertyName().equalsIgnoreCase("colorReceived")) {
            ColorMessage message = new ColorMessage(getPlayerId());
            message.setColor((Color) evt.getNewValue());
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "GodlySelectingWaitingPanel");
            connection.sendEvent(new PropertyChangeEvent(this, "notifyColor", null, message));

        } else if (evt.getPropertyName().equalsIgnoreCase("godsSelected")) {
            InitialCardsMessage message = new InitialCardsMessage();
            ArrayList<String> selectedGods = (ArrayList<String>) evt.getNewValue();
            for (String s : selectedGods) message.addToSelectedList(s);
            getConnection().sendEvent(new PropertyChangeEvent(this, "notify1ofNGod", false, message));
            System.out.println("SENDED GODS");
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");

        } else if (evt.getPropertyName().equalsIgnoreCase("godSelected")) {
            GodMessage message = new GodMessage();
            message.setGod((String) (evt.getNewValue()));
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "GodSelectionWaitingPanel");
            getConnection().sendEvent(new PropertyChangeEvent(this, "notifyGod", null, message));
        } else if (evt.getPropertyName().equalsIgnoreCase("firstPlayerSelected")) {
            NicknameMessage nicknameMessage;
            nicknameMessage = (NicknameMessage) evt.getNewValue();
            getConnection().sendEvent(new PropertyChangeEvent(this, "firstPlayerSelected", null, nicknameMessage));
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "InitialWaitingPanel");
        } else if (evt.getPropertyName().equalsIgnoreCase("workerReceived")) {
            WorkerMessage message = (WorkerMessage) evt.getNewValue();
            getConnection().sendEvent(new PropertyChangeEvent(this, "notifyWorker", null, message));
        } else if (evt.getPropertyName().equalsIgnoreCase("actionRequest")) {
            connection.sendEvent(new PropertyChangeEvent(this, "actionsRequest",
                    null, new GenericMessage()));
        } else if (evt.getPropertyName().equalsIgnoreCase("actionReceived")) {
            ActionMessage message= new ActionMessage();
            message.setAction((Action) evt.getNewValue());
            connection.sendEvent(new PropertyChangeEvent(this, "notifyAction", null, message));
        } else if(evt.getPropertyName().equalsIgnoreCase("end turn")){
            Message message = (GenericMessage) evt.getNewValue();
            connection.sendEvent(new PropertyChangeEvent(this, "endTurn", null, message));
            
        }
    }
}
