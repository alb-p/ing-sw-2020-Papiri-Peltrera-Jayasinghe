package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.GUIpackage.GUI;
import it.polimi.ingsw.view.RemoteView;

import java.beans.PropertyChangeEvent;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServerConnection {

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


    public void start() {
        try {
            this.socket = new Socket(ip, port);

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        try {
            this.online = true;
            inputStream = new ObjectInputStream(socket.getInputStream());
            printStream = new ObjectOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
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
            }).start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

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

    public void sendEvent(PropertyChangeEvent evt) {
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

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }


}
