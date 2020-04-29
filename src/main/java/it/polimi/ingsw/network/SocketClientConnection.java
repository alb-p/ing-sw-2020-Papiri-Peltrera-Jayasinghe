package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

//import static jdk.internal.net.http.common.Utils.close;

public class SocketClientConnection implements Runnable {

    private Socket socket;
    private ObjectOutputStream outSocket;
    private ObjectInputStream inSocket;

    private VirtualView view;


    private int id;
    private Server server;

    private boolean active = true;

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


    public synchronized void send(Object message) {
        try {
            outSocket.reset();
            outSocket.writeObject(message);
            outSocket.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public int askNumOfPlayers() {
        int read = 0;
        try {
            System.out.println("ASK NUM OF PLAYERS");
            //inSocket = new Scanner(socket.getInputStream());
            //outSocket = new ObjectOutputStream(socket.getOutputStream());
            send(new SetupMessage());
            read = ((SetupMessage) inSocket.readObject()).getField();
            //numOfPlayers = Integer.parseInt(read);
            while (!(read == 2 || read == 3)) {
                System.out.println(read);
                System.out.println("ASK NUM OF PLAYERS after error");

                send(new SetupMessage());
                read = ((SetupMessage) inSocket.readObject()).getField();
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        }
        return read;
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
            while (isActive()) {
                Object inputObject = inSocket.readObject();

                if (inputObject instanceof Message) {
                    Message message = (Message) inputObject;
                    message.setId(this.id);
                    if (inputObject instanceof NicknameMessage) {
                        view.receiveNick((NicknameMessage) message);
                    } else if (inputObject instanceof ColorMessage) {
                        view.receiveColor((ColorMessage) message);
                    } else if (inputObject instanceof ActionMessage) {
                        view.receiveAction((ActionMessage) message);
                    } else if (inputObject instanceof GodMessage) {
                        view.receiveGod((GodMessage) message);
                    } else if (inputObject instanceof InitialCardsMessage) {
                        view.receiveInitialCards((InitialCardsMessage) message);
                    }else if (inputObject instanceof FirstPlayerMessage) {
                        view.receiveFirstPlayer((FirstPlayerMessage) message);
                    }else if (inputObject instanceof WorkerMessage) {
                        view.receiveSetWorker((WorkerMessage) message);
                    }
                } else {
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}