package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.model.*;

/**
 * Your Build:
 *     Your Worker may build
 *     one additional block
 *     (not dome) on top of
 *     your first block.
 */
public class Hephaestus extends BasicGodCard {

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
        TreeActionNode root =  super.cardTreeSetup(worker, board);

        for (TreeActionNode m : root.getChildren()){
            for (TreeActionNode b : m.getChildren()){
                if (board.infoSlot(b.getData().getEnd()).getConstructionLevel()<2){
                    b.addChild(new TreeActionNode(new Build(b.getData().getStart(),b.getData().getEnd())));
                }
            }
        }

        return root;
    }
}
