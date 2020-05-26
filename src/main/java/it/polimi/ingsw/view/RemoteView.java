package it.polimi.ingsw.view;

import it.polimi.ingsw.model.VirtualSlot;
import it.polimi.ingsw.network.SocketServerConnection;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;

public abstract class RemoteView {
    //client invoca funzioni di questa classe per richiedere input
    // all'utente a seguito di richieste specifiche
    private ModelView modelView = new ModelView();
    private SocketServerConnection connection;
    private int id;

    public static Object getMonitor() {
        return monitor;
    }

    final static Object monitor = new Object();

    public RemoteView(SocketServerConnection connection) {
        this.connection = connection;
    }

    public RemoteView() {
    }

    public SocketServerConnection getConnection() {
        return connection;
    }

    public ModelView getModelView() {
        return modelView;
    }

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
            this.setGodly(((GodlyMessage) evt.getNewValue()).getId());
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
            endTurn((NicknameMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("playerLostDetected")) {
            modelView.playerLost(((GenericMessage) evt.getNewValue()).getId());
            playerHasLost((GenericMessage) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("winnerDetected")) {
            modelView.setWinnerId(((WinnerMessage) evt.getNewValue()).getId());
            winnerDetected((WinnerMessage) evt.getNewValue());
        }
    }

    protected abstract void playerHasLost(GenericMessage newValue);

    protected abstract void winnerDetected(WinnerMessage newValue);

    protected abstract void endTurn(NicknameMessage message);

    protected abstract void setWorker(WorkerMessage message);

    protected abstract void setFirstPlayer(NicknameMessage message);

    protected abstract void actionsAvailable(ActionMessage newValue);

    protected abstract void assignedGod(GodMessage message);

    protected abstract void chosenGods(InitialCardsMessage newValue);

    protected synchronized void setGodly(int godlyId) {
        this.modelView.setGodlyId(godlyId);
        godlyReceived();
        notifyAll();
    }

    protected abstract void colorReceived(ColorMessage newValue);

    protected abstract void nicknameReceived(NicknameMessage newValue);

    protected abstract void godlyReceived();

    protected void setPlayerId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return id;
    }

    protected abstract void gameReady();

    public void askNumOfPlayers() {
        SetupMessage message = chooseNumberOfPlayers();
        message.setId(getPlayerId());
        connection.send(message);
    }

    protected abstract SetupMessage chooseNumberOfPlayers();
}
