package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.utils.messages.ActionMessage;
import it.polimi.ingsw.utils.messages.ChoiceMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TurnHandler implements PropertyChangeListener {
    private Model model;
    private int playersPerGame;
    private int playerDefeatedID = -1;
    private int totalTurnCounter = 0;

    //int indice giocatore inziale
    public TurnHandler(Model model, int playersPerGame) {
        this.model = model;
        this.playersPerGame = playersPerGame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("actionMessageResponse")) {
            ActionMessage message = (ActionMessage) evt.getNewValue();
            turnManager(message);
        } else if (evt.getPropertyName().equals("gameReadyResponse")) {
            if (model.buildPlayerTree(actualPlayer())) {
                playerHasLost(actualPlayer());
            } else model.selectPlayerPlaying(actualPlayer());

        } else if (evt.getPropertyName().equals("choiceResponse")) {


            if (!model.selectChoice((ChoiceMessage) evt.getNewValue())) {
                endTurnManager();
            }
        }
    }

    private void turnManager(ActionMessage message) {
        model.turnHandler(actualPlayer(), message.getAction());
        if (!model.getPlayer(actualPlayer()).hasDone()) {
            model.selectPlayerPlaying(actualPlayer());
        } else {
            endTurnManager();
        }
    }


    private void endTurnManager() {
        if (model.checkWinner(actualPlayer())) {
            //game ended
        } else {
            model.getPlayer(actualPlayer()).setNotDone();
            totalTurnCounter++;
            if (model.buildPlayerTree(actualPlayer())) {
                playerHasLost(actualPlayer());
            }
            model.selectPlayerPlaying(actualPlayer());
        }
    }

    public void playerHasLost(int id) {
        model.notifyPlayerHasLost(id);
        if (playersPerGame == 2) {
            model.endGameForNoAvailableMoves(id);
        } else {
            playerDefeatedID = id;
            model.removePlayer(id);
            totalTurnCounter++;
            if (model.buildPlayerTree(actualPlayer())) {
                playerHasLost(actualPlayer());
            }
            model.selectPlayerPlaying(actualPlayer());
        }
    }

    public int actualPlayer() {
        if (playerDefeatedID == totalTurnCounter % playersPerGame) {
            totalTurnCounter++;
        }
        return totalTurnCounter % playersPerGame;
    }

    public void setTotalTurnCounter(int tt) {
        this.totalTurnCounter = tt;
    }
}
