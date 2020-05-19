package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.controller.TurnHandler;
import it.polimi.ingsw.model.InitSetup;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.network.SocketClientConnection;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

public class Room {

    private ArrayList<SocketClientConnection> connections = new ArrayList<>();
    private int playersPerGame = 0;


    public  void addPlayer(SocketClientConnection connection) throws Exception {
        if (this.playersPerGame > connections.size()) {
            this.connections.add(connection);
            System.out.println("CONNECTION ADDED"+ connection.getId());
        } else {
            throw new Exception();
        }
    }

    public  boolean isReady() {
        return this.connections.size() == this.playersPerGame;
    }

    public  void setNumOfPlayers(int playersPerGame) {
        this.playersPerGame = playersPerGame;
    }

    public  boolean isUninitialized() {
        return this.connections.size() == this.playersPerGame && this.playersPerGame == 0;
    }


    public void start() {
        Model model = new Model();
        VirtualView view = new VirtualView(connections);
        for(SocketClientConnection c: connections) c.setView(view);

        InitSetup initSetup =new InitSetup();
        GameHandler gameHandler=new GameHandler(initSetup,model, playersPerGame);
        TurnHandler turnHandler = new TurnHandler(model, playersPerGame);
        gameHandler.setTurnHandler(turnHandler);

        view.addVirtualViewListener(gameHandler);
        view.addVirtualViewListener(turnHandler);
        //TODO crearre una sola PropertyChangeSupport in model e poi passarla a chi ne ha bisogno
        model.addModelListener(view);
        initSetup.addInitSetupListener(view);
        view.run();
    }

    public int currentPlayerId() {
        System.out.println("CURRENT PLAYER ID = "+this.connections.size());
        return this.connections.size();
    }


}
