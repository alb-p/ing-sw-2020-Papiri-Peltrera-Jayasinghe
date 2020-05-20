package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.VirtualSlot;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;
import java.io.Console;

public abstract class RemoteView extends View {
    //client invoca funzioni di questa classe per richiedere input
    // all'utente a seguito di richieste specifiche
    private ModelView modelView;
    private Client connection;

    public RemoteView(Client connection){
        this.connection = connection;
    }

    public void notifyEvent(PropertyChangeEvent evt) {

        String propertyName = evt.getPropertyName();
        if (propertyName.equalsIgnoreCase("colorConfirm")) {

        } else if (propertyName.equalsIgnoreCase("deltaUpdate")) {
            this.modelView.getBoard().setSlot((VirtualSlot) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("godsAvailable")) {

        } else if (propertyName.equalsIgnoreCase("actionsAvailable")) {

        } else if (propertyName.equalsIgnoreCase("currPlayerUpdate")) {

        } else if (propertyName.equalsIgnoreCase("freeWorkerPositions")) {

        } else if (propertyName.equalsIgnoreCase("godConfirm")) {

        } else if (propertyName.equalsIgnoreCase("gameReady")) {
            startMainThread();
        }
    }

    protected abstract void startMainThread();

    public void askNumOfPlayers(){
        SetupMessage message = chooseNumberOfPlayers();
        System.out.println(message.getMessage()+ "  "+ message.getField());
        connection.send(message);
    }

    protected abstract SetupMessage chooseNumberOfPlayers();
}
