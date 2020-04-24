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


    public VirtualView(ArrayList<SocketClientConnection> connections) {         // crea arraylist dei scc
        this.connections = connections;
    }


    public void addVirtualViewListener(PropertyChangeListener listener) {       //aggiunge listener della virtualview
        virtualViewListeners.addPropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getNewValue() instanceof Message) {
            Message message = (Message) evt.getNewValue();
            if (evt.getPropertyName().equals("sendNick")) {                     //richiede nick ad un client (nick precedente non valido)
                getConnection(message.getId()).send(message);
            } else if (evt.getPropertyName().equals("sendColor")) {             //richiede colore ad un client che ha dato nick
                getConnection(message.getId()).send(message);
            } else if (evt.getPropertyName().equals("delColor")) {              //cancella colore perchè scelto da qualcuno e notifica gli altri dei nuovi colori
                for (SocketClientConnection c : connections) {
                    if (c.getId() != ((Message) evt.getNewValue()).getId())
                        c.send(message);
                }
            } else if (evt.getPropertyName().equals("initialCards")) {//richiede le carte divinità tra cui scegliere a un giocatore random
                //SocketClientConnection a=getConnection(message.getId());
                getConnection(message.getId()).send(message);
            } else if (evt.getPropertyName().equals("sendGod")) {             //richiede divnità al player
                getConnection(message.getId()).send(message);
            } else if (evt.getPropertyName().equals("delGod")) {              //cancella colore perchè scelto da qualcuno e notifica gli altri dei nuovi colori
                for (SocketClientConnection c : connections) {
                    if (c.getId() != ((Message) evt.getNewValue()).getId())
                        c.send(message);
                }
            } else if (evt.getPropertyName().equals("sendAction")) {
                getConnection(message.getId()).send(message);
                for (SocketClientConnection c : connections) {
                    if (c.getId() != ((Message) evt.getNewValue()).getId())
                        c.send(new WaitingMessage(message.getMessage()));
                    else c.send(message);
                }

                // manda che quellaltro stagiocando e te aspetti
            } else if (evt.getPropertyName().equals("deltaUpdate")) {
                for (SocketClientConnection c : connections) c.send(message);
            }else if (evt.getPropertyName().equals("gameReady")){
                for (SocketClientConnection c : connections) c.send(message);
                notifyGameReady();
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

        for (SocketClientConnection c : connections) {   //invia a tutti WelcomeMessage e richiesta  inserimento nick
            c.send(new WelcomeMessage("X"));
            try {
                sleep(1314);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c.send(new NicknameMessage());
        }


    }


    public void receiveNick(NicknameMessage message) {  //arriva nick da scc che viene inoltrato a GameHandler (controller)
        virtualViewListeners.firePropertyChange("nickMessageResponse", null, message);
    }

    public void receiveColor(ColorMessage message) {    //arriva scelta colore da scc che viene inoltrato a GameHandler (controller)
        virtualViewListeners.firePropertyChange("colorMessageResponse", null, message);
    }

    public void receiveAction(ActionMessage message) {  //arriva action da scc che viene inoltrato a GameHandler (controller)
        virtualViewListeners.firePropertyChange("actionMessageResponse", null, message);
    }

    public void receiveInitialCards(InitialCardsMessage message) {        //arriva scelta dei tre god da scc che viene inoltrato a GameHandler (controller)
        virtualViewListeners.firePropertyChange("initialCardsResponse", null, message);
    }

    public void receiveGod(GodMessage message) {        //arriva scelta god da scc che viene inoltrato a GameHandler (controller)
        virtualViewListeners.firePropertyChange("godMessageResponse", null, message);
    }

    public void notifyGameReady(){
        virtualViewListeners.firePropertyChange("gameReady", null, true);
    }

    private SocketClientConnection getConnection(int i) { //restituisce il scc associato all'id (ad es. di un messaggio)
        for (SocketClientConnection c : connections) {
            if (c.getId() == i) return c;
        }
        return null;
    }

    public void receiveListGods(InitialCardsMessage message) {
        virtualViewListeners.firePropertyChange("initialCardsResponse", null, message);
    }
}
