package it.polimi.ingsw.view;

import it.polimi.ingsw.model.VirtualSlot;
import it.polimi.ingsw.network.SocketServerConnection;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;

/**
 * The type Remote view.
 */
public abstract class RemoteView implements Runnable {
    //client invoca funzioni di questa classe per richiedere input
    // all'utente a seguito di richieste specifiche
    private ModelView modelView = new ModelView();
    private SocketServerConnection connection;
    private int id;

    /**
     * Gets monitor.
     *
     * @return the monitor
     */
    public static Object getMonitor() {
        return monitor;
    }

    final static Object monitor = new Object();

    /**
     * Instantiates a new Remote view.
     *
     * @param connection the connection
     */
    public RemoteView(SocketServerConnection connection) {
        this.connection = connection;
    }

    /**
     * Instantiates a new Remote view.
     */
    public RemoteView() {
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public SocketServerConnection getConnection() {
        return connection;
    }

    /**
     * Gets model view.
     *
     * @return the model view
     */
    public ModelView getModelView() {
        return modelView;
    }

    /**
     * Notify event.
     *
     * @param evt the evt
     */
    public void notifyEvent(PropertyChangeEvent evt) {

        String propertyName = evt.getPropertyName();
        if (propertyName.equalsIgnoreCase("gameReady")) {
            setPlayerId((int) evt.getNewValue());
            gameReady();
        } else if (propertyName.equalsIgnoreCase("nicknameConfirm")) {
            this.nicknameReceived((NicknameMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("colorConfirm")) {
            this.colorReceived((ColorMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("godlySelected")) { //godlySelected contains godlyMessage
            this.setGodly(((GodlyMessage) evt.getNewValue()));
        } else if (propertyName.equalsIgnoreCase("god1ofNConfirmed")) {
            chosenGods((InitialCardsMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("godConfirm")) {
            assignedGod((GodMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("firstPlayerConfirmed")) {
            setFirstPlayer((NicknameMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("deltaUpdate")) {
            modelView.updateBoard((VirtualSlot) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("workerConfirm")) {
            setWorker((WorkerMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("actionsAvailable")) {
            actionsAvailable((ActionMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("endTurnConfirm")) {
            modelView.getActionsAvailable().clear();
            endTurn((NicknameMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("playerLostDetected")) {
            System.out.println("PLAYER LOST DETECTED");
            modelView.playerLost(((GenericMessage) evt.getNewValue()).getId());
            playerHasLost((GenericMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("winnerDetected")) {
            modelView.setWinnerId(((WinnerMessage) evt.getNewValue()).getId());
            winnerDetected((WinnerMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("endGame")) {
            connection.closeConnection();
            System.exit(0);
        }
    }

    /**
     * Player has lost.
     *
     * @param message the message
     */
    protected void playerHasLost(GenericMessage message) {
        System.out.println("REMOTE VIEW PLAYER HAS LOST : id__"+message.getId());
        if(message.getId() == modelView.getActualPlayerId())modelView.setNextPlayerId();
    }

    /**
     * Winner detected.
     *
     * @param newValue the new value
     */
    protected abstract void winnerDetected(WinnerMessage newValue);

    /**
     * End turn.
     *
     * @param message the message
     */
    protected void endTurn(NicknameMessage message) {
        modelView.setNextPlayerId();
    }

    /**
     * Sets worker.
     *
     * @param message the message
     */
    protected void setWorker(WorkerMessage message) {
        if (message.getWorkerNumber() == 1) {
            modelView.setNextPlayerId();
        }
    }

    /**
     * Sets first player.
     *
     * @param message the message
     */
    protected void setFirstPlayer(NicknameMessage message) {

        modelView.setActualPlayerId(message.getNickname());
        modelView.setFirstPlayerId(modelView.getPlayer(message.getNickname()).getId());
    }

    /**
     * Actions available.
     *
     * @param message the message
     */
    protected void actionsAvailable(ActionMessage message) {
        //modelView.getActionsAvailable().addAll(message.getChoices());
        modelView.getActionsAvailable().clear();
        modelView.setOptional(message.isOptional());
        modelView.setActionsAvailable(message.getChoices());
    }

    /**
     * Assigned god.
     *
     * @param message the message
     */
    protected void assignedGod(GodMessage message) {
        modelView.setGod(message.getId(), message.getGod());
        modelView.setNextPlayerId();
    }

    /**
     * Chosen gods.
     *
     * @param message the message
     */
    protected void chosenGods(InitialCardsMessage message) {
        modelView.addChosenGods(message.getSelectedList());
    }

    /**
     * Sets godly.
     *
     * @param message the message
     */
    protected void setGodly(GodlyMessage message) {
        modelView.setGodlyId(message.getId());
    }

    /**
     * Color received.
     *
     * @param message the message
     */
    protected void colorReceived(ColorMessage message) {
        modelView.setColor(message.getId(), message.getColor());
    }

    /**
     * Nickname received.
     *
     * @param message the message
     */
    protected void nicknameReceived(NicknameMessage message) {
        modelView.addPlayer(message.getId(), message.getNickname());
    }

    /**
     * Sets player id.
     *
     * @param id the id
     */
    protected void setPlayerId(int id) {
        this.id = id;
    }

    /**
     * Gets player id.
     *
     * @return the player id
     */
    public int getPlayerId() {
        return id;
    }

    /**
     * Game ready.
     */
    protected abstract void gameReady();

    /**
     * Ask num of players.
     */
    public void askNumOfPlayers() {
        SetupMessage message = chooseNumberOfPlayers();
        message.setId(getPlayerId());
        connection.send(message);
    }

    /**
     * Choose number of players setup message.
     *
     * @return the setup message
     */
    protected abstract SetupMessage chooseNumberOfPlayers();
}
