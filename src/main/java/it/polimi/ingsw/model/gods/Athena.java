package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.actions.Move;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Coordinate;

/**
 * Opponent’s Turn: If one of your
 * Workers moved up on your last
 * turn, opponent Workers cannot
 * move up this turn.
 */
public class Athena extends BasicGodCard {

    private boolean specialRuleActivated = false;


    /**
     * This method is called to verify
     * if there are moves that the goddess
     * can forbid based on her power.
     * It calls elaboration method only
     * if the previous turn the player
     * moved up.
     *
     * @param tree  the tree
     * @param board the board
     */
    @Override
    public void specialRule(TreeActionNode tree, IslandBoard board) {
        if (specialRuleActivated) {
            elaboration(tree, board, 0);
        }
    }

    /**
     * Actual removal of the
     * forbidden moves.
     *
     * @param node  the node
     * @param board the board
     * @param index the index
     */
    private void elaboration(TreeActionNode node, IslandBoard board, int index) {
        if (!node.isLeaf()) {
            for (int i = 0; i < node.getChildren().size(); i++) {
                if (node.getChild(i).getData() instanceof Move) {
                    int start = board.infoSlot(node.getChild(i).getData().getStart()).getConstructionLevel();
                    int end = board.infoSlot(node.getChild(i).getData().getEnd()).getConstructionLevel();

                    if (end - start > 0) {
                        node.removeChild(i);
                        i--;
                    } else elaboration(node.getChild(i), board, i);
                } else elaboration(node.getChild(i), board, i);


            }
        }
        /* IF A GOD THAT ALLOW A MOVE AFTER BUILD IS IMPLEMENTED add this code ↓

        else {
            if (node.getData() instanceof Move) {
                int start = board.infoSlot(node.getData().getStart()).getConstructionLevel();
                int end = board.infoSlot(node.getData().getEnd()).getConstructionLevel();

                if (end - start > 0) node.getParent().removeChild(index);
            }

        }*/

    }

    /**
     * Standard move but needs to
     * save if during the player moves up.
     *
     * @param worker     the worker
     * @param coord the destination coordinate
     * @param board the board
     * @return the outcome of the move
     */
    @Override
    public boolean move(Worker worker, Coordinate coord, IslandBoard board) {
        specialRuleActivated = false;
        int workerSlotLevel = board.infoSlot(worker.getPosition()).getConstructionLevel();
        int destSlotLevel = board.infoSlot(coord).getConstructionLevel();
        if (super.move(worker, coord, board)) {
            if (destSlotLevel - workerSlotLevel > 0) {
                specialRuleActivated = true;
            }
            return true;
        }
        return false;
    }
}
