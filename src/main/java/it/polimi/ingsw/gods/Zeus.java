package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 * The type Zeus.
 */
public class Zeus extends BasicGodCard {
    //Your Build: Your Worker may
    //build a block under itself.

    boolean specialBuild = false;

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
        for (TreeActionNode move : root.getChildren()) {
            if (board.infoSlot(move.getData().getEnd()).getConstructionLevel() < 3) {
                move.addChild(new TreeActionNode(new Build(move.getData().getEnd(), move.getData().getEnd())));
            }
        }
        return root;
    }

    /**
     * Build boolean.
     *
     * @param w     the w
     * @param coord the coord
     * @param board the board
     * @return the boolean
     * @throws Exception the exception
     */
    @Override
    public boolean build(Worker w, Coordinate coord, IslandBoard board) throws Exception {
        if (!super.build(w, coord, board)) {
            if (w.getPosition().equals(coord) && board.infoSlot(coord).getConstructionLevel() < 3) {
                board.infoSlot(coord).free();
                board.infoSlot(coord).construct(Construction.FLOOR);
                board.infoSlot(coord).occupy(w);
                specialBuild = true;
                return true;
            }
            return false;
        } else return true;
    }


    /**
     * Winning condition boolean.
     *
     * @param w            the w
     * @param board        the board
     * @param virtualBoard the virtual board
     * @return the boolean
     */
    @Override
    public boolean winningCondition(Worker w, IslandBoard board, VirtualBoard virtualBoard) {
        boolean won = super.winningCondition(w, board, virtualBoard) && !specialBuild;
        specialBuild = false;
        return won;
    }
}
