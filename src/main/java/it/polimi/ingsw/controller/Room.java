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
        InitSetup initSetup =new InitSetup();
        VirtualView view = new VirtualView(connections);
        GameHandler gameHandler=new GameHandler(initSetup);

        //manca controller da aggiungere come listener della virualview
        view.addVirtualViewListener(gameHandler);
        model.addModelListener(view);
        initSetup.addInitSetupListener(view);
    }
}
