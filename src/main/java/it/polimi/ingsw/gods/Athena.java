package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 * The type Athena.
 */
public class Athena extends BasicGodCard {

    //Opponent’s Turn: If one of your
    //Workers moved up on your last
    //turn, opponent Workers cannot
    //move up this turn.

    private boolean specialRuleActivated = false;


    /**
     * Special rule.
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
     * Elaboration.
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
        } else {
            if (node.getData() instanceof Move) {
                int start = board.infoSlot(node.getData().getStart()).getConstructionLevel();
                int end = board.infoSlot(node.getData().getEnd()).getConstructionLevel();

                if (end - start > 0) node.getParent().removeChild(index);
            }

        }

    }

    /**
     * Move boolean.
     *
     * @param w     the w
     * @param coord the coord
     * @param board the board
     * @return the boolean
     */
    @Override
    public boolean move(Worker w, Coordinate coord, IslandBoard board){
        specialRuleActivated = false;
        int workerSlotLevel = board.infoSlot(w.getPosition()).getConstructionLevel();
        int destSlotLevel = board.infoSlot(coord).getConstructionLevel();
        if (super.move(w, coord, board)) {
            if (destSlotLevel - workerSlotLevel > 0) {
                specialRuleActivated = true;
            }
            return true;
        }
        return false;
    }
}
