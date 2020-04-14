package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.Controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

//import static jdk.internal.net.http.common.Utils.close;

public class SocketClientConnection implements Runnable {

    private Socket socket;
    private ObjectOutputStream outSocket;
    private Scanner inSocket;

    private Server server;

    private boolean active = true;

    private synchronized boolean isActive() {
        return active;
    }


    private synchronized void send(Object message) {
        try {
            outSocket.reset();
            outSocket.writeObject(message);
            outSocket.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

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

    public int askNumofPlayers() {
        int numOfPlayers = 0;

        try {
            inSocket = new Scanner(socket.getInputStream());
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String read = inSocket.nextLine();
            numOfPlayers = Integer.parseInt(read);
            while (numOfPlayers != 2 || numOfPlayers != 3) {
                send("Welcome!\nWhat is your name?");
                read = inSocket.nextLine();
                numOfPlayers = Integer.parseInt(read);
            }

        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }

        return numOfPlayers;

    }

    public void notifyGamePlaying() {
        send("The Lobby is full!! Please Wait");
    }
}
