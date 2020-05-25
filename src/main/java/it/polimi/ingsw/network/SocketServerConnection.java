package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.RemoteView;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServerConnection {

    private Socket socket;

    boolean online = false;
    private RemoteView view;
    private ObjectInputStream inputStream;
    private ObjectOutputStream printStream;
    private Logger logger = Logger.getLogger("CLI");
    //oppure trovare soluzione più definitiva per associare id e nome player
    //è strettamente necessario avere il nickname?
    String nickname;

    public SocketServerConnection(String ip, int port, int chosenUI) {

        try {
            this.socket = new Socket(ip, port);

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        if (chosenUI == 0) {
            this.view = new CLI(this);
            new Thread((Runnable) this.view).start();
        } else {
            //TODO this.view = new GUI();
        }
    }


    public void start() {
        try {
            this.online = true;
            inputStream = new ObjectInputStream(socket.getInputStream());
            printStream = new ObjectOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        final Object inputObject = inputStream.readObject();

                        if (inputObject instanceof PropertyChangeEvent) {;
                            view.notifyEvent((PropertyChangeEvent) inputObject);
                        } else if (inputObject instanceof SetupMessage) {
                            view.askNumOfPlayers();
                        }

                    }
                } catch (IOException | ClassNotFoundException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                    e.printStackTrace();

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

    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }


}
