package it.polimi.ingsw.controller;

import it.polimi.ingsw.gods.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.messages.ActionMessage;
import it.polimi.ingsw.utils.messages.GenericMessage;
import it.polimi.ingsw.utils.messages.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TurnHandler implements PropertyChangeListener {
    private Model model;
    private int playersPerGame;
    private int playerDefeatedID = -1;
    private int totalTurnCounter = 0;
    private int firstPlayerID;

    public TurnHandler(Model model, int playersPerGame) {
        this.model = model;
        this.playersPerGame = playersPerGame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("actionsRequest")) {
            int id = actualPlayerID();
            Message message = (GenericMessage) evt.getNewValue();
            if (message.getId() == id) {
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
            if(totalTurnCounter!=firstPlayerID){
                playerDefeatedID = (int)evt.getNewValue();
                this.model.removeModelListener((PropertyChangeListener)evt.getOldValue());
                playerHasLost((int)evt.getNewValue());
            }
            else {
                this.model.removeModelListener((PropertyChangeListener)evt.getOldValue());
                model.endGame(-1);

            }
        }

    }

    //fa finire il turno e crea l'albero per il giocatore succesivo + controllo se ha perso
    private void endTurnManager(int id) {
        if (!model.checkWinner(id)) {
            model.endTurn(id);
            totalTurnCounter++;
        }else model.endGame(id);

    }

    //fa quello che deve fare se si è rilevato che un player ha perso
    public void playerHasLost(int id) {
        if (playersPerGame == 2) {
            model.endGameForNoAvailableMoves(id);
        } else {
            playerDefeatedID = id;
            model.removePlayer(id);
            totalTurnCounter++;
        }
    }

    //restituisce l'id del giocatore in questo turno
    public int actualPlayerID() {
        if (playerDefeatedID == totalTurnCounter % playersPerGame)
            totalTurnCounter++;
        return totalTurnCounter % playersPerGame;
    }

    //imposta il primo giocatore
    public void setTotalTurnCounter(int id) {
        this.totalTurnCounter = id;
        this.firstPlayerID = id;
    }


}
