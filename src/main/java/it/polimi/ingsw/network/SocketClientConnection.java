package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

import static java.lang.Thread.sleep;


public class SocketClientConnection implements Runnable {

    private Socket socket;
    private ObjectOutputStream outSocket;
    private ObjectInputStream inSocket;

    private VirtualView view;


    private int id;
    private Server server;

    private boolean active = true;
    private boolean pinged = true;
    private boolean pingHandled = false;

    public SocketClientConnection(Socket newSocket) {
        try {
            socket = newSocket;
            outSocket = new ObjectOutputStream(newSocket.getOutputStream());
            inSocket = new ObjectInputStream(newSocket.getInputStream());
            System.out.println("CREATA CONNECTION");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized boolean isActive() {
        return active;
    }

    private void pingHandler() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int risk = 0;
                while (!socket.isClosed()) {
                    if (pinged) {
                        pinged = false;
                        risk = 0;
                    } else {
                        risk++;
                        if (risk >= 2) {
                            //closeClient
                            try {
                                socket.close();
                                if(view!=null)view.receivePingError(new PingMessage(id));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        sleep(7500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    public void send(Object message) {
        try {
            synchronized (outSocket) {
                if (socket.isClosed()) return;
                outSocket.reset();
                outSocket.writeObject(message);
                outSocket.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int askNumOfPlayers() {
        if (!pingHandled) pingHandler();
        int read = 0;
        try {
            //outSocket = new ObjectOutputStream(socket.getOutputStream());
            boolean invalidRead;
            invalidRead = true;

            while (true) {
                if (invalidRead) {
                    send(new SetupMessage());
                    invalidRead = false;
                }
                Message mess = (Message) inSocket.readObject();
                if (mess instanceof SetupMessage) {
                    read = ((SetupMessage) mess).getField();
                    if (read == 2 || read == 3) {
                        return read;
                    } else {
                        invalidRead = true;
                    }
                } else if (mess instanceof PingMessage) {
                    pingListener();
                }

            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        }
        return read;
    }

    private void pingListener() {
        pinged = true;
    }

    public void notifyGamePlaying() {
        send("The Lobby is full!! Please Wait");
    }

    public void setId(int i) {

        this.id = i;
    }

    public int getId() {
        return id;
    }

    public void setView(VirtualView view) {
        this.view = view;
    }


    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                Object inputObject = inSocket.readObject();
                pingHandler();

                if (inputObject instanceof Message) {
                    Message message = (Message) inputObject;
                    message.setId(this.id);
                    //
                    if (message.getMessage().equalsIgnoreCase("PingMessage")) {
                        pingListener();
                    }
                    //
                    if (inputObject instanceof NicknameMessage) {
                        view.receiveNick((NicknameMessage) message);
                    } else if (inputObject instanceof ColorMessage) {
                        view.receiveColor((ColorMessage) message);
                    } else if (inputObject instanceof ActionMessage) {
                        view.receiveAction((ActionMessage) message);
                    } else if (inputObject instanceof ChoiceMessage) {
                        view.receiveChoice((ChoiceMessage) message);
                    } else if (inputObject instanceof GodMessage) {
                        view.receiveGod((GodMessage) message);
                    } else if (inputObject instanceof InitialCardsMessage) {
                        view.receiveInitialCards((InitialCardsMessage) message);
                    } else if (inputObject instanceof FirstPlayerMessage) {
                        view.receiveFirstPlayer((FirstPlayerMessage) message);
                    } else if (inputObject instanceof WorkerMessage) {
                        view.receiveSetWorker((WorkerMessage) message);
                    }
                }
            }

        } catch (Exception e) {

            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void resetConnection() throws IOException {
        socket.close();
    }
}