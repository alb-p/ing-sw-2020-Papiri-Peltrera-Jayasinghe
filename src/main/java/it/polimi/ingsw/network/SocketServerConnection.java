package it.polimi.ingsw.network;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.VirtualSlot;
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
 * The type Socket server connection.
 */
public class SocketServerConnection implements Runnable{

    private Socket socket;
    private final String ip;
    private final int port;
    boolean online = false;
    private RemoteView view;
    private ObjectInputStream inputStream;
    private ObjectOutputStream printStream;
    private Logger logger = Logger.getLogger("network.ssc");
    //oppure trovare soluzione più definitiva per associare id e nome player
    //è strettamente necessario avere il nickname?
    String nickname;

    /**
     * Instantiates a new Socket server connection.
     *
     * @param ip       the ip
     * @param port     the port
     * @param chosenUI the chosen ui
     */
    public SocketServerConnection(String ip, int port, int chosenUI) {
        this.ip = ip;
        this.port = port;
        if (chosenUI == 0) {
            this.view = new CLI(this);
            new Thread(this.view).start();
        } else {
            this.view = new GUI(this);
            javax.swing.SwingUtilities.invokeLater(view);
        }
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        try {
            this.socket = new Socket(ip, port);

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        try {
            this.online = true;
            inputStream = new ObjectInputStream(socket.getInputStream());
            printStream = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
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
     * Send event.
     *
     * @param evt the evt
     */
    public void sendEvent(PropertyChangeEvent evt) {
        debug(evt);
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
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Close connection.
     */
    public void closeConnection() {
        try {
            socket.close();
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
        System.out.println("---DEBUG ID " + " " + "---DEBUG ID " + " " + "---DEBUG ID " + " " + "---DEBUG ID " + " ");
        if (evt.getNewValue() instanceof Message) {
            if (evt.getNewValue() instanceof ActionMessage) {
                System.out.println("ACTION MESSAGE SENDING");
                System.out.println(evt.getPropertyName());
                Action a = ((ActionMessage) evt.getNewValue()).getAction();
                System.out.println(a.getActionName() + a.getStart() + a.getEnd());
            }
            System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*_*_");
        } else if (evt.getNewValue() instanceof GenericMessage) {
            System.out.println("GENERIC MESSAGE SENDING");
            System.out.println(evt.getPropertyName());
            System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*_*_");
        } else if (evt.getNewValue() instanceof NicknameMessage) {
            System.out.println("NICKNAME MESSAGE SENDING");
            System.out.println(evt.getPropertyName());
            System.out.println("NICK : " + ((NicknameMessage) evt.getNewValue()).getNickname());
            System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*_*_");
        } else {
            System.out.println("SENDING");
            System.out.println(evt.getPropertyName());
            System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*_*_");

        }
        System.out.println("END---DEBUG ID " + " " + "END---DEBUG ID " + " " + "END---DEBUG ID " + " " + "END---DEBUG ID " + " " + "END---DEBUG ID " + " ");

    }


}
