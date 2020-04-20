package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.utils.messages.ActionMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TurnHandler implements PropertyChangeListener {
    private Model model;
    private int playersPerGame;
    private int totalTurnCounter = 0;

 //int indice giocatore inziale
    public TurnHandler(Model model, int playersPerGame) {
        this.model = model;
        this.playersPerGame = playersPerGame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("actionMessageResponse")) {
            ActionMessage message = (ActionMessage)evt.getNewValue();
            turnManager(message);
        } else if (evt.getPropertyName().equals("endTurnMessageResponse")){
            endTurnManager((ActionMessage)evt.getNewValue());
        }
    }

    private void turnManager(ActionMessage message){
        //arriva azione, o inzio turno o n-azione
        model.getPlayer(message.getId());
    }

    public void setTotalTurnCounter(int tt){
        this.totalTurnCounter = tt;
    }

    public void endTurnManager(ActionMessage message){
        //model.getPlayer(totalTurnCounter
        totalTurnCounter++;
        //changePlayerPlaying()
        //crea gli alberi (è inizio turno nuovo)
    }
}
