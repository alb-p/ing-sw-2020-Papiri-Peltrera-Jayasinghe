package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ActionsEnum;
import java.util.HashMap;
import java.util.ArrayList;

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
            //  this.card = new Atlas();
        } else if (card.equals("ARTEMIS")) {
            //  this.card = new Artemis();
        } else if (card.equals("ATHENA")) {
            //  this.card = new Athena();
        } else if (card.equals("ATLAS")) {
            //  this.card = new Atlas();
        } else if (card.equals("DEMETER")) {
            //  this.card = new Demeter();
        } else if (card.equals("HEPHAESTUS")) {
            //  this.card = new Hephaestus();
        } else if (card.equals("MINOTAUR")) {
            //  this.card = new Minotaur();
        } else if (card.equals("PAN")) {
            //  this.card = new Pan();
        } else if (card.equals("PROMETHEUS")) {
            //this.card = new Prometheus();
        }
        //TODO when gods are done remove this statement
        this.card = new BasicGodCard();

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
        boolean build;
        boolean move;
        build = false;
        move = false;
        //viene attivato per il primo giocatore
        if (actualWorker == null) {
            for (TreeActionNode t : treeMap.get(this.getWorker(0)).getChildren()) {
                if (t.getData() instanceof Build) {
                    build = true;
                } else if (t.getData() instanceof Move) {
                    move = true;
                }

            }
            for (TreeActionNode t : treeMap.get(this.getWorker(1)).getChildren()) {
                if (t.getData() instanceof Build) {
                    build = true;
                } else if (t.getData() instanceof Move) {
                    move = true;
                }

            }
            System.out.println("FINITO ALBERI PER PRIMO TURNO GIOCATORE");
        } else {
            for (TreeActionNode t : treeMap.get(actualWorker).getChildren()) {
                if (t.getData() instanceof Build) {
                    build = true;
                } else if (t.getData() instanceof Move) {
                    move = true;
                }

            }
        }
        if (build && move) {
            System.out.println("BOTH");
            return ActionsEnum.BOTH;
        } else if (build) {
            System.out.println("BUILD");
            return ActionsEnum.BUILD;
        } else if (move) {
            System.out.println("MOVE");
            return ActionsEnum.MOVE;
        }
        return null;
    }
}
