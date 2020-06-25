package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 *  Your Move: Your Worker may
 *     move into an opponent Worker’s
 *     space by forcing their Worker to
 *     the space yours just vacated
 */
public class Apollo extends BasicGodCard {

    /**
     * Create the tree of a worker based
     * on the god's special power
     *
     * @param worker     the worker that will be able
     *              to perform the actions in the tree
     * @param board the board of the game
     * @return the root of the tree
     */
    @Override
    public TreeActionNode cardTreeSetup(Worker worker, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(worker, board);
        for (Coordinate c1 : (worker.getPosition()).getAdjacentCoords()) {
            if (board.infoSlot(c1).workerOnSlot() && board.infoSlot(c1).getWorker().getColor() != worker.getColor() &&
                    (board.infoSlot(worker.getPosition()).getConstructionLevel() - board.infoSlot(c1).getConstructionLevel() >= -1)) {
                TreeActionNode moveNode = new TreeActionNode(new Move(worker.getPosition(), c1));
                for (Coordinate c2 : c1.getAdjacentCoords()) {
                    if (board.infoSlot(c2).isFree()) {
                        TreeActionNode buildNode = new TreeActionNode(new Build(c1, c2));
                        moveNode.addChild(buildNode);
                    }
                }
                root.addChild(moveNode);
            }
        }
        return root;
    }

    /**
     * Handle the action chosen by the player
     * and calls the respective methods
     *
     * @param player the player
     * @param board  the board
     * @param action the action wanted to be performed
     * @return the outcome of the action wanted to be performed
     */
    @Override
    public boolean turnHandler(Player player, IslandBoard board, Action action){
        if (action.getActionName().equalsIgnoreCase("move")) {
            if(modMove(player.getActualWorker(), action.getStart(), action.getEnd(), board) ){
                return true;
            }
            return this.move(player.getActualWorker(), action.getEnd(), board);
        } else if (action.getActionName().equalsIgnoreCase("build")) {
            return (this.build(player.getActualWorker(), action.getEnd(), board));
        }
        return false;
    }

    /**
     * The implementation of the
     * special move, power of Apollo.
     *
     * @param worker     the worker
     * @param start the start of the move
     * @param end   the end of the move
     * @param board the board
     * @return the outcome of the move
     */
    private boolean modMove(Worker worker, Coordinate start, Coordinate end, IslandBoard board){
        Worker oppWorker;

        if(board.infoSlot(end).workerOnSlot() && board.infoSlot(end).getWorker().getColor() != worker.getColor() &&
                (board.infoSlot(worker.getPosition()).getConstructionLevel() - board.infoSlot(end).getConstructionLevel() >= -1)){
            oppWorker = board.infoSlot(end).getWorker();
            oppWorker.setPosition(start);
            board.infoSlot(start).free();
            board.infoSlot(start).occupy(oppWorker);
            worker.setPosition(end);
            board.infoSlot(end).free();
            board.infoSlot(end).occupy(worker);
            return true;
        }
        return false;
    }
}

