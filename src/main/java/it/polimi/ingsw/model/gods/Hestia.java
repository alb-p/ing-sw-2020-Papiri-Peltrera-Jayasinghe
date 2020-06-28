package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Coordinate;

/**
 * Your Build: Your Worker may
 * build one additional time, but this
 * cannot be on a perimeter space.
 */
public class Hestia extends BasicGodCard {


    /**
     * Create the tree of a worker based
     * on the godess' special power
     *
     * @param worker the worker that will be able
     *               to perform the actions in the tree
     * @param board  the board of the game
     * @return the root of the tree
     */
    @Override
    public TreeActionNode cardTreeSetup(Worker worker, IslandBoard board) {

        TreeActionNode root = super.cardTreeSetup(worker, board);
        ;
        for (TreeActionNode move : root.getChildren()) {
            for (TreeActionNode firstBuild : move.getChildren()) {
                for (Coordinate c : firstBuild.getData().getStart().getAdjacentCoords()) {
                    if (!isPerimeter(c)) {
                        if (!c.equals(firstBuild.getData().getEnd()) ||
                                (board.infoSlot(firstBuild.getData().getEnd()).getConstructionLevel() <= 2)) {
                            if (board.infoSlot(c).isFree() || c.equals(move.getData().getStart())) {
                                firstBuild.addChild(new TreeActionNode(new Build(firstBuild.getData().getStart(), c)));
                            }
                        }

                    }
                }
            }
        }
        return root;
    }

    /**
     * Is perimeter boolean.
     *
     * @param coordinate the coordinate
     * @return true if coordinate is in the perimeter
     */
    private boolean isPerimeter(Coordinate coordinate) {
        int row = coordinate.getRow();
        int col = coordinate.getCol();
        return row == 0 || row == 4 || col == 0 || col == 4;
    }

}
