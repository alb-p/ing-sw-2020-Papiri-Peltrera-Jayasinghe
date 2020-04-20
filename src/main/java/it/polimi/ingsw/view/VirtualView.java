package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.SocketClientConnection;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;
import it.polimi.ingsw.utils.messages.WelcomeMessage;
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
        if (evt.getPropertyName().equals("sendNick")) {
            //manda a client un nicknameMessage
            //manda a quello specifico
        } else if (evt.getPropertyName().equals("sendColor")) {
            //manda a client specifico la scelta del colore
        } else if (evt.getPropertyName().equals("delColor")) {
            ColorMessage message = (ColorMessage) evt.getNewValue();
            //invia a tutti tranne uno che i colori sono cambiati

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

        for(SocketClientConnection c: connections){

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




    public void playersSetup() {

    }

    private void serializeAsk(int i) {
        new Thread() {
            public void run() {
                try {
                    fun(2);

                } catch (Exception e) {
                }
            }
        }.start();
    }

    public void fun(int i) throws IOException, ClassNotFoundException {
        String val = "VAL";

        while (!val.equals("VAL")) {
            val = connections.get(i).askNick();
        }

    }

}
