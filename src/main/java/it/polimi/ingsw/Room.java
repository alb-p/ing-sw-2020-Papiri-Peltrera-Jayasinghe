package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.controller.TurnHandler;
import it.polimi.ingsw.model.InitSetup;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.network.SocketClientConnection;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/**
 * The type Room handles the creation of a match.
 */
public class Room {

    private final ExecutorService executor;
    private ArrayList<SocketClientConnection> connections = new ArrayList<>();
    private int playersPerGame = 0;

    /**
     * Instantiates a new Room.
     *
     * @param executor the executor
     */
    public Room(ExecutorService executor){
        this.executor = executor;
    }

    /**
     * Add player.
     *
     * @param connection the connection
     */
    public void addPlayer(SocketClientConnection connection){
        if (this.playersPerGame > connections.size()) {
            this.connections.add(connection);
        }
    }

    /**
     * Is ready boolean.
     *
     * @return the boolean
     */
    public boolean isReady() {
        return this.connections.size() == this.playersPerGame;
    }

    /**
     * Sets num of players.
     *
     * @param playersPerGame the players per game
     */
    public void setNumOfPlayers(int playersPerGame) {
        this.playersPerGame = playersPerGame;
    }

    /**
     * Is uninitialized boolean.
     *
     * @return the boolean
     */
    public boolean isUninitialized() {
        return this.connections.size() == this.playersPerGame && this.playersPerGame == 0;
    }


    /**
     * Start.
     */
    public void start() {
        Model model = new Model();

        InitSetup initSetup = new InitSetup();
        GameHandler gameHandler = new GameHandler(initSetup, model, playersPerGame);
        TurnHandler turnHandler = new TurnHandler(model, playersPerGame);
        gameHandler.setTurnHandler(turnHandler);
        for (SocketClientConnection c : connections) {
            c.addSccListener(gameHandler);
            c.addSccListener(turnHandler);
            model.addModelListener(c);
            initSetup.addInitSetupListener(c);
            executor.submit(c);
        }

    }

    /**
     * Current player id int.
     *
     * @return the int
     */
    public int currentPlayerId() {
        return this.connections.size();
    }


}
