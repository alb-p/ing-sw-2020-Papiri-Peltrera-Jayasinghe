package it.polimi.ingsw.model;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.actions.Move;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.VirtualBoard;

/**
 * The type Basic god card uses the basic rules
 * of the game so any god can extend this class and
 * override only the method that involves the special power.
 */
public class BasicGodCard {

    /**
     * The worker is moved in the adjacent
     * coords chosen by the player
     *
     * @param worker     the worker
     * @param coord the destination coord
     * @param board the board
     * @return the outcome of the move
     */
    public boolean move(Worker worker, Coordinate coord, IslandBoard board) {
        Slot workerSlot = board.infoSlot(worker.getPosition());
        Slot destSlot = board.infoSlot(coord);
        if (worker.getPosition().isAdjacent(coord) &&
                (workerSlot.getConstructionLevel() - destSlot.getConstructionLevel() >= -1) && destSlot.isFree()) {
            workerSlot.free();
            destSlot.occupy(worker);
            worker.setPosition(coord);
            return true;
        }
        return false;
    }

    /**
     The worker is
     * able to build in one of the
     * adjacent slots of his actual position
     * if the slot is free
     *
     * @param w     the w
     * @param coord the coordinate where the
     *              worker wants to build
     * @param board the board
     * @return the outcome of the build
     */
    public boolean build(Worker w, Coordinate coord, IslandBoard board){
        if (w.getPosition().isAdjacent(coord) && board.infoSlot(coord).isFree()) {
            Slot slot = board.infoSlot(coord);
            if (board.infoSlot(coord).getConstructionLevel() < 3) {
                return slot.construct(Construction.FLOOR);
            } else {
                return slot.construct(Construction.DOME);
            }
        } else {
            return false;
        }
    }

    /**
     * If the god has the power of modify the tree
     * of an opponent player, this method will
     * be overridden.
     *
     * @param root  the root
     * @param board the board
     */
    public void specialRule(TreeActionNode root, IslandBoard board) {
        return;
    }



    /**
     * Handle the action chose from the player
     * and calls the respective methods
     *
     * @param player the player
     * @param board  the board
     * @param action the action wanted to be performed
     * @return the outcome of the action wanted to be performed
     */
    public boolean turnHandler(Player player, IslandBoard board, Action action){
        if (action instanceof Move) {
            return (this.move(player.getActualWorker(), action.getEnd(), board));
        } else if (action instanceof Build) {
            return (this.build(player.getActualWorker(), action.getEnd(), board));
        }
        return false;
    }


    /**
     * Create the basic tree of the available
     * actions of a worker
     *
     * @param worker     the worker that will be able
     *              to perform the actions in the tree
     * @param board the board of the game
     * @return the root of the tree
     */
    public TreeActionNode cardTreeSetup(Worker worker, IslandBoard board) {
        TreeActionNode tree = new TreeActionNode(null);
        if(worker == null) System.out.println("worker nulll");
        for (Coordinate c1 : worker.getPosition().getAdjacentCoords()) {  //check around worker position to perform a Move

            if (board.infoSlot(c1).isFree() &&
                    (board.infoSlot(worker.getPosition()).getConstructionLevel() - board.infoSlot(c1).getConstructionLevel() >= -1)) {

                TreeActionNode moveNode = new TreeActionNode(new Move(worker.getPosition(), c1));
                for (Coordinate c2 : c1.getAdjacentCoords()) {                       //check around every position to perform a Build
                    if (board.infoSlot(c2).isFree()) {
                        TreeActionNode buildNode = new TreeActionNode(new Build(c1, c2));
                        moveNode.addChild(buildNode);
                    }
                }
                //adding the initial worker position as a possible Build position
                TreeActionNode buildNode = new TreeActionNode(new Build(c1, worker.getPosition()));
                moveNode.addChild(buildNode);
                tree.addChild(moveNode);

            }
        }
        return tree;

    }

    /**
     * Winning condition check.
     *
     * @param w            the w
     * @param board        the board
     * @param virtualBoard the virtual board
     * @return true if the player wins
     */
    public boolean winningCondition(Worker w, IslandBoard board, VirtualBoard virtualBoard) {

        return  (virtualBoard.getSlot(new Coordinate(w.getOldPosition().getRow(), w.getOldPosition().getCol())).getLevel() == 2 &&
                board.infoSlot(w.getPosition()).getConstructionLevel() == 3);
    }
}

