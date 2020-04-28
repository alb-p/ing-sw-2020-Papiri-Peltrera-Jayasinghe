package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.RemoteView;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private Socket socket;

    boolean online = false;
    private RemoteView view;
    private ObjectInputStream inputStream;
    private ObjectOutputStream printStream;
    private Logger logger = Logger.getLogger("CLI");
    //oppure trovare soluzione più definitiva per associare id e nome player
    //è strettamente necessario avere il nickname?
    String nickname;

    public Client(String ip, int port, int chosenUI) {

        try {
            this.socket = new Socket(ip, port);

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        if (chosenUI == 0) {
            this.view = new CLI();
        } else {
            //TODO this.view = new GUI();
        }
    }


    public void start() {
        try {
            this.online = true;
            inputStream = new ObjectInputStream(socket.getInputStream());
            printStream = new ObjectOutputStream(socket.getOutputStream());

            // OPEN READER
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (online) {
                            System.out.println("sono online ready to read");
                            Object inputObject = inputStream.readObject();
                            System.out.println("ARRIVATO OGGETTO");

                            if (inputObject instanceof WelcomeMessage) {
                                view.welcomeMessage();
                            } else if (inputObject instanceof NicknameMessage) {
                                send(view.askNickPlayer((NicknameMessage) inputObject));
                            } else if (inputObject instanceof ColorMessage) {
                                //meglio tenere anche info sul colore nel client?
                                send(view.askColor((ColorMessage) inputObject));
                            } else if (inputObject instanceof ColorSelectedMessage) {
                                view.showColor((ColorSelectedMessage) inputObject);
                            } else if (inputObject instanceof FirstPlayerMessage) {
                                send(view.firstPlayer((FirstPlayerMessage) inputObject));
                            }else if (inputObject instanceof ActionMessage) {
                                view.askAction((ActionMessage) inputObject);
                            } else if (inputObject instanceof InitialCardsMessage) {
                                //meglio tenere anche info sul colore nel client?
                                send(view.askGodList((InitialCardsMessage) inputObject));
                            } else if (inputObject instanceof GodMessage) {
                                //meglio tenere anche info sul colore nel client?
                                send(view.askGod((GodMessage) inputObject));
                            } else if (inputObject instanceof SetupMessage) {
                                //meglio tenere anche info sul colore nel client?
                                send(view.askNumOfPlayers((SetupMessage) inputObject));

                            }

                            // TODO collegarsi alla remoteView (CLI o GUI) del client

                        }
                    } catch (Exception e) {
                       // online = false;
                        e.printStackTrace();
                    }

                }
            }).start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        // TODO writer che andrà a mandare gli oggetti al controller del server
        // writer che sarà chiamato dalla update dopo la chiamatea della cli

    }

    private synchronized void send(Object message) {
        try {
            printStream.reset();
            printStream.writeObject(message);
            printStream.flush();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }


}
