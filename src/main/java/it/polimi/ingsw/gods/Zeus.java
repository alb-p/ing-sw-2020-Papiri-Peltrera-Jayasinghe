package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 * Your Build: Your Worker may
 *     build a block under itself.
 */
public class Zeus extends BasicGodCard {

    boolean specialBuild = false;

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
        for (TreeActionNode move : root.getChildren()) {
            if (board.infoSlot(move.getData().getEnd()).getConstructionLevel() < 3) {
                move.addChild(new TreeActionNode(new Build(move.getData().getEnd(), move.getData().getEnd())));
            }
        }
        return root;
    }

    /**
     * Override of the normal build.
     * with this method the worker is
     * able to build above his feet
     *
     * @param worker     the worker
     * @param coord the destination coord
     * @param board the board
     * @return the boolean
     */
    @Override
    public boolean build(Worker worker, Coordinate coord, IslandBoard board){
        if (!super.build(worker, coord, board)) {
            if (worker.getPosition().equals(coord) && board.infoSlot(coord).getConstructionLevel() < 3) {
                board.infoSlot(coord).free();
                board.infoSlot(coord).construct(Construction.FLOOR);
                board.infoSlot(coord).occupy(worker);
                specialBuild = true;
                return true;
            }
            return false;
        } else return true;
    }


    /**
     * Player does not win if
     * he moves up on the third level if
     * he builted upon his feet
     *
     * @param worker            the worker
     * @param board        the board
     * @param virtualBoard the virtual board
     * @return the boolean
     */
    @Override
    public boolean winningCondition(Worker worker, IslandBoard board, VirtualBoard virtualBoard) {
        boolean won = super.winningCondition(worker, board, virtualBoard) && !specialBuild;
        specialBuild = false;
        return won;
    }
}
