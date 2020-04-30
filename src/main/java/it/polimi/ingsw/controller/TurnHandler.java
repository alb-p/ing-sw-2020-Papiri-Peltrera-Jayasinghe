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
        } else if (evt.getPropertyName().equals("gameReadyResponse")){
            model.buildPlayerTree(totalTurnCounter%playersPerGame);
            System.out.println("BUILDTREE DONE");
            model.selectPlayerPlaying(totalTurnCounter%playersPerGame);
        }
    }

    private void turnManager(ActionMessage message) {
        model.turnHandler(totalTurnCounter%playersPerGame, message.getAction());
        if(!model.getPlayer(totalTurnCounter%playersPerGame).hasDone()){
            model.selectPlayerPlaying(totalTurnCounter%playersPerGame);
        }else{
            endTurnManager();
        }
    }


    private void endTurnManager(){
        model.getPlayer(totalTurnCounter%playersPerGame).setNotDone();
        model.checkWinner(totalTurnCounter%playersPerGame);
        totalTurnCounter++;
        model.buildPlayerTree(totalTurnCounter%playersPerGame);
        model.selectPlayerPlaying(totalTurnCounter%playersPerGame);
    }

    public void setTotalTurnCounter(int tt){
        this.totalTurnCounter = tt;
    }
}
