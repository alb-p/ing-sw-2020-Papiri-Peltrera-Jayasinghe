package it.polimi.ingsw.controller;

import it.polimi.ingsw.gods.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.ActionsEnum;
import it.polimi.ingsw.utils.messages.ActionMessage;
import it.polimi.ingsw.utils.messages.ChoiceMessage;
import it.polimi.ingsw.utils.messages.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TurnHandler implements PropertyChangeListener {
    private Model model;
    private int playersPerGame;
    private int playerDefeatedID = -1;
    private int totalTurnCounter = 0;

    public TurnHandler(Model model, int playersPerGame) {
        this.model = model;
        this.playersPerGame = playersPerGame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("actionsRequest")) {
            Message message = (Message) evt.getNewValue();
            int ID = actualPlayerID();
            if (message.getId() == ID) {
                //TODO create tree only if first call to actionRequest,
                // otherwise send children of the root.
                model.sendActions(ID);
                //model.buildTree(ID);
                if (model.getPlayer(ID).checkLoser())
                    playerHasLost(ID);

            }
        }

        if (evt.getPropertyName().equalsIgnoreCase("notifyAction")) {
            ActionMessage message = (ActionMessage) evt.getNewValue();
            int ID = actualPlayerID();
            if (message.getId() == ID) {
                model.turnHandler(ID, message.getAction());
                if (model.getPlayer(ID).hasDone())
                    endTurnManager(ID);
            }
        }

    }

    //fa finire il turno e crea l'albero per il giocatore succesivo + controllo se ha perso
    private void endTurnManager(int ID) {
        if (!model.checkWinner(ID)) {
            model.getPlayer(ID).setEndTurn();
            totalTurnCounter++;


        }
    }

    //fa quello che deve fare se si è rilevato che un player ha perso
    public void playerHasLost(int ID) {
        if (playersPerGame == 2) {
            model.endGameForNoAvailableMoves(ID);
        } else {
            playerDefeatedID = ID;
            model.removePlayer(ID);
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
    public void setTotalTurnCounter(int ID) {
        this.totalTurnCounter = ID;
    }


}
