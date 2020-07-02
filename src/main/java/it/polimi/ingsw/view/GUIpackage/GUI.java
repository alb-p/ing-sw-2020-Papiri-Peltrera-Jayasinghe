package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.network.client.SocketServerConnection;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.ModelView;
import it.polimi.ingsw.view.RemoteView;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The type Gui.
 * subclass of remoteview
 */
public class GUI extends RemoteView implements Runnable, PropertyChangeListener {
    private final ModelView modelView;
    private final SocketServerConnection connection;
    private MainJFrame window;
    private Integer numOfPlayers = 1;
    Logger logger = Logger.getLogger("gui.view");
    /**
     * Gets dimension.
     *
     * @return the dimension
     */
    public static Dimension getDimension() {
        return new Dimension(960, 720);
    }

    private final PropertyChangeSupport guiListeners = new PropertyChangeSupport(this);

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

    @Override
    protected void invalidAlert() {
        ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "invalidAlertPanel");
    }

    @Override
    protected void endGame() {
        if(modelView.getWinnerId()==-1)
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "endGamePanel");

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
     * Fire event of playerHasLost
     *
     * @param message the message
     */
    @Override
    protected void playerHasLost(GenericMessage message) {
        super.playerHasLost(message);
        guiListeners.firePropertyChange("playerHasLost", null , message);
    }

    /**
     * Fire event of winnerDetected
     *
     * @param message the message
     */
    @Override
    protected void winnerDetected(WinnerMessage message) {
        guiListeners.firePropertyChange("winnerDetected", false, message);
    }

    /**
     * Fire event of endTurnConfirm
     *
     * @param message the message
     */
    @Override
    protected void endTurn(NicknameMessage message) {
        super.endTurn(message);
        guiListeners.firePropertyChange("endTurnConfirm", false, true);
    }

    /**
     * Fire event of workerConfirm
     *
     * @param message the message
     */
    @Override
    protected void setWorker(WorkerMessage message) {
        super.setWorker(message);
        guiListeners.firePropertyChange("workerConfirm", null, message);
    }

    /**
     * Sets first player and start island animation
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
     * manages the assignment of the gods
     * and behaviors after this phase
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
     *
     * manages the phase of choosing the god card
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
     * Sets godly and redirect to next screen
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
     * manages the receipt of the color confirmation
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
     * manages the receipt of the nickname confirmation
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
     * is started when all participants of the game have joined
     */
    @Override
    protected void gameReady() {
        ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "NicknamePanel");
        guiListeners.firePropertyChange("playerID", null, getPlayerId());
    }

    /**
     * starts when you press the play button
     * and allows you to choose the number of players
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
            logger.log(Level.SEVERE, e.getMessage());
        }
        message.setField(numOfPlayers);

        return message;
    }

    /**
     * opens the window and starts the first screen (logo animation)
     */
    @Override
    public void run() {
        //create and show gui
        try {
            window = new MainJFrame(this, modelView);
        } catch (IOException | FontFormatException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        window.setVisible(true);
        window.startLogo();
    }

    /**
     * Handle events fired from client's panels
     *
     * @param evt the evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("play")) {
           ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "WaitingNumberOfPlayers");
            new Thread(getConnection()).start();
        } else if (evt.getPropertyName().equalsIgnoreCase("numberOfPlayers")) {
            ((CardLayout) window.getContentPane().getLayout()).show(window.getContentPane(), "WaitingNumberOfPlayers");
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
        } else if(evt.getPropertyName().equalsIgnoreCase("playerToDisconnect")){
            connection.sendEvent(new PropertyChangeEvent(this, "playerDisconnected", null, true));
        }
    }
}
