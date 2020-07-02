package it.polimi.ingsw.view;

import it.polimi.ingsw.utils.VirtualSlot;
import it.polimi.ingsw.network.client.SocketServerConnection;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;

/**
 * The type Remote view is extended by CLI and GUI.
 * Represents the view in the MVC pattern.
 */
public abstract class RemoteView implements Runnable {

    private final ModelView modelView = new ModelView();
    private SocketServerConnection connection;
    private int id;

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
     * Method called by SocketServerConnection whenever
     * a new event is received by the client.
     *
     * @param evt the evt
     */
    public void notifyEvent(PropertyChangeEvent evt) {

        String propertyName = evt.getPropertyName();
        if (propertyName.equalsIgnoreCase("gameReady")) {
            setPlayerId((int) evt.getNewValue());
            gameReady();
        } else if (propertyName.equalsIgnoreCase("invalidServer")) {
            this.invalidAlert();
        }else if (propertyName.equalsIgnoreCase("nicknameConfirm")) {
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
            modelView.playerLost(((GenericMessage) evt.getNewValue()).getId());
            playerHasLost((GenericMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("winnerDetected")) {
            modelView.setWinnerId(((WinnerMessage) evt.getNewValue()).getId());
            winnerDetected((WinnerMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("endGame")) {
            endGame();
        }
    }

    protected abstract void invalidAlert();

    protected abstract void endGame();

    /**
     * Player has lost.
     * Notifies to the player that someone has lost.
     *
     * @param message the message
     */
    protected void playerHasLost(GenericMessage message) {
        if (message.getId() == modelView.getActualPlayerId()) modelView.setNextPlayerId();
    }

    /**
     * Winner detected.
     * Notifies to the player that someone has won.
     *
     * @param newValue the new value
     */
    protected abstract void winnerDetected(WinnerMessage newValue);

    /**
     * End turn.
     * Notifies to the player that someone has ended his turn.
     *
     * @param message the message
     */
    protected void endTurn(NicknameMessage message) {
        modelView.setNextPlayerId();
    }

    /**
     * Sets worker.
     * Notifies to the player that someone has setup his worker.
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
     * Notifies to the player that godly has chosen the firstPlayer.
     *
     * @param message the message
     */
    protected void setFirstPlayer(NicknameMessage message) {

        modelView.setActualPlayerId(message.getNickname());
        modelView.setFirstPlayerId(modelView.getPlayer(message.getNickname()).getId());
    }

    /**
     * Actions available.
     * Notifies to the player that the actual player has available the actions in the message.
     *
     * @param message the message
     */
    protected void actionsAvailable(ActionMessage message) {
        modelView.getActionsAvailable().clear();
        modelView.setOptional(message.isOptional());
        modelView.setActionsAvailable(message.getChoices());
    }

    /**
     * Assigned god.
     * Notifies to the player that someone has chosen his god.
     *
     * @param message the message
     */
    protected void assignedGod(GodMessage message) {
        modelView.setGod(message.getId(), message.getGod());
        modelView.setNextPlayerId();
    }

    /**
     * Chosen gods.
     * Notifies to the player that godly has chosen the gods.
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
     * Notifies to the player that someone has chosen his color.
     *
     * @param message the message
     */
    protected void colorReceived(ColorMessage message) {
        modelView.setColor(message.getId(), message.getColor());
    }

    /**
     * Nickname received.
     * Notifies to the player that someone has chosen his nickname.
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
