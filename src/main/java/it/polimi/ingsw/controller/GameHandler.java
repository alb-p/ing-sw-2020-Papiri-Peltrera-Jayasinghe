package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.InitSetup;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.GodMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

public class GameHandler implements PropertyChangeListener {

    InitSetup data;
    HashMap<Integer, String> playersMap = new HashMap<>();                        //associazione    numero -> nome
    Model model;

    public GameHandler(InitSetup initSetup, Model m) {
        data = initSetup;
        model = m;
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
        } else if (evt.getPropertyName().equals("godMessageResponse")) {        //arriva un god da virtualview arrivata da scc
            GodMessage message = (GodMessage) evt.getNewValue();
            String god = message.getGod();
            if (data.isInGod(god))
                data.delGod(god);
            //else
            //data.wrongGod(evt);
        }
    }

    private void playerCreationQueue(Object value) {                                                    //la mappa è del tipo
        if (value instanceof NicknameMessage) {                                                         // 1 -> Mario
            playersMap.put(((NicknameMessage) value).getId(), ((NicknameMessage) value).getNick());     // 2 -> Luca
        } else if (value instanceof ColorMessage) {                                                     // 3 -> Andrea
            ColorMessage message = (ColorMessage) value;                                                //quando arriva il colore si procede alla
            model.addPlayer(new Player(playersMap.get(message.getId()), message.getColor()));           //creazione del player
        }

    }


}
