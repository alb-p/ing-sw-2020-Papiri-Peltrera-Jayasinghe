package it.polimi.ingsw.model;

import it.polimi.ingsw.gods.*;

import java.awt.*;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class Player {


    private ArrayList<Worker> workers = new ArrayList<Worker>();
    private HashMap<Worker,TreeActionNode> treeMap = new HashMap<Worker, TreeActionNode>();

    private String nickName;
    private BasicGodCard card;
    private boolean done = false;
    private boolean halfDone = false;

    public Player(String nickName, String color) {
        this.nickName = nickName;
        workers.add(new Worker(0, 0, color));
        workers.add(new Worker(0, 0, color));
        treeMap.put(getWorker(0),null); //necessario? guarda riga 81
        treeMap.put(getWorker(1),null); //necessario? guarda riga 82


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

    public ArrayList<TreeActionNode> playerTreeSetup(IslandBoard board){
        TreeActionNode treeW0 = this.card.cardTreeSetup(this.getWorker(0),board);
        TreeActionNode treeW1 = this.card.cardTreeSetup(this.getWorker(1),board);
        ArrayList<TreeActionNode> list= new ArrayList<TreeActionNode>();

        treeMap.put(this.getWorker(0),treeW0);
        treeMap.put(this.getWorker(1),treeW1);
        list.add(treeW0);
        list.add(treeW1);
        return list;

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
