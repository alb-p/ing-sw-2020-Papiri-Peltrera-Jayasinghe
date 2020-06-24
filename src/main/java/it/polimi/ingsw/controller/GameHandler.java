package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Random;

/**
 * The type Game handler.
 */
public class GameHandler implements PropertyChangeListener {

    private InitSetup data;
    private HashMap<Integer, String> playersMap = new HashMap<>();      //associazione    ID -> nome
    private Model model;
    private TurnHandler turnHandler;
    private int playersPerGame;
    private int firstPlayerChosenID = -1;
    private int currentPlayerID = -1;
    private boolean atLeastOneGod = false;
    private boolean workerPlaced = false;

    /**
     * Instantiates a new Game handler.
     *
     * @param initSetup      the init setup
     * @param m              the m
     * @param playersPerGame the players per game
     */
    public GameHandler(InitSetup initSetup, Model m, int playersPerGame) {
        data = initSetup;
        model = m;
        this.playersPerGame = playersPerGame;
    }

    /**
     * Property change.
     *
     * @param evt the evt
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
                        data.FirstPlayer(message);
                        break;
                    }
                }
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
     * Player creation queue.
     *
     * @param value the value
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
     * Get current player id int.
     *
     * @return the int
     */
    public int getCurrentPlayerID(){ //usato solamente per i test
        return this.currentPlayerID;
    }
}


