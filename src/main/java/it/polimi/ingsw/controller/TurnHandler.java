package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.messages.ActionMessage;
import it.polimi.ingsw.utils.messages.GenericMessage;
import it.polimi.ingsw.utils.messages.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The type Turn handler is the controller used
 * for handle the game.
 *
 * */
public class TurnHandler implements PropertyChangeListener {
    private final Model model;
    private final int playersPerGame;
    private int playerDefeatedID = -1;
    private int totalTurnCounter = 0;
    private boolean gameStarted = false;

    /**
     * Instantiates a new Turn handler.
     *
     * @param model          the model
     * @param playersPerGame the number of players per game
     */
    public TurnHandler(Model model, int playersPerGame) {
        this.model = model;
        this.playersPerGame = playersPerGame;
    }

    /**
     * Property change.
     *
     * Handles the events coming from
     * the SocketClientConnections
     *
     * @param evt the event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("actionsRequest")) {
            gameStarted = true;
            int id = actualPlayerID();
            Message message = (GenericMessage) evt.getNewValue();
            if (message.getId() == id && !model.isWinnerDetected()) {
                model.sendActions(id);
                if (model.getPlayer(id).checkLoser())
                    playerHasLost(id);
            }
        } else if (evt.getPropertyName().equalsIgnoreCase("notifyAction")) {
            ActionMessage message = (ActionMessage) evt.getNewValue();
            int id = actualPlayerID();
            if (message.getId() == id) {
                model.turnHandler(id, message.getAction());
                if (model.getPlayer(id).hasDone()) {
                    endTurnManager(id);
                } else if(model.getPlayer(id).essentialDone()){
                    if(model.checkWinner(id)){
                        model.endGame(id);
                    }
                }
            }
        } else if (evt.getPropertyName().equalsIgnoreCase("endTurn")) {
            GenericMessage message = (GenericMessage) evt.getNewValue();
            int id = actualPlayerID();
            if (message.getId() == id) {
                if (model.getPlayer(id).essentialDone()) {
                    endTurnManager(id);
                }
            }
        } else if(evt.getPropertyName().equalsIgnoreCase("playerDisconnected")){
            if(gameStarted){
                this.model.removeModelListener((PropertyChangeListener)evt.getOldValue());
                if((int)evt.getNewValue() != playerDefeatedID && !model.isWinnerDetected())playerHasLost((int)evt.getNewValue());
            }
            else {
                this.model.removeModelListener((PropertyChangeListener)evt.getOldValue());
                model.endGame(-1);

            }
        }

    }

    /**
     * Handles the end of a turn of
     * the player with ID id
     *
     * @param id the id of the player who
     *           ended his turn
     */
    private void endTurnManager(int id) {
        if (!model.checkWinner(id)) {
            totalTurnCounter++;
            model.endTurn(id);
        }else model.endGame(id);

    }

    /**
     * This method is called whenever a
     * player cannot play anymore.
     *
     * @param id the id of the player to be
     *           removed.
     */
    public void playerHasLost(int id) {
        if (model.getNumOfPlayers() == 2) {
            if(id == actualPlayerID()) totalTurnCounter++;
            model.endGameForNoAvailableMoves(id);
        } else {
            playerDefeatedID = id;
            model.removePlayer(id);
            if(id == actualPlayerID())totalTurnCounter++;
            model.notifyPlayerHasLost(id);
        }
    }

    /**
     * Handles the actual playerID
     * of the game
     *
     * @return the id of the actual player
     */
    public int actualPlayerID() {
        if (playerDefeatedID == totalTurnCounter % playersPerGame) {
            totalTurnCounter++;
            System.out.println("SERVER SALTO IL MORTO");
        }
        return totalTurnCounter % playersPerGame;
    }

    /**
     * Sets the initial id of the first player.
     *
     * @param id the id of the player chose by
     *           the godly
     */
    public void setTotalTurnCounter(int id) {
        this.totalTurnCounter = id;
    }


}
