package it.polimi.ingsw.model;

import it.polimi.ingsw.gods.*;
import it.polimi.ingsw.utils.ActionsEnum;
import it.polimi.ingsw.utils.messages.ActionMessage;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class Player {


    private ArrayList<Worker> workers = new ArrayList<Worker>();
    private HashMap<Worker, TreeActionNode> treeMap = new HashMap<Worker, TreeActionNode>();

    private String nickName;
    private BasicGodCard card;
    private boolean done;
    private boolean moveDone;
    private boolean buildDone;


    private int id;
    private Color color;
    private Worker actualWorker;


    public Player(String nickName, String color) {
        this.nickName = nickName;
        this.actualWorker = null;
        this.done = false;
        this.moveDone = false;
        this.buildDone = false;
        workers.add(new Worker(0, 0, color));
        workers.add(new Worker(0, 0, color));
    }

    public Player(int id, String nickName, Color color) {
        this.nickName = nickName;
        this.id = id;
        this.actualWorker = null;
        this.done = false;
        this.moveDone = false;
        this.buildDone = false;
        workers.add(new Worker(new Coordinate(0, 0), color));
        workers.add(new Worker(new Coordinate(0, 0), color));
    }

    public Player(int id, String nickName, String color) {
        this.nickName = nickName;
        this.id = id;
        workers.add(new Worker(0, 0, color));
        workers.add(new Worker(0, 0, color));
    }

    public void setCard(String card) {

        BasicGodCard godCard = new BasicGodCard();

        if (card.equals("APOLLO")) {
            this.card = new Apollo();
        } else if (card.equals("ARTEMIS")) {
            this.card = new Artemis();
        } else if (card.equals("ATHENA")) {
            this.card = new Athena();
        } else if (card.equals("ATLAS")) {
            this.card = new Atlas();
        } else if (card.equals("DEMETER")) {
            this.card = new Demeter();
        } else if (card.equals("HEPHAESTUS")) {
            this.card = new Hephaestus();
        } else if (card.equals("MINOTAUR")) {
            this.card = new Minotaur();
        } else if (card.equals("PAN")) {
            this.card = new Pan();
        } else if (card.equals("PROMETHEUS")) {
            this.card = new Prometheus();
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
        boolean actionResult;
        Worker attemptedWorker;
        TreeActionNode attemptedActionNode;
        attemptedActionNode = null;
        attemptedWorker = board.infoSlot(message.getStart()).getWorker();

        System.out.println("ACTUALW IN PLAYER:: " + actualWorker + " THIS:COLOR:: " + this.getWorker(0).getColor());
        if (attemptedWorker != null && (attemptedWorker.getColor() == (this.getWorker(0).getColor()))) {
            if (actualWorker == null) {
                this.actualWorker = attemptedWorker;
                System.out.println("ACTUAL WORKER COLOR CHECK");

            } else if (!actualWorker.equals(attemptedWorker)) {
                return false;
            }
            attemptedActionNode = treeMap.get(actualWorker).search(message);
        }
        if (attemptedActionNode == null) {
            //TODO mossa non valida, da comunicare verso il client all'interno di un eventuale pacchetto specifico
            if (!moveDone && !buildDone) {
                actualWorker = null;
            }
            return false;
        }
        treeMap.remove(actualWorker);
        treeMap.put(actualWorker, attemptedActionNode);
        actionResult = this.card.turnHandler(this, board, message);
        if (!actionResult) {
            //TODO sollevo eccezione? come gestire
            return false;
        }
        if (message instanceof Move) {
            moveDone = true;
            //TODO selezione worker giusta sempre?

        } else if (message instanceof Build) buildDone = true;

        if (moveDone && buildDone) {
            if (attemptedActionNode.isLeaf()) {
                done = true;
            } else {
                //for Demeter

            }
        }
        return true;
    }


    public boolean hasDone() {
        return this.done;
    }

    public void setNotBuildDone() {
        buildDone = false;
    }

    public int getId() {
        return id;
    }

    public void setNotDone() {
        this.done = false;
        this.moveDone = false;
        this.buildDone = false;
        actualWorker = null;
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


    public HashMap<Worker, TreeActionNode> getTrees() {
        return treeMap;
    }

    public TreeActionNode getTree(Worker worker) {
        return (treeMap.get(worker));
    }

    public Set<Worker> getHashList() {
        return treeMap.keySet();
    }

    public ActionMessage getAvailableAction() {
        ArrayList<Action> actionList = new ArrayList<>();
        ActionMessage message = new ActionMessage(this.id, this.nickName);

        if (actualWorker != null) {
            actionList.addAll(treeMap.get(this.actualWorker).getChildrenActions());
        } else {
            for (Worker w : workers) {
                ArrayList<Action> actions = new ArrayList<>();
                actions.addAll(treeMap.get(w).getChildrenActions());
                for (Action a : actions) {
                    boolean isIn;
                    isIn = false;
                    for (Action d : actionList) {
                        if (d.getActionName().equalsIgnoreCase(a.getActionName())) isIn = true;
                    }
                    if (!isIn) actionList.add(a);
                }
            }
        }
        for (Action a : actionList) {
            System.out.println("ACTIONLIST :: " + a.getActionName());
        }
        if (actionList.size() == 0) {
            System.out.println("NO AVAILABLE ACTIONS");
            return null;
        } else if (actionList.size() == 1) {
            if (moveDone && buildDone) {
                message.setActionsAvailable(ActionsEnum.BOTH);
                message.getChoices().add(actionList.get(0).getActionName());
                message.getChoices().add("ENDTURN");
                return message;
            }
            message.setAction(actionList.get(0).clone());
            message.setActionsAvailable(ActionsEnum.BUILD);
            System.out.println("COSTRUITO ACTIONMESSAGE ::" + message.getActionsAvailable());

            return message;

        } else {
            for (Action a : actionList) message.getChoices().add(a.getActionName());
            message.setActionsAvailable(ActionsEnum.BOTH);
        }

        return message;

    }

    public ActionMessage modifierTree(String message) {
        Worker worker;
        ActionMessage actionMessage = new ActionMessage(id, nickName);
        ArrayList<TreeActionNode> deleteNode = new ArrayList<>();

        if (actualWorker != null) {
            worker = actualWorker;
            for (TreeActionNode t : treeMap.get(actualWorker).getChildren()) {
                if (!t.getData().getActionName().equals(message)) {
                    deleteNode.add(t);
                }
            }
            treeMap.get(actualWorker).getChildren().removeAll(deleteNode);
            /*
            for(TreeActionNode t : deleteNode){
                treeMap.get(actualWorker).getChildren().remove(t);
            }*/
            if (treeMap.get(actualWorker).isLeaf()) {
                done = true;
                return null;
            } else {
                actionMessage.setAction(treeMap.get(actualWorker).getChildren().get(0).getData().clone());
                actionMessage.setActionsAvailable(ActionsEnum.BUILD);
            }
        } else {
            for (Worker w : workers) {
                for (TreeActionNode t : treeMap.get(w).getChildren()) {
                    if (!t.getData().getActionName().equals(message)) {
                        deleteNode.add(t);
                    }
                }
                treeMap.get(w).getChildren().removeAll(deleteNode);
            }
            worker = workers.get(0);
            if (treeMap.get(worker).isLeaf()) {
                worker = workers.get(1);
            }
            actionMessage.setAction(treeMap.get(worker).getChildren().get(0).getData().clone());
            actionMessage.setActionsAvailable(ActionsEnum.BUILD);

        }

        return actionMessage;
    }
}
