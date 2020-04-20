package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.SocketClientConnection;
import it.polimi.ingsw.utils.messages.*;
import org.w3c.dom.ls.LSOutput;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;

public class VirtualView implements Runnable, PropertyChangeListener {

    private ArrayList<SocketClientConnection> connections;

    private ArrayList<Color> colorSet = new ArrayList<Color>();
    private PropertyChangeSupport virtualViewListeners = new PropertyChangeSupport(this);


    public VirtualView(ArrayList<SocketClientConnection> connections) {
        this.connections = connections;
        colorSet.addAll(Arrays.asList(Color.values()));

    }


    /**********************************************************************************************/
    public void addVirtualViewListener(PropertyChangeListener listener) {
        virtualViewListeners.addPropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //io sono in ascolto sia del model che del socketCC.
        if (evt.getNewValue() instanceof Message) {
            Message message = (Message)evt.getNewValue();
            if (evt.getPropertyName().equals("sendNick")) {
                //manda a client un nicknameMessage
                //manda a quello specifico
                getConnection(message.getId()).send(evt);
            } else if (evt.getPropertyName().equals("sendColor")) {
                //manda a client specifico la scelta del colore
                getConnection(message.getId()).send(evt);
            } else if (evt.getPropertyName().equals("delColor")) {
                message = (ColorMessage)message;

                //invia a tutti tranne uno che i colori sono cambiati

            } else if (evt.getPropertyName().equals("sendAction")){
                getConnection(message.getId()).send(evt);

            }
        }

    }

    /************************************************************************************************/


    public void run() {


        System.out.println("\n" +
                "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░╔╦═╦╗░░░░░░░░░░░░░╔╗░░░░░░░░░░░░\n" +
                "░░░░░║║║║╠═╦╗╔═╦═╦══╦═╗║╚╦═╗░░░░░░░░░\n" +
                "░░░░░║║║║║╩╣╚╣═╣╬║║║║╩╣║╔╣╬║░░░░░░░░░\n" +
                "░░░░░╚═╩═╩═╩═╩═╩═╩╩╩╩═╝╚═╩═╝░░░░░░░░░\n" +
                "░░░░░╔══╗░░░░░╔╗░░░░╔╗░░╔╗░░░░░░░░░░░\n" +
                "░░░░░║══╬═╗╔═╦╣╚╦═╦╦╬╬═╦╬╣░░░░░░░░░░░\n" +
                "░░░░░╠══║╬╚╣║║║╔╣╬║╔╣║║║║║░░░░░░░░░░░\n" +
                "░░░░░╚══╩══╩╩═╩═╩═╩╝╚╩╩═╩╝░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n" +
                "SE");

        for (SocketClientConnection c : connections) {

            c.send(new WelcomeMessage("X"));
            try {
                sleep(1314);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c.send(new NicknameMessage());
        }


    }


    public void receiveNick(NicknameMessage message) {
        virtualViewListeners.firePropertyChange("nickMessageResponse", null, message);
    }

    public void receiveColor(ColorMessage message) {
        virtualViewListeners.firePropertyChange("colorMessageResponse", null, message);
    }

    public void receiveAction(ActionMessage message) {
        virtualViewListeners.firePropertyChange("actionMessageResponse", null, message);
    }

    public void receiveGod(GodMessage message) {
        virtualViewListeners.firePropertyChange("godMessageResponse", null, message);
    }

    private SocketClientConnection getConnection(int i) {
        for (SocketClientConnection c : connections) {
            if (c.getId() == i) return c;
        }
        return null;
    }
}
