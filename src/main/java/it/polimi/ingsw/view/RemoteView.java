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

    public void notifyEvent(PropertyChangeEvent evt) {

        String propertyName = evt.getPropertyName();
        if (propertyName.equalsIgnoreCase("colorsAvailable")) {

        } else if (propertyName.equalsIgnoreCase("deltaUpdate")) {
            this.modelView.getBoard().setSlot((VirtualSlot) evt.getNewValue());
        } else if (propertyName.equalsIgnoreCase("deltaUpdate")) {

        } else if (propertyName.equalsIgnoreCase("deltaUpdate")) {

        } else if (propertyName.equalsIgnoreCase("deltaUpdate")) {

        } else if (propertyName.equalsIgnoreCase("deltaUpdate")) {

        } else if (propertyName.equalsIgnoreCase("deltaUpdate")) {

        } else if (propertyName.equalsIgnoreCase("deltaUpdate")) {

        }
    }
}
