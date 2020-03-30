package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private Model model;
    private View view;
    private int moment;
    private int numberOfPlayers;

    public Controller(Model model, View view) {

        this.model = model;
        this.view = view;
    }


    @Override
    public void update(Observable o, Object arg) {

        if (o != view) {
            System.out.println("Error!");
        } else if (arg instanceof Integer) {

            numberOfPlayers = (Integer) arg;
            try {
                initPlayers(numberOfPlayers);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (arg instanceof String) {
            try {
                model.turnHan( moment%numberOfPlayers, (String) arg);
                moment++;
                System.out.println(moment+ " <- moment capsule molli");
            } catch (Exception e) {
                e.printStackTrace();
                moment--;
            }
        }


    }

    private void initPlayers(Integer k) throws Exception {

        String color;
        ArrayList<String> colorList = new ArrayList<String>();
        for (Color c : Color.values().clone()) {
            colorList.add(c.getName());
        }

        for (int i = 0; i < k; i++) {

            String nick = this.view.setNick(i);

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
    }
}
