package it.polimi.ingsw.model;

//import it.polimi.ingsw.gods.Artemis;
//import it.polimi.ingsw.gods.Atlas;

import it.polimi.ingsw.gods.Demeter;
import it.polimi.ingsw.gods.Prometheus;

import java.util.Scanner;
import java.util.ArrayList;

public class Player {


    private ArrayList<Worker> workers = new ArrayList<Worker>();

    private String nickName;
    private BasicGodCard card;
    private boolean done = false;
    private boolean halfDone = false;

    public Player(String nickName, String color) {
        this.nickName = nickName;
        workers.add(new Worker(0, 0, color));
        workers.add(new Worker(0, 0, color));

    }

    public void setCard(String card) {

        BasicGodCard godCard = new BasicGodCard();

        if (card.equals("ATLAS")) {
            //  this.card = new Atlas();
            return;
        } else if (card.equals("ARTEMIS")) {
            //  this.card = new Artemis();
            return;
        } else if (card.equals("DEMETER")) {
            this.card = new Demeter();
            return;
        } else if (card.equals("PROMETHEUS")) {
            this.card = new Prometheus();
            return;
        }else {
            this.card = godCard;
        }
    }

    public BasicGodCard getCard() {
        return this.card;
    }

    public String getNickName() {
        return this.nickName;
    }


    public Worker getWorker(Coordinate c) {
        for (int i = 0; i < workers.size(); i++) {
            if (c.equals(workers.get(i).getPosition())) {
                return workers.get(i);
            }
        }
        return null;
    }

    public Worker getWorker(int i) {
        return this.workers.get(i);
    }

    public void turnHandler(IslandBoard board, String message) throws Exception {
        this.done = this.card.turnHandler(this, board, message, this.halfDone);
        if (!done) halfDone = true;
        if (done) halfDone = false;
    }


    public boolean hasDone() {
        return this.done;
    }

}
