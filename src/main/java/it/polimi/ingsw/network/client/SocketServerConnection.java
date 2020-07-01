package it.polimi.ingsw.network.client;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.GUIpackage.GUI;
import it.polimi.ingsw.view.RemoteView;

import java.beans.PropertyChangeEvent;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Socket Server connection is the linker to the server
 *      for the client.
 *
 */
public class SocketServerConnection implements Runnable{

    private Socket socket;
    private final String ip;
    private final int port;
    boolean online = false;
    private final RemoteView view;
    private ObjectInputStream inputStream;
    private ObjectOutputStream printStream;
    private final Logger logger = Logger.getLogger("network.ssc");


    /**
     * Instantiates a new Socket server connection.
     *  Starts the thread for the view
     *
     * @param ip       the ip
     * @param port     the port
     * @param chosenUI the chosen ui
     */
    public SocketServerConnection(String ip, int port, int chosenUI, boolean useUnicode) throws UnsupportedEncodingException {
        this.ip = ip;
        this.port = port;
        if (chosenUI == 0) {
            this.view = new CLI(this, useUnicode);
            new Thread(this.view).start();
        } else {
            this.view = new GUI(this);
            javax.swing.SwingUtilities.invokeLater(view);
        }
    }

    /**
     * Reads the objects coming from the server
     * and forwards them to the remote view
     */
    @Override
    public void run() {
        try {
            this.socket = new Socket(ip, port);
            socket.setKeepAlive(true);

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        try {
            this.online = true;
            inputStream = new ObjectInputStream(socket.getInputStream());
            printStream = new ObjectOutputStream(socket.getOutputStream());

            while (!socket.isClosed()) {
                final Object inputObject = inputStream.readObject();

                if (inputObject instanceof PropertyChangeEvent) {
                    view.notifyEvent((PropertyChangeEvent) inputObject);
                } else if (inputObject instanceof SetupMessage) {
                    view.askNumOfPlayers();
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Send.
     *
     * @param message the message
     */
    public void send(Object message) {
        synchronized (printStream) {
            try {
                printStream.reset();
                printStream.writeObject(message);
                printStream.flush();
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    /**
     * Forwards the events to
     * the SCC that will be catch
     * by the controller.
     *
     * @param evt the evt
     */
    public void sendEvent(PropertyChangeEvent evt) {
        //Client debug function
        debug(evt);
        if (socket.isClosed()) return;
        try {
            if (evt.getNewValue() instanceof Message) {
                ((Message) evt.getNewValue()).setId(view.getPlayerId());
            }
            synchronized (printStream) {
                printStream.reset();
                printStream.writeObject(evt);
                printStream.flush();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, " Socket closed server side " + e.getMessage());
        }
    }

    /**
     * Close connection.
     */
    public void closeConnection() {
        try {
            if (!socket.isClosed()) socket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }


    /**
     * Debug.
     *
     * @param evt the evt
     */
    private void debug(PropertyChangeEvent evt) {
        String separator = "_*_*_*_*_*_*_*_*_*_*_*_*_*_";
        logger.log(Level.FINE, "---DEBUG ID " + " " + "---DEBUG ID " + " " + "---DEBUG ID " + " " + "---DEBUG ID " + " ");
        if (evt.getNewValue() instanceof Message) {
            if (evt.getNewValue() instanceof ActionMessage) {
                logger.log(Level.FINE, "ACTION MESSAGE SENDING");
                logger.log(Level.FINE, evt.getPropertyName());
                Action a = ((ActionMessage) evt.getNewValue()).getAction();
                logger.log(Level.FINE, a.getActionName() + a.getStart() + a.getEnd());
            }
            logger.log(Level.FINE, separator);
        } else if (evt.getNewValue() instanceof GenericMessage) {
            logger.log(Level.FINE, "GENERIC MESSAGE SENDING");
            logger.log(Level.FINE, evt.getPropertyName());
            logger.log(Level.FINE, separator);
        } else if (evt.getNewValue() instanceof NicknameMessage) {
            logger.log(Level.FINE, "NICKNAME MESSAGE SENDING");
            logger.log(Level.FINE, evt.getPropertyName());
            logger.log(Level.FINE, "NICK : " + ((NicknameMessage) evt.getNewValue()).getNickname());
            logger.log(Level.FINE, separator);
        } else {
            logger.log(Level.FINE, "SENDING");
            logger.log(Level.FINE, evt.getPropertyName());
            logger.log(Level.FINE, separator);

        }
        logger.log(Level.FINE, "END---DEBUG ID " + " " + "END---DEBUG ID " + " " + "END---DEBUG ID " + " " + "END---DEBUG ID " + " " + "END---DEBUG ID " + " ");

    }


}
