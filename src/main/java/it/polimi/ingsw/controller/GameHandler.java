package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.InitSetup;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.SocketClientConnection;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.GodMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

public class GameHandler implements PropertyChangeListener {

    InitSetup data;
    HashMap< Integer, String> playersMap=new HashMap<>();
    Model model;

    public GameHandler(InitSetup initSetup, Model m) {
        data = initSetup;
        model=m;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) { //gestire caso input errato
        if (evt.getPropertyName().equals("nickMessageResponse")) {
            NicknameMessage message = (NicknameMessage) evt.getNewValue();
            String name = message.getNick();
            if (!data.isInUser(name))
                data.setUsername(name);
            playerCreationQueue(message);
            data.askColor(message.getIndentificativo());
            //else
            //data.wrongUsername(evt);
        }else if (evt.getPropertyName().equals("colorMessageResponse")) {
            ColorMessage message= (ColorMessage) evt.getNewValue();
            String color = message.getColor();
            if (data.isInColor(color))
                data.delColor(message);
                playerCreationQueue(message);
            //else
            //data.wrongColor(evt);
        } else if (evt.getPropertyName().equals("godMessageResponse")) {
            GodMessage message= (GodMessage) evt.getNewValue();
            String god = message.getGod();
            if (data.isInGod(god))
                data.delGod(god);
            //else
            //data.wrongGod(evt);
        }
    }

    private void playerCreationQueue(Object value) {
        if(value instanceof NicknameMessage ){
            playersMap.put(((NicknameMessage) value).getIndentificativo(),((NicknameMessage) value).getNick());
        }else if(value instanceof ColorMessage){
            ColorMessage message= (ColorMessage)value;
            model.addPlayer(new Player(playersMap.get(message.getIndentificativo()),message.getColor()));
        }

    }


}
