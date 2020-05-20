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
            this.view = new CLI(this);
            System.out.println("COSTRUITA CLI +"+this);
        } else {
            //TODO this.view = new GUI();
        }
    }


    public void start() {
        System.out.println("CLIENT SOUT"+ this);

        try {
            this.online = true;
            inputStream = new ObjectInputStream(socket.getInputStream());
            printStream = new ObjectOutputStream(socket.getOutputStream());
/*
            // OPEN READER
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (online) {
                            Object inputObject = inputStream.readObject();

                            if (inputObject instanceof WelcomeMessage) {
                                view.welcomeMessage();
                            } else if (inputObject instanceof NicknameMessage) {
                                send(view.askNickPlayer((NicknameMessage) inputObject));
                            } else if (inputObject instanceof ColorMessage) {
                                send(view.askColor((ColorMessage) inputObject));
                            } else if (inputObject instanceof ColorSelectedMessage) {
                                view.showColor((ColorSelectedMessage) inputObject);
                            } else if (inputObject instanceof FirstPlayerMessage) {
                                send(view.firstPlayer((FirstPlayerMessage) inputObject));
                            } else if (inputObject instanceof WorkerMessage) {
                                send(view.setWorker((WorkerMessage) inputObject));
                            } else if (inputObject instanceof ActionMessage) {
                                send(view.askAction((ActionMessage) inputObject));
                            } else if (inputObject instanceof ChoiceMessage) {
                                send(view.askChoice((ChoiceMessage) inputObject));
                            } else if (inputObject instanceof InitialCardsMessage) {
                                send(view.askGodList((InitialCardsMessage) inputObject));
                            } else if (inputObject instanceof GodMessage) {
                                send(view.askGod((GodMessage) inputObject));
                            } else if (inputObject instanceof SetupMessage) {
                                send(view.askNumOfPlayers((SetupMessage) inputObject));
                            } else if (inputObject instanceof StartGameMessage) {
                                view.gameIsReady((StartGameMessage) inputObject);
                            } else if (inputObject instanceof WaitingMessage) {
                                view.waitingMess((WaitingMessage) inputObject);
                            }  else if (inputObject instanceof GenericMessage) {
                                view.genericMess((GenericMessage) inputObject);
                            } else if (inputObject instanceof WinnerMessage) {
                                view.winnerMess((WinnerMessage) inputObject);
                                online = false;
                            } else if (inputObject instanceof VirtualSlotMessage) {
                                view.updateVBoard((VirtualSlotMessage) inputObject);

                            }

                            // TODO collegarsi alla remoteView (CLI o GUI) del client

                        }
                        closeConnection();
                    } catch (Exception e) {
                        // online = false;
                        e.printStackTrace();
                    }
                }
            }).start();
*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            final Object inputObject = inputStream.readObject();

                            if (inputObject instanceof PropertyChangeEvent) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.notifyEvent((PropertyChangeEvent) inputObject);
                                    }
                                }).start();
                            } else if(inputObject instanceof SetupMessage){
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.askNumOfPlayers();
                                    }
                                }).start();
                            }

                        }
                    } catch (IOException | ClassNotFoundException e) {
                        logger.log(Level.SEVERE, e.getMessage());

                    }
                }
            }).start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());


            // TODO writer che andrà a mandare gli oggetti al controller del server
            // writer che sarà chiamato dalla update dopo la chiamatea della cli

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
            if(evt.getNewValue() instanceof Message)
                ((Message) evt.getNewValue()).setId(view.getPlayerId());
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
