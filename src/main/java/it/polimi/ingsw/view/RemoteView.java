package it.polimi.ingsw.view;

import it.polimi.ingsw.model.VirtualSlot;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;

public abstract class RemoteView {
    //client invoca funzioni di questa classe per richiedere input
    // all'utente a seguito di richieste specifiche
    private ModelView modelView = new ModelView();
    private Client connection;
    private int id;

    public static Object getMonitor() {
        return monitor;
    }

    final static Object monitor = new Object();

    public RemoteView(Client connection) {
        this.connection = connection;
    }
    public RemoteView(){}

    public Client getConnection() {return connection;}

    public ModelView getModelView() {
        return modelView;
    }

    public void notifyEvent(PropertyChangeEvent evt) {

        String propertyName = evt.getPropertyName();
        if (propertyName.equalsIgnoreCase("colorConfirm")) {
            this.colorReceived((ColorMessage)evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("deltaUpdate")) {
            this.modelView.getBoard().setSlot((VirtualSlot) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("god1ofNConfirmed")) {
            chosenGods((InitialCardsMessage)evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("actionsAvailable")) {

        } else if (propertyName.equalsIgnoreCase("nicknameConfirm")) {
            this.nicknameReceived((NicknameMessage)evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("currPlayerUpdate")) {

        }else if (propertyName.equalsIgnoreCase("godlySelected")) { //godlySelected contains godlyMessage
            this.setGodly(((GodlyMessage)evt.getNewValue()).getId());
        } else if (propertyName.equalsIgnoreCase("freeWorkerPositions")) {

        } else if (propertyName.equalsIgnoreCase("godConfirm")) {
//TODO cathcarlo in thread del client e svegliare il prossimo actualplayer incrementandolo (toglierlo dal while 377CLI)
        } else if (propertyName.equalsIgnoreCase("gameReady")) {
            setPlayerId((int) evt.getNewValue());
            gameReady();
        }
    }

    protected abstract void chosenGods(InitialCardsMessage newValue);

    protected synchronized void setGodly(int godlyId){
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
