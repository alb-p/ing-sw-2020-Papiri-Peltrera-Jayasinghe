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
        } catch (IOException e) {
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

    /*
        @Override
        public void run() {

            String name;
            try {
                inSocket = new Scanner(socket.getInputStream());
                outSocket = new ObjectOutputStream(socket.getOutputStream());
                send("Welcome!\nWhat is your name?");
                String read = inSocket.nextLine();
                name = read;
                //  new Controller().getInstance().lobby(this, name);
                // TODO attendo che la partita sia pronta
                // TODO collegare la virtualView del player al Client connesso a lui
                while (isActive()) {
                    read = inSocket.nextLine();
                    // TODO mettere in relazione come listener il socket sul controller

                }
            } catch (IOException | NoSuchElementException e) {
                System.err.println("Error!" + e.getMessage());
            } finally {
                //close();
            }
        }
    */
    public int askNumOfPlayers() {
        int numOfPlayers = 0;
        try {
            //inSocket = new Scanner(socket.getInputStream());
            //outSocket = new ObjectOutputStream(socket.getOutputStream());
            send("How many players for the game");
            String read = (String) inSocket.readObject();
            numOfPlayers = Integer.parseInt(read);
            while (numOfPlayers != 2 || numOfPlayers != 3) {
                send("Welcome!\nWhat is your name?");
                read = (String) inSocket.readObject();
                numOfPlayers = Integer.parseInt(read);
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        }
        return numOfPlayers;
    }

    public void notifyGamePlaying() {
        send("The Lobby is full!! Please Wait");
    }

    public String askNick() throws IOException, ClassNotFoundException {
        send("Write your nick:\n>>");
        String read = (String) inSocket.readObject();
        return read;

    }

   /* @Override
    public void run() {
            while(socket.isConnected()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while(isActive()){
                                Object inputObject = inSocket.readObject();
                                // socketListeners.firePropertyChange("Sock",null , inputObject);
                                // TODO collegarsi alla remoteView (CLI) del client
                                if(inputObject instanceof Message){
                                    Message message=(Message) inputObject;
                                    message.setId(this.id);
                                    if(inputObject instanceof NicknameMessage){

                                    }else if(inputObject instanceof ColorMessage){

                                    }else if(inputObject instanceof NicknameMessage){

                                    }
                                }else{
                                    socket.close();
                                }
                            }
                        }catch (Exception e){
                            active=false;
                        }

                    }
                }).start();
            }
    }*/


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
                        view.receiveColor((ColorMessage)message);
                    } else if (inputObject instanceof ActionMessage) {
                        view.receiveAction((ActionMessage)message);
                    } else if (inputObject instanceof GodMessage){
                        view.receiveGod((GodMessage)message);
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