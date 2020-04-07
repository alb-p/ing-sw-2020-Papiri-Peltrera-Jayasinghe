package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    private Map<String, SocketClientConnection> waitingConnection = new HashMap<>();
    private int numOfPlayers;

    public static void main(String[] args){
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        view.addViewListener(controller);
        model.addModelListener(view);
        view.run();

        Server server;
        try {
            server = new Server();
            server.run();
        } catch (Exception e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }







}
