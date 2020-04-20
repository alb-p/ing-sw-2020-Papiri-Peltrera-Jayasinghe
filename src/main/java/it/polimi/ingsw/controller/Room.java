package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.InitSetup;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.network.SocketClientConnection;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

public class Room {

    private ArrayList<SocketClientConnection> connections = new ArrayList<SocketClientConnection>();
    private int playersPerGame = 0;

    public synchronized void addPlayer(SocketClientConnection connection) throws Exception {
        if (this.playersPerGame < connections.size()) {
            this.connections.add(connection);
        } else {
            throw new Exception();
        }
    }

    public synchronized boolean isReady() {
        return this.connections.size() == this.playersPerGame;
    }

    public synchronized void setNumOfPlayers(int playersPerGame) {
        this.playersPerGame = playersPerGame;
    }

    public synchronized boolean isUninitializated() {
        return this.connections.size() == this.playersPerGame && this.playersPerGame == 0;
    }


    public void start() {
        Model model = new Model();
        VirtualView view = new VirtualView(connections);
        for(SocketClientConnection c: connections) c.setView(view);

        Controller controller=new Controller(model,view);
        InitSetup initSetup =new InitSetup();
        GameHandler gameHandler=new GameHandler(initSetup,model);

        view.addVirtualViewListener(controller);
        view.addVirtualViewListener(gameHandler);
        model.addModelListener(view);
        initSetup.addInitSetupListener(view);
    }

    public int currentPlayerId() {
        return this.connections.size();
    }
}
