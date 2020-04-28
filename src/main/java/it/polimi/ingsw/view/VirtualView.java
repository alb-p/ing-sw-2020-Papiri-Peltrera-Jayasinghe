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
    private PropertyChangeSupport virtualViewListeners = new PropertyChangeSupport(this);
    public VirtualView(ArrayList<SocketClientConnection> connections) {         // crea arraylist dei scc
        this.connections = connections;
    }


    //invia a tutti i client il messaggio di benvenuto e
    //dopo 1314ms la richiesta di inserimento del nick
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


/*********************************************************************************************************************************/
/***MESSAGGI IN USCITA***/

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getNewValue() instanceof Message) {
            Message message = (Message) evt.getNewValue();

            //richiede nick ad un client (nick precedente non valido)
            if (evt.getPropertyName().equals("sendNick")) {
                getConnection(message.getId()).send(message);
            }

            //richiede colore ad un client che ha dato nick
            else if (evt.getPropertyName().equals("sendColor")) {
                getConnection(message.getId()).send(message);
            }

            //un colore è stato cancellato, notifica gli altri dei nuovi colori
            else if (evt.getPropertyName().equals("delColor")) {
                for (SocketClientConnection c : connections) {
                    if (c.getId() != ((Message) evt.getNewValue()).getId())
                        c.send(message);
                }
            }

            //richiede le carte divinità tra cui scegliere al giocatore prescelto
            else if (evt.getPropertyName().equals("initialCards")) {
                getConnection(message.getId()).send(message);
            }

            //richiede divinità al player
            else if (evt.getPropertyName().equals("sendGod")) {
                getConnection(message.getId()).send(message);
            }

            //richiede all'ultimo player che ha scelto la divinità (il prescelto)
            //chi deve essere il primo giocatore
            else if (evt.getPropertyName().equals("firstPlayer")) {
                getConnection(message.getId()).send(message);
            }

            else if (evt.getPropertyName().equals("setWorker")) {
                getConnection(message.getId()).send(message);
            }

            else if (evt.getPropertyName().equals("sendAction")) {
                System.out.println("SENDACTION VIRTUALVIEW");
                getConnection(message.getId()).send(message);
                for (SocketClientConnection c : connections) {
                    if (c.getId() != ((Message) evt.getNewValue()).getId())
                        c.send(new WaitingMessage(message.getMessage()));
                    else c.send(message);
                }


            } else if (evt.getPropertyName().equals("deltaUpdate")) {
                for (SocketClientConnection c : connections) c.send(message);
            }


            else if (evt.getPropertyName().equals("gameReady")) {
                for (SocketClientConnection c : connections){
                    c.send(message);
                }
                notifyGameReady();
            }
        }
    }




/*********************************************************************************************************************************/
/***MESSAGGI IN ENTRATA***/

    //arriva nick da scc che viene inoltrato a GameHandler (controller)
    public void receiveNick(NicknameMessage message) {
        virtualViewListeners.firePropertyChange("nickMessageResponse", null, message);
    }

    //arriva scelta colore da scc che viene inoltrato a GameHandler (controller)
    public void receiveColor(ColorMessage message) {
        virtualViewListeners.firePropertyChange("colorMessageResponse", null, message);
    }

    //arriva scelta dei tre god da scc che viene inoltrato a GameHandler (controller)
    public void receiveInitialCards(InitialCardsMessage message) {
        virtualViewListeners.firePropertyChange("initialCardsResponse", null, message);
    }

    //arriva scelta god da scc che viene inoltrato a GameHandler (controller)
    public void receiveGod(GodMessage message) {
        virtualViewListeners.firePropertyChange("godMessageResponse", null, message);
    }

    public void receiveFirstPlayer(FirstPlayerMessage message) {
        virtualViewListeners.firePropertyChange("firstPlayerResponse", null, message);
    }

    public void receiveSetWorker(WorkerMessage message) {
        virtualViewListeners.firePropertyChange("setWorkerResponse", null, message);
    }

    //arriva action da scc che viene inoltrato a GameHandler (controller)
    public void receiveAction(ActionMessage message) {
        virtualViewListeners.firePropertyChange("actionMessageResponse", null, message);
    }


    public void notifyGameReady() {
        virtualViewListeners.firePropertyChange("gameReadyResponse", null, true);
    }

    //restituisce il scc associato all'id (ad es. di un messaggio)
    private SocketClientConnection getConnection(int i) {
        for (SocketClientConnection c : connections) {
            if (c.getId() == i) return c;
        }
        return null;
    }

    //aggiunge listener della virtualview
    public void addVirtualViewListener(PropertyChangeListener listener) {
        virtualViewListeners.addPropertyChangeListener(listener);
    }


}
