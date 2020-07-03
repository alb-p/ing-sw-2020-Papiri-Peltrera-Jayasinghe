package it.polimi.ingsw.network.server;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.utils.VirtualSlot;
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


/**
 * The type Socket client connection is the linker to the client
 * for the server, if it cannot find the socket open,
 * it notifies to the controller the disconnection of the player.
 */
public class SocketClientConnection implements Runnable, PropertyChangeListener {

    private Socket socket;
    private ObjectOutputStream outSocket;
    private ObjectInputStream inSocket;
    private final PropertyChangeSupport sccListeners = new PropertyChangeSupport(this);
    private final Logger logger = Logger.getLogger("network.scc");

    private int id;

    /**
     * Instantiates a new Socket client connection.
     *
     * @param newSocket the new socket
     */
    public SocketClientConnection(Socket newSocket) {
        try {
            socket = newSocket;
            socket.setKeepAlive(true);
            outSocket = new ObjectOutputStream(newSocket.getOutputStream());
            inSocket = new ObjectInputStream(newSocket.getInputStream());
            logger.log(Level.INFO, "Connection created");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Send.
     *
     * @param message the message
     */
    public synchronized void send(Object message) {
        try {
            outSocket.reset();
            outSocket.writeObject(message);
            outSocket.flush();

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    /**
     * Ask num of players int.
     *
     * @return the int
     */
    public int askNumOfPlayers() {
        int read = 0;
        try {
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


    /**
     * Sets id of the player
     *
     * @param i the id
     */
    public void setId(int i) {
        this.id = i;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }


    /**
     * Fires to the controller the events coming
     * from the view.
     */
    @Override
    public void run() {
        try {
            sendEvent(new PropertyChangeEvent(this, "gameReady", null, id));
            while (!socket.isClosed()) {
                Object inputObject = inSocket.readObject();
                if (inputObject instanceof PropertyChangeEvent &&
                        ((PropertyChangeEvent) inputObject).getNewValue() instanceof Message
                        && ((Message) ((PropertyChangeEvent) inputObject).getNewValue()).getId() == id) {
                    receiveDebug((PropertyChangeEvent) inputObject);
                    sccListeners.firePropertyChange((PropertyChangeEvent) inputObject);
                } else if (inputObject instanceof PropertyChangeEvent &&
                        ((PropertyChangeEvent) inputObject).getPropertyName().equalsIgnoreCase("playerDisconnected")) {
                    sccListeners.firePropertyChange("playerDisconnected", this, id);
                    send(new GenericMessage(id,"acceptedDisconnection"));
                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException exception) {
            logger.log(Level.WARNING, "Connection id " + id + " lost  " + exception.getMessage());
            sccListeners.firePropertyChange("playerDisconnected", this, id);
            try {
                socket.close();
            } catch (IOException e) {
                logger.log(Level.WARNING, "catch close disconnected player");
            }
        }

    }

    /**
     * Receive debug.
     *
     * @param inputObject the input object
     */
    private void receiveDebug(PropertyChangeEvent inputObject) {
        System.out.println("\n\n\t\t |RECEIVED EVENT");
        System.out.println("\t\t |" + inputObject.getPropertyName());
        System.out.println("\t\t |" + "ID SENDER " + id);
        System.out.println("\t\t | ______________________________");
    }

    /**
     * Property change.
     *
     * @param evt the evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!socket.isClosed()) sendEvent(evt);
    }

    /**
     * Forward the events coming from
     * the model to the view.
     *
     * @param evt the evt to forward
     */
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
                logger.log(Level.INFO, "Connection " + id + " SOCKET CLOSED");
            } catch (IOException ioException) {
                logger.log(Level.SEVERE, e.getMessage());
            } finally {
                sccListeners.firePropertyChange("playerDisconnected", this, id);
            }
        }

    }

    /**
     * Debug.
     *
     * @param evt the evt
     */
    private void debug(PropertyChangeEvent evt) {
        System.out.println("| " + "---DEBUG ID " + id + " " + "---DEBUG ID " + id + " " + "---DEBUG ID " + id + " " + "---DEBUG ID " + id + "\n|\n|\n|");
        if (evt.getNewValue() instanceof Message) {
            if (evt.getNewValue() instanceof ActionMessage) {
                System.out.println("| ACTION MESSAGE SENDING");
                System.out.println(evt.getPropertyName());
                for (Action a : ((ActionMessage) evt.getNewValue()).getChoices()) {
                    System.out.println("| " + a.getActionName() + a.getStart() + a.getEnd());
                }
                System.out.println("| MESSAGE ID : " + ((ActionMessage) evt.getNewValue()).getId());
                System.out.println("| _*_*_*_*_*_*_*_*_*_*_*_*_*_");
            }
            if (evt.getNewValue() instanceof WorkerMessage) {
                System.out.println("| worker SENDING");
                System.out.println(evt.getPropertyName());
                WorkerMessage message = (WorkerMessage) evt.getNewValue();
                System.out.println("| MESSAGE ID : " + message.getId());
                System.out.println("| worker ID : " + message.getWorkerNumber());
                System.out.println("| _*_*_*_*_*_*_*_*_*_*_*_*_*_");
            } else if (evt.getNewValue() instanceof GenericMessage) {
                System.out.println("| GENERIC MESSAGE SENDING");
                System.out.println("| " + evt.getPropertyName());
                System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*_*_");
            } else if (evt.getNewValue() instanceof NicknameMessage) {
                System.out.println("| NICKNAME MESSAGE SENDING");
                System.out.println("| " + evt.getPropertyName());
                System.out.println("| NICK : " + ((NicknameMessage) evt.getNewValue()).getNickname());
                System.out.println("| _*_*_*_*_*_*_*_*_*_*_*_*_*_");
            } else if (evt.getNewValue() instanceof WinnerMessage) {
                System.out.println("| WINNER MESSAGE SENDING");
                System.out.println("| " + evt.getPropertyName());
                System.out.println("| NICK WINNER: " + ((WinnerMessage) evt.getNewValue()).getMessage());
                System.out.println("| _*_*_*_*_*_*_*_*_*_*_*_*_*_");
            }
        } else if (evt.getNewValue() instanceof VirtualSlot) {
            System.out.println("| " + "VIRTUALSLOT SENDING");
            System.out.println("| " + ((VirtualSlot) evt.getNewValue()).getCoordinate());
            System.out.println("| " + "_*_*_*_*_*_*_*_*_*_*_*_*_*_");
        }
        System.out.println("| " + evt.getPropertyName());
        System.out.println("| " + "END---DEBUG ID " + id + " " + "END---DEBUG ID " + id + " " + "END---DEBUG ID " + id + " " + "END---DEBUG ID " + id + " " + "END---DEBUG ID " + id + "\n\n\n ");

    }

    /**
     * Add scc listener.
     *
     * @param listener the listener to add
     */
    public void addSccListener(PropertyChangeListener listener) {
        sccListeners.addPropertyChangeListener(listener);
    }
}