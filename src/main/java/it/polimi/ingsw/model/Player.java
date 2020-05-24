package it.polimi.ingsw.model;

import it.polimi.ingsw.gods.*;
import it.polimi.ingsw.utils.ActionsEnum;
import it.polimi.ingsw.utils.messages.ActionMessage;

import java.util.ArrayList;

public class Player {


    private ArrayList<Worker> workers = new ArrayList<Worker>();
    private TreeActionNode tree;

    private String nickName;
    private BasicGodCard card;
    private boolean done;
    private boolean moveDone;
    private boolean buildDone;
    private boolean hasLost = false;


    private int id;
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

    public Worker getWorker(int i) {
        return this.workers.get(i);
    }

    public Worker getActualWorker() {
        return this.actualWorker;
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

    public TreeActionNode getTree() {
        return this.tree;
    }

    public void playerTreeSetup(IslandBoard board) throws Exception {
        TreeActionNode tree = this.card.cardTreeSetup(this.getWorker(0), board);
        TreeActionNode temp = this.card.cardTreeSetup(this.getWorker(1), board);
        for (TreeActionNode t : temp.getChildren())
            tree.addChild(t);
        this.tree = tree;


    }

    public boolean turnHandler(IslandBoard board, Action message) throws Exception {
        TreeActionNode attemptedActionNode;
        attemptedActionNode = tree.search(message);
        this.actualWorker= board.infoSlot(message.getStart()).getWorker();
        this.tree = attemptedActionNode;
        if (!this.card.turnHandler(this, board, message))
            return false;//TODO mettere warning

        if (message instanceof Move)
            moveDone = true;

        else if (message instanceof Build) buildDone = true;

        if (moveDone && buildDone && attemptedActionNode.isLeaf())

            done = true;

        return true;
    }

    public void setEndTurn() {
        this.done = false;
        this.moveDone = false;
        this.buildDone = false;
        actualWorker = null;
    }

    //It set actual worker
    public boolean selectWorker(Coordinate coord) {
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getPosition().equals(coord)) {
                actualWorker = workers.get(i);
            }
        }
        return (actualWorker != null);
    }

    public ActionMessage getNextActions() {
        ArrayList<Action> actions = new ArrayList<>();
        boolean isOption = false;
        if (!tree.isLeaf() && buildDone && moveDone)
            isOption = true;
        for (TreeActionNode t : tree.getChildren())
            actions.add(t.getData());

        return new ActionMessage(actions, isOption, this.id);
    }

    public boolean checkLoser() {
        if (this.tree.isLeaf())
            this.hasLost = true;
        return this.hasLost;
    }
}
