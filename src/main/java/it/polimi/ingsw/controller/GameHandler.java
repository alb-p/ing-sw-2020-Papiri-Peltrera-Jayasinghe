package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.InitSetup;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.GodMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;
import it.polimi.ingsw.utils.messages.Select3GodsMessage;

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
        if (evt.getPropertyName().equals("nickMessageResponse")) {              //arriva un nick da virtualview arrivata da scc
            NicknameMessage message = (NicknameMessage) evt.getNewValue();
            String name = message.getNick();
            if (!data.isInUser(name)) {                                         //se il nick è unico lo in InitSetup e crea
                data.setUsername(name);                                         //una coda di preparazione per la creazione del player
                playerCreationQueue(message);
                data.askColor(message.getId());
            }else{
                data.WrongUsername(message.getId());
            }
        } else if (evt.getPropertyName().equals("colorMessageResponse")) {      //arriva un colore da virtualview arrivata da scc
            ColorMessage message = (ColorMessage) evt.getNewValue();
            String color = message.getColor();
            if (data.isInColor(color)) {                                        //se il colore è valido lo cancella da InitSetup
                data.delColor(message);                                         // e passa alla creazione del player
                playerCreationQueue(message);
            }else{
                data.askColor(message.getId());
            }
        } else if (evt.getPropertyName().equals("3godsResponse")) {        //arriva un god da virtualview arrivata da scc
            Select3GodsMessage message = (Select3GodsMessage) evt.getNewValue();
            ArrayList<String> listGods = message.getSelectedList();
            int check=0;
            for(String s:listGods){
                if (!data.isInListGod(s)) check++;
            }
            if (check==0) data.setChosenGods(listGods,message.getId());
            else data.choose3cards(message.getId());


        }else if (evt.getPropertyName().equals("godMessageResponse")) {        //arriva un god da virtualview arrivata da scc
            GodMessage message = (GodMessage) evt.getNewValue();
            String god = message.getGod();
            if (data.isInGod(god)) {                                        //se il colore è valido lo cancella da InitSetup
                data.delGod(message);                                         // e passa alla creazione del player
                playerCreationQueue(message);
            }else{
                data.askColor(message.getId());
            }
        }
    }

    private void playerCreationQueue(Object value) {                                                                        //la mappa è del tipo
        if (value instanceof NicknameMessage) {                                                                             // 1 -> Mario
            playersMap.put(((NicknameMessage) value).getId(), ((NicknameMessage) value).getNick());                         // 2 -> Luca
        } else if (value instanceof ColorMessage) {                                                                         // 3 -> Andrea
            ColorMessage message = (ColorMessage) value;                                                                    //quando arriva il colore si procede alla
            model.addPlayer(new Player(message.getId(), playersMap.get(message.getId()), message.getColor()));              //creazione del player

            if(model.getNumOfPlayers()==this.playersPerGame){
                Random random=new Random();
                int firstplayerID=random.nextInt(3);
                data.choose3cards(firstplayerID);

            }
        }

    }


    public void setTurnhandler(TurnHandler turnHandler) {
        this.turnHandler = turnHandler;
    }
}







































