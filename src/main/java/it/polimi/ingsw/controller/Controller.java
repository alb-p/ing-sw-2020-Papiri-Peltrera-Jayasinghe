package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class Controller implements PropertyChangeListener {


    private Model model;
    private VirtualView view;
    private int moment = 0;
    private int numberOfPlayers;


    public Controller(Model model, VirtualView view) {

        this.model = model;
        this.view = view;
    }

    public void propertyChange(PropertyChangeEvent evt){
        if (evt.getPropertyName().equals("playersNumber")) {
            numberOfPlayers = (Integer) evt.getNewValue();
            try {
                //initPlayers(numberOfPlayers);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (evt.getPropertyName().equals("playerAction")) {
            try {
                if (model.getPlayer(moment%numberOfPlayers).hasDone()) {
                    moment++;
                }

                model.turnHandler(moment % numberOfPlayers, (String) evt.getNewValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
    private void initPlayers(Integer k) throws Exception {

        boolean sameName;
        String nick = null;
        String color;
        ArrayList<String> colorList = new ArrayList<String>();
        for (Color c : Color.values().clone()) {
            colorList.add(c.getName());
        }

        //TODO rename k var
        for (int i = 0; i < k; i++) {

            sameName = false;
            while(!sameName) {
                // TODO verify all names are different
                nick = this.view.setNick(i);
                if (!model.nameDuplicate(nick)) sameName = true;
            }

            if (i == 2) {
                color = colorList.get(0);

            } else {
                color = this.view.setColor(colorList, nick);
                colorList.remove(color);
            }
            this.model.addPlayer(new Player(nick, color));

            for (int j = 0; j < 2; j++) {

                Coordinate coord = this.view.setWorkers(j);
                this.model.addWorker(i, coord, j);
            }

            String card = this.view.setCard(i);
            this.model.setCard(i, card);
        }
    }*/
}
