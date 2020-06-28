package it.polimi.ingsw.model;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.actions.Move;
import it.polimi.ingsw.model.gods.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.messages.ActionMessage;

import java.util.ArrayList;

/**
 * The type Player represent one
 * of the contestants of the game
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
     * Sets the god card.
     *
     * @param card the card
     */
    public void setCard(String card) {

        switch (card) {
            case "APOLLO":
                this.card = new Apollo();
                break;
            case "ARTEMIS":
                this.card = new Artemis();
                break;
            case "ATHENA":
                this.card = new Athena();
                break;
            case "ATLAS":
                this.card = new Atlas();
                break;
            case "CHRONUS":
                this.card = new Chronus();
                break;
            case "DEMETER":
                this.card = new Demeter();
                break;
            case "HEPHAESTUS":
                this.card = new Hephaestus();
                break;
            case "HESTIA":
                this.card = new Hestia();
                break;
            case "HYPNUS":
                this.card = new Hypnus();
                break;
            case "MINOTAUR":
                this.card = new Minotaur();
                break;
            case "PAN":
                this.card = new Pan();
                break;
            case "POSEIDON":
                this.card = new Poseidon();
                break;
            case "PROMETHEUS":
                this.card = new Prometheus();
                break;
            case "ZEUS":
                this.card = new Zeus();
                break;
            default: this.card = new BasicGodCard();
        }
    }

    /**
     * Gets god card.
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
     * @return the worker of index i
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
     * Has ended his turn.
     *
     * @return the boolean
     */
    public boolean hasDone() {
        return this.done;
    }

    /**
     * Reset build done.
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
     * create the tree for his possible actions.
     * @param board the board
     */
    public void playerTreeSetup(IslandBoard board) {
        TreeActionNode treeActionNode = this.card.cardTreeSetup(this.getWorker(0), board);
        TreeActionNode temp = this.card.cardTreeSetup(this.getWorker(1), board);
        for (TreeActionNode t : temp.getChildren())
            treeActionNode.addChild(t);
        this.tree = treeActionNode;
    }

    /**
     * Tries to do the move requested from the
     * player.
     *
     * @param board   the board
     * @param message the message
     * @return the boolean
     */
    public boolean turnHandler(IslandBoard board, Action message){
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
     * Select actual worker boolean.
     *
     * @param coord the coord
     * @return the boolean
     */
    public boolean selectWorker(Coordinate coord) {
        for (Worker worker : workers) {
            if (worker.getPosition().equals(coord)) {
                actualWorker = worker;
            }
        }
        return (actualWorker != null);
    }

    /**
     * Gets next actions available
     * for the player.
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
     * The player has done at least a move and a build.
     *
     * @return the boolean
     */
    public boolean essentialDone() {
        return moveDone && buildDone;
    }
}
