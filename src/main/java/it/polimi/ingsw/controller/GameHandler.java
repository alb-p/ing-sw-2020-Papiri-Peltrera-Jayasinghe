package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Game handler is the controller used for
 * the setup of the game.
 */
public class GameHandler implements PropertyChangeListener {

    private final InitSetup data;
    private final HashMap<Integer, String> playersMap = new HashMap<>();
    private final Model model;
    private TurnHandler turnHandler;
    private final int playersPerGame;
    private int firstPlayerChosenID = -1;
    private int currentPlayerID = -1;
    private boolean atLeastOneGod = false;
    private boolean workerPlaced = false;
    private final Logger logger = Logger.getLogger("gameHandler.controller");
    /**
     * Instantiates a new Game handler.
     *
     * @param initSetup      the model used to set up the main model
     * @param model          the model of the entire game
     * @param playersPerGame the players per game
     */
    public GameHandler(InitSetup initSetup, Model model, int playersPerGame) {
        data = initSetup;
        this.model = model;
        this.playersPerGame = playersPerGame;
    }

    /**
     * Property change.
     * Handles the events coming from view
     *
     * @param evt the event come sent from a talker
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {


        if (evt.getPropertyName().equals("notifyNickname")) {
            NicknameMessage message = (NicknameMessage) evt.getNewValue();
            String name = message.getNickname();
            if (!data.isInUser(name)) {
                data.setNicknames(message);
                playerCreationQueue(message);
            }

        } else if (evt.getPropertyName().equals("notifyColor")) {
            ColorMessage message = (ColorMessage) evt.getNewValue();
            Color color = message.getColor();
            if (data.isInColor(color)) {
                data.delColor(message);
                playerCreationQueue(message);
            }

        } else if (evt.getPropertyName().equals("notify1ofNGod")) {
            InitialCardsMessage message = (InitialCardsMessage) evt.getNewValue();
            if (message.getId() == currentPlayerID) {
                for (String s : message.getSelectedList()) if (data.isInListGod(s)) data.addChosenGod(s, message);
                if (data.chosenGodsSize() == playersPerGame) currentPlayerID++;
            }

        } else if (evt.getPropertyName().equals("notifyGod")) {
            GodMessage message = (GodMessage) evt.getNewValue();
            String god = message.getGod();
            if (message.getId() == currentPlayerID%playersPerGame && data.isInGod(god)) {
                model.setCard(message.getId(), god);
                data.delGod(message);
                currentPlayerID++;
                atLeastOneGod = true;
            }

        } else if (evt.getPropertyName().equals("firstPlayerSelected") && atLeastOneGod && data.chosenGodsSize() == 0) {
            NicknameMessage message = (NicknameMessage) evt.getNewValue();
            String name = message.getNickname();
            if ( data.isInUser(name) && message.getId() == (currentPlayerID-1)%playersPerGame) {
                for (int i = 0; i < playersPerGame; i++) {
                    if (playersMap.get(i).equals(name)) {
                        firstPlayerChosenID = i;
                        currentPlayerID = firstPlayerChosenID;
                        turnHandler.setTotalTurnCounter(i);
                        data.firstPlayer(message);
                        break;
                    }
                }
                logger.log(Level.INFO, "A new match is starting...");
            }

        } else if (evt.getPropertyName().equals("notifyWorker") && firstPlayerChosenID != -1) {
            WorkerMessage message = (WorkerMessage) evt.getNewValue();
            Coordinate coordinate = message.getCoordinate();
            if (currentPlayerID%playersPerGame == message.getId()) {
                boolean addedWorker =  model.addWorker(currentPlayerID%playersPerGame, coordinate, message.getWorkerNumber());
                if (!workerPlaced && addedWorker) {
                    workerPlaced = true;
                    data.workerPlaced(message);
                } else if (workerPlaced && addedWorker) {
                    workerPlaced = false;
                    currentPlayerID++;
                    data.workerPlaced(message);
                }


            }
        }
    }


    /**
     * This method modify the model
     * and initialize the players
     *
     * @param value the value needed to initialize the model
     */
    private void playerCreationQueue(Object value) {
        if (value instanceof NicknameMessage) {
            playersMap.put(((NicknameMessage) value).getId(), ((NicknameMessage) value).getNickname());
        } else if (value instanceof ColorMessage) {
            ColorMessage message = (ColorMessage) value;
            model.addPlayer(new Player(message.getId(), playersMap.get(message.getId()), message.getColor()));
            if (model.getNumOfPlayers() == this.playersPerGame) {
                Random random = new Random();
                currentPlayerID = random.nextInt(model.getNumOfPlayers());
                data.notifyGodly(currentPlayerID);
            }
        }

    }


    /**
     * Sets turn handler.
     *
     * @param turnHandler the turn handler
     */
    public void setTurnHandler(TurnHandler turnHandler) {
        this.turnHandler = turnHandler;
    }

    /**
     * Get current player id.
     *
     * @return the id of the current player
     */
    public int getCurrentPlayerID(){ //usato solamente per i test
        return this.currentPlayerID;
    }
}


