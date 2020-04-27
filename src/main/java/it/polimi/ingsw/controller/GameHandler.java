package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.InitSetup;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameHandler implements PropertyChangeListener {

    private InitSetup data;
    private HashMap<Integer, String> playersMap = new HashMap<>();                        //associazione    numero -> nome
    private Model model;
    private TurnHandler turnHandler;
    private int playersPerGame;

    public GameHandler(InitSetup initSetup, Model m, int playersPerGame) {
        data = initSetup;
        model = m;
        this.playersPerGame = playersPerGame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("nickMessageResponse")) {
            NicknameMessage message = (NicknameMessage) evt.getNewValue();
            String name = message.getNick();                                            //arriva un nick da virtualview arrivata da scc
            if (!data.isInUser(name)) {                                                 //se il nick è unico lo in InitSetup e crea
                data.setUsername(name);                                                 //una coda di preparazione per la creazione del player
                playerCreationQueue(message);
                data.askColor(message.getId());
            } else {
                data.wrongUsername(message.getId());
            }
        } else if (evt.getPropertyName().equals("colorMessageResponse")) {
            ColorMessage message = (ColorMessage) evt.getNewValue();
            Color color = message.getColor();                                          //arriva un colore da virtualview arrivata da scc
            if (data.isInColor(color)) {                                                //se il colore è valido lo cancella da InitSetup
                data.delColor(message);                                                 // e passa alla creazione del player
                playerCreationQueue(message);
            } else {
                data.askColor(message.getId());
            }
        } else if (evt.getPropertyName().equals("initialCardsResponse")) {              //arriva una lista di god da virtualview arrivata da scc
            InitialCardsMessage message = (InitialCardsMessage) evt.getNewValue();      //se i god sono validi e di quanttà corretta vengono salvati
                                                                                        //e si passa alla richiesta del god al primo giocatore
                                                                                        //altrimenti viene richiesta la lista
            for (String s : message.getSelectedList()) if (data.isInListGod(s)) data.addChosenGod(s);
            if (data.ChosenGodsSize() == playersPerGame) {
                int nextID =( message.getId() + 1) % playersPerGame;
                data.setChosenGods(nextID);
            } else {
                data.initialCards(message.getId(), playersPerGame - data.ChosenGodsSize());
            }


        } else if (evt.getPropertyName().equals("godMessageResponse")) {                 //arriva un god da virtualview arrivata da scc
            GodMessage message = (GodMessage) evt.getNewValue();                        //se è valido lo asssegna al rispettivo player
            String god = message.getGod();                                              //altrimenti lo richiede
            if (data.isInGod(god)) {
                data.delGod(message, playersPerGame);
                try {
                    model.setCard(model.getIndex(message.getId()), god);
                } catch (CloneNotSupportedException e) {
                    System.out.println("errore nell'inserimento della divinità");
                }
            } else {
                data.askGod(message.getId());
            }
        }else if (evt.getPropertyName().equals("firstPlayerResponse")) {
            FirstPlayerMessage message = (FirstPlayerMessage) evt.getNewValue();
            String name = message.getChosenName();
            if(data.isInUser(name)){
               for(int i=0;i<playersPerGame;i++){
                   if(playersMap.get(i).equals(name)){
                       System.out.println("FIRSTPLAYERID "+i);
                       turnHandler.setTotalTurnCounter(i);
                       break;
                   }
               }
            }else{
                data.askFirstPlayer(message.getId());
            }
        }
    }


    //TODO possibile cambiare Object in Message?
    private void playerCreationQueue(Object value) {                                                                        //la mappa è del tipo
        if (value instanceof NicknameMessage) {                                                                             // 1 -> Mario
            playersMap.put(((NicknameMessage) value).getId(), ((NicknameMessage) value).getNick());                         // 2 -> Luca
        } else if (value instanceof ColorMessage) {                                                                         // 3 -> Andrea
            ColorMessage message = (ColorMessage) value;                                                                    //quando arriva il colore si procede alla
            System.out.println("PRESO : " + message.getColor());
            model.addPlayer(new Player(message.getId(), playersMap.get(message.getId()), message.getColor().getName()));              //creazione del player
            if (model.getNumOfPlayers() == this.playersPerGame) {                       //se tutti i player sono stati creati
                Random random = new Random();                                         //un giocatore a caso sceglie le divinità
                int lastPlayerID = random.nextInt(model.getNumOfPlayers());           //di partenza e sarà ultimo nel turno
                System.out.println("l'ultimo player è: " + lastPlayerID);
                data.initialCards(lastPlayerID, model.getNumOfPlayers());

            }
        }

    }


    public void setTurnhandler(TurnHandler turnHandler) {
        this.turnHandler = turnHandler;
    }
}







































