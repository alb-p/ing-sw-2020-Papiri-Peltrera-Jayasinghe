package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 * The type Apollo.
 */
public class Apollo extends BasicGodCard {

    //Your Move: Your Worker may
    //move into an opponent Worker’s
    //space by forcing their Worker to
    //the space yours just vacated

    /**
     * Card tree setup tree action node.
     *
     * @param w     the w
     * @param board the board
     * @return the tree action node
     */
    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(w, board);
        for (Coordinate c1 : (w.getPosition()).getAdjacentCoords()) {
            if (board.infoSlot(c1).workerOnSlot() && board.infoSlot(c1).getWorker().getColor() != w.getColor() &&
                    (board.infoSlot(w.getPosition()).getConstructionLevel() - board.infoSlot(c1).getConstructionLevel() >= -1)) {
                TreeActionNode moveNode = new TreeActionNode(new Move(w.getPosition(), c1));
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
     * Turn handler boolean.
     *
     * @param player the player
     * @param board  the board
     * @param action the action
     * @return the boolean
     * @throws Exception the exception
     */
    @Override
    public boolean turnHandler(Player player, IslandBoard board, Action action) throws Exception {
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
     * Mod move boolean.
     *
     * @param w     the w
     * @param start the start
     * @param end   the end
     * @param board the board
     * @return the boolean
     */
    private boolean modMove(Worker w, Coordinate start, Coordinate end, IslandBoard board){
        Worker oppWorker;

        if(board.infoSlot(end).workerOnSlot() && board.infoSlot(end).getWorker().getColor() != w.getColor() &&
                (board.infoSlot(w.getPosition()).getConstructionLevel() - board.infoSlot(end).getConstructionLevel() >= -1)){
            oppWorker = board.infoSlot(end).getWorker();
            oppWorker.setPosition(start);
            board.infoSlot(start).free();
            board.infoSlot(start).occupy(oppWorker);
            w.setPosition(end);
            board.infoSlot(end).free();
            board.infoSlot(end).occupy(w);
            return true;
        }
        return false;
    }
}

