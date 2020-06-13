package it.polimi.ingsw.network;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.VirtualSlot;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class SocketClientConnection implements Runnable, PropertyChangeListener {

    private Socket socket;
    private ObjectOutputStream outSocket;
    private ObjectInputStream inSocket;
    private PropertyChangeSupport sccListeners = new PropertyChangeSupport(this);
    private Logger logger = Logger.getLogger("network.scc");

    private int id;
    private Server server;

    private boolean active = true;

    public SocketClientConnection(Socket newSocket) {
        try {
            socket = newSocket;
            outSocket = new ObjectOutputStream(newSocket.getOutputStream());
            inSocket = new ObjectInputStream(newSocket.getInputStream());
            logger.log(Level.INFO, "Connection created");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private synchronized boolean isActive() {
        return active;
    }


    public synchronized void send(Object message) {
        try {
            outSocket.reset();
            outSocket.writeObject(message);
            outSocket.flush();

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    public int askNumOfPlayers() {
        int read = 0;
        try {
            //outSocket = new ObjectOutputStream(socket.getOutputStream());
            send(new SetupMessage());
            read = ((SetupMessage) inSocket.readObject()).getField();
            while (!(read == 2 || read == 3)) {
                send(new SetupMessage());
                read = ((SetupMessage) inSocket.readObject()).getField();
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return read;
    }

    public void notifyGamePlaying() {
        send("The Lobby is full! Please Wait");
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getId() {
        return id;
    }


    @Override
    public void run() {
        try {
            sendEvent(new PropertyChangeEvent(this, "gameReady", null, id));
            while (socket.isConnected()) {
                Object inputObject = inSocket.readObject();
                if (inputObject instanceof PropertyChangeEvent &&
                        ((PropertyChangeEvent) inputObject).getNewValue() instanceof Message
                        && ((Message) ((PropertyChangeEvent) inputObject).getNewValue()).getId() == id) {
                    receiveDebug((PropertyChangeEvent)inputObject);
                    sccListeners.firePropertyChange((PropertyChangeEvent) inputObject);
                }
            }

        } catch (Exception e) {

            try {
                socket.close();
            } catch (IOException ioException) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    private void receiveDebug(PropertyChangeEvent inputObject) {
        System.out.println("\n\n\t\t |RECEIVED EVENT");
        System.out.println("\t\t |"+inputObject.getPropertyName());
        System.out.println("\t\t |"+"ID SENDER "+id);
        System.out.println("\t\t | ______________________________");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        sendEvent(evt);
        if (evt.getPropertyName().equalsIgnoreCase("endGame")){
            try {
                socket.close();
                System.out.println("DOPO ENDGAME CHIUDO CONNESSIONE "+id);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    public void sendEvent(PropertyChangeEvent evt) {
        debug(evt);
        try {
            synchronized (outSocket) {
                outSocket.reset();
                outSocket.writeObject(evt);
                outSocket.flush();
            }
        } catch (IOException e) {
            try {
                socket.close();
                System.out.println("Connection "+ id+ " SOCKET CLOSED");
            } catch (IOException ioException) {
                logger.log(Level.SEVERE, e.getMessage());
            }finally {
                sccListeners.firePropertyChange("playerDisconnected", this, id);
            }
        }

    }

    private void debug(PropertyChangeEvent evt) {
        System.out.println("| "+"---DEBUG ID "+id+" "+"---DEBUG ID "+id+" "+"---DEBUG ID "+id+" "+"---DEBUG ID "+id+"\n|\n|\n|");
        if(evt.getNewValue() instanceof Message){
            if (evt.getNewValue() instanceof ActionMessage){
                System.out.println("| ACTION MESSAGE SENDING");
                System.out.println(evt.getPropertyName());
                for (Action a: ((ActionMessage) evt.getNewValue()).getChoices()){
                    System.out.println("| "+a.getActionName()+a.getStart() + a.getEnd());
                    System.out.println("| OPTIONAL : "+a.isOption());
                }
                System.out.println("| MESSAGE ID : "+((ActionMessage) evt.getNewValue()).getId());
                System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*_*_");
            } else if (evt.getNewValue() instanceof GenericMessage){
                System.out.println("| GENERIC MESSAGE SENDING");
                System.out.println("| "+evt.getPropertyName());
                System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*_*_");
            }else if (evt.getNewValue() instanceof NicknameMessage){
                System.out.println("| NICKNAME MESSAGE SENDING");
                System.out.println("| "+evt.getPropertyName());
                System.out.println("| NICK : "+((NicknameMessage) evt.getNewValue()).getNickname());
                System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*_*_");
            }
        } else if(evt.getNewValue() instanceof VirtualSlot){
            System.out.println("| "+"VIRTUALSLOT SENDING");
            System.out.println("| "+((VirtualSlot) evt.getNewValue()).getCoordinate());
            System.out.println("| "+"_*_*_*_*_*_*_*_*_*_*_*_*_*_");

        }
        System.out.println("| "+evt.getPropertyName());
        System.out.println("| "+"END---DEBUG ID "+id+" "+"END---DEBUG ID "+id+" "+"END---DEBUG ID "+id+" "+"END---DEBUG ID "+id+" "+"END---DEBUG ID "+id+"\n\n\n ");

    }

    public void addSccListener(PropertyChangeListener listener) {
        sccListeners.addPropertyChangeListener(listener);
    }
}