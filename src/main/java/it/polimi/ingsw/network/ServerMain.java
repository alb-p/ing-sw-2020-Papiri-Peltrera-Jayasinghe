package it.polimi.ingsw.network;

import java.util.*;

public class ServerMain {

    private Map<String, SocketClientConnection> waitingConnection = new HashMap<>();
    private int numOfPlayers;

    public static void main(String[] args){

        Server server;
        try {
            server = new Server();
            server.run();
        } catch (Exception e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }







}
