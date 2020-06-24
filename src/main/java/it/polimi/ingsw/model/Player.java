package it.polimi.ingsw.model;

import it.polimi.ingsw.gods.*;
import it.polimi.ingsw.utils.messages.ActionMessage;

import java.util.ArrayList;

/**
 * The type Player.
 */
public class Player {


    private final ArrayList<Worker> workers = new ArrayList<>();
    private TreeActionNode tree;

    private final String nickName;
    private BasicGodCard card;
    private boolean done;
    private boolean moveDone;
    private boolean buildDone;
    private boolean hasLost = false;


    private int id;
    private Worker actualWorker;


    /**
     * Instantiates a new Player.
     *
     * @param nickName the nick name
     * @param color    the color
     */
    public Player(String nickName, String color) {
        this.nickName = nickName;
        this.actualWorker = null;
        this.done = false;
        this.moveDone = false;
        this.buildDone = false;
        workers.add(new Worker(0, 0, color));
        workers.add(new Worker(0, 0, color));
    }

    /**
     * Instantiates a new Player.
     *
     * @param id       the id
     * @param nickName the nick name
     * @param color    the color
     */
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

    /**
     * Instantiates a new Player.
     *
     * @param id       the id
     * @param nickName the nick name
     * @param color    the color
     */
    public Player(int id, String nickName, String color) {
        this.nickName = nickName;
        this.id = id;
        workers.add(new Worker(0, 0, color));
        workers.add(new Worker(0, 0, color));
    }

    /**
     * Sets card.
     *
     * @param card the card
     */
    public void setCard(String card) {

        if (card.equals("APOLLO")) {
            this.card = new Apollo();
        } else if (card.equals("ARTEMIS")) {
            this.card = new Artemis();
        } else if (card.equals("ATHENA")) {
            this.card = new Athena();
        } else if (card.equals("ATLAS")) {
            this.card = new Atlas();
        } else if (card.equals("CHRONUS")) {
            this.card = new Chronus();
        } else if (card.equals("DEMETER")) {
            this.card = new Demeter();
        } else if (card.equals("HEPHAESTUS")) {
            this.card = new Hephaestus();
        } else if (card.equals("HESTIA")) {
            this.card = new Hestia();
        } else if (card.equals("HYPNUS")) {
            this.card = new Hypnus();
        } else if (card.equals("MINOTAUR")) {
            this.card = new Minotaur();
        } else if (card.equals("PAN")) {
            this.card = new Pan();
        } else if(card.equals("POSEIDON")) {
            this.card = new Poseidon();
        } else if (card.equals("PROMETHEUS")) {
            this.card = new Prometheus();
        } else if (card.equals("ZEUS")) {
            this.card = new Zeus();
        }
    }

    /**
     * Gets card.
     *
     * @return the card
     */
    public BasicGodCard getCard() {
        return this.card;
    }

    /**
     * Gets nick name.
     *
     * @return the nick name
     */
    public String getNickName() {
        return this.nickName;
    }

    /**
     * Gets worker.
     *
     * @param i the
     * @return the worker
     */
    public Worker getWorker(int i) {
        return this.workers.get(i);
    }

    /**
     * Gets actual worker.
     *
     * @return the actual worker
     */
    public Worker getActualWorker() {
        return this.actualWorker;
    }

    /**
     * Has done boolean.
     *
     * @return the boolean
     */
    public boolean hasDone() {
        return this.done;
    }

    /**
     * Sets not build done.
     */
    public void setNotBuildDone() {
        buildDone = false;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets tree.
     *
     * @return the tree
     */
    public TreeActionNode getTree() {
        return this.tree;
    }

    /**
     * Player tree setup.
     *
     * @param board the board
     */
    public void playerTreeSetup(IslandBoard board) {
        TreeActionNode tree = this.card.cardTreeSetup(this.getWorker(0), board);
        TreeActionNode temp = this.card.cardTreeSetup(this.getWorker(1), board);
        for (TreeActionNode t : temp.getChildren())
            tree.addChild(t);
        this.tree = tree;
    }

    /**
     * Turn handler boolean.
     *
     * @param board   the board
     * @param message the message
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean turnHandler(IslandBoard board, Action message) throws Exception {
        TreeActionNode attemptedActionNode;
        attemptedActionNode = tree.search(message);
        if(attemptedActionNode == null) {
            return false;
        }
        this.actualWorker = board.infoSlot(message.getStart()).getWorker();
        this.tree = attemptedActionNode;
        if (!this.card.turnHandler(this, board, message)) {
            return false;//TODO mettere warning
        }


        if (message instanceof Move)
            moveDone = true;

        else if (message instanceof Build) buildDone = true;

        if (moveDone && buildDone && attemptedActionNode.isLeaf())

            done = true;

        return true;
    }

    /**
     * Sets end turn.
     */
    public void setEndTurn() {
        this.done = false;
        this.moveDone = false;
        this.buildDone = false;
        tree = null;
        actualWorker = null;
    }

    /**
     * Select worker boolean.
     *
     * @param coord the coord
     * @return the boolean
     */
//It set actual worker
    public boolean selectWorker(Coordinate coord) {
        for (Worker worker : workers) {
            if (worker.getPosition().equals(coord)) {
                actualWorker = worker;
            }
        }
        return (actualWorker != null);
    }

    /**
     * Gets next actions.
     *
     * @return the next actions
     */
    public ActionMessage getNextActions() {
        ArrayList<Action> actions = new ArrayList<>();
        boolean isOption = false;
        if (!tree.isLeaf() && buildDone && moveDone)
            isOption = true;
        for (TreeActionNode t : tree.getChildren())
            actions.add(t.getData());

        return new ActionMessage(actions, isOption, this.id);
    }

    /**
     * Check loser boolean.
     *
     * @return the boolean
     */
    public boolean checkLoser() {
        if (this.tree.isLeaf())
            this.hasLost = true;
        return this.hasLost;
    }

    /**
     * Essential done boolean.
     *
     * @return the boolean
     */
    public boolean essentialDone() {
        return moveDone && buildDone;
    }
}
