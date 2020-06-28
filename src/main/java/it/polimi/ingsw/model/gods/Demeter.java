package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Coordinate;

/**
 * Your Worker may
 *  build one additional time, but not
 *  on the same space
 */
public class Demeter extends BasicGodCard {

    /**
     * Create the tree of a worker based
     * on the godess' special power
     *
     * @param worker     the worker that will be able
     *              to perform the actions in the tree
     * @param board the board of the game
     * @return the root of the tree
     */
    @Override
    public TreeActionNode cardTreeSetup(Worker worker, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(worker, board);
        for (TreeActionNode first : root.getChildren()) {
            for (TreeActionNode second : first.getChildren()) {
                for (Coordinate c : second.getData().getStart().getAdjacentCoords()) {
                    if ((board.infoSlot(c).isFree() || c.equals(first.getData().getStart()))
                            && (!second.getData().getEnd().equals(c))) {
                        TreeActionNode buildNode = new TreeActionNode(new Build(second.getData().getStart(), c));
                        second.addChild(buildNode);
                    }
                }
            }
        }
        return root;
    }
}
