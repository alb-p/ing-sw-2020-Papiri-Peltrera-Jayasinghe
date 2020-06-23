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
    private boolean gameStarted = false;

    public TurnHandler(Model model, int playersPerGame) {
        this.model = model;
        this.playersPerGame = playersPerGame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        System.out.println("ACTUAL PLAYER  __"+actualPlayerID());
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

    //fa finire il turno
    private void endTurnManager(int id) {
        System.out.println("\t\t\t\tEND TURN MANAGER CALLED BY ID: "+id);
        if (!model.checkWinner(id)) {
            totalTurnCounter++;
            model.endTurn(id);
        }else model.endGame(id);

    }

    //fa quello che deve fare se si è rilevato che un player ha perso
    public void playerHasLost(int id) {
        if (model.getNumOfPlayers() == 2) {
            model.endGameForNoAvailableMoves(id);
        } else {
            playerDefeatedID = id;
            model.removePlayer(id);
            System.out.println("ACTUAL ID "+actualPlayerID()+ " LOST ID "+id);
            if(id == actualPlayerID())totalTurnCounter++;
            model.notifyPlayerHasLost(id);
        }
    }

    //restituisce l'id del giocatore in questo turno
    public int actualPlayerID() {
        if (playerDefeatedID == totalTurnCounter % playersPerGame) {
            totalTurnCounter++;
            System.out.println("SERVER SALTO IL MORTO");
        }
        return totalTurnCounter % playersPerGame;
    }

    //imposta il primo giocatore
    public void setTotalTurnCounter(int id) {
        this.totalTurnCounter = id;
        System.out.println(model.getPlayer(0).getNickName() + " ID"+0);
        System.out.println(model.getPlayer(1).getNickName() + " ID"+1);
    }


}
