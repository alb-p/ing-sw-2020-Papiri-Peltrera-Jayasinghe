package it.polimi.ingsw.model;

import it.polimi.ingsw.gods.*;
import it.polimi.ingsw.utils.ActionsEnum;
import it.polimi.ingsw.utils.messages.ActionMessage;

import java.awt.*;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class Player {


    private ArrayList<Worker> workers = new ArrayList<Worker>();
    private HashMap<Worker, TreeActionNode> treeMap = new HashMap<Worker, TreeActionNode>();

    private String nickName;
    private BasicGodCard card;
    private boolean done = false;
    private boolean moveDone = false;
    private boolean buildDone = false;

    private int id;
    private Worker actualWorker = null;


    public Player(String nickName, String color) {
        this.nickName = nickName;
        workers.add(new Worker(0, 0, color));
        workers.add(new Worker(0, 0, color));
    }

    public Player(int id, String nickName, String color) {
        this.nickName = nickName;
        this.id = id;
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
        } else {
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

    public ArrayList<TreeActionNode> playerTreeSetup(IslandBoard board) throws Exception {
        TreeActionNode treeW0 = this.card.cardTreeSetup(this.getWorker(0), board);
        TreeActionNode treeW1 = this.card.cardTreeSetup(this.getWorker(1), board);
        ArrayList<TreeActionNode> list = new ArrayList<TreeActionNode>();

        treeMap.put(this.getWorker(0), treeW0);
        treeMap.put(this.getWorker(1), treeW1);
        //TODO queste list sono necessarie???
        list.add(treeW0);
        list.add(treeW1);
        return list;

    }


    public boolean turnHandler(IslandBoard board, Action message) throws Exception {
        boolean actionResult = false;
        TreeActionNode attemptedActionNode = treeMap.get(actualWorker).search(message);
        if (attemptedActionNode == null) {
            //TODO mossa non valida, da comunicare verso il client all'interno di un eventuale pacchetto specifico
            return false;
        }
        treeMap.remove(actualWorker);
        treeMap.put(actualWorker, attemptedActionNode);
        actionResult = this.card.turnHandler(this, board, message);
        if(!actionResult){
            //TODO sollevo eccezione? come gestire
            return false;
        }
        if(message instanceof Move) moveDone = true;
        else if(message instanceof Build) buildDone = true;

        if (moveDone && buildDone) {
            if(attemptedActionNode.isLeaf()) {
                done = true;
            }else{

            }
        }
        return true;
    }


    public boolean hasDone() {
        return this.done;
    }

    public int getId() {
        return id;
    }

    public void setNotDone() {
        this.done = false;
    }

    public boolean selectWorker(Coordinate coord) {
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getPosition().equals(coord)) {
                actualWorker = workers.get(i);
            }
        }
        return (actualWorker != null);
    }

    public Worker getActualWorker() {
        return this.actualWorker;
    }


    public Object getTrees() {
        return treeMap;
    }

    public TreeActionNode getTree(Worker worker) {
        return (treeMap.get(worker));
    }

    public Object getHashList() {
        return treeMap.keySet();
    }

    public ActionsEnum getAvailableAction() {
        boolean build = false;
        boolean move = false;
        for (TreeActionNode t : treeMap.get(actualWorker).getChildren()) {
            if (t.getData() instanceof Build) {
                build = true;
            } else if (t.getData() instanceof Move) {
                move = true;
            }
        }
        if (build && move) return ActionsEnum.BOTH;
        else if (build) return ActionsEnum.BUILD;
        else if (move) return ActionsEnum.MOVE;
        else return null;
    }
}
