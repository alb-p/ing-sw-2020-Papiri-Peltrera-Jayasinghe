package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 * Win Condition: You also win if
 *      your Worker moves down two or
 *      more levels.
 */
public class Pan extends BasicGodCard {


    /**
     * Controls if the player
     * has won after the actions of
     * his turn.
     *
     * @param worker       the worker
     * @param board        the board
     * @param virtualBoard the old board
     * @return the boolean
     */
    @Override
    public boolean winningCondition(Worker worker, IslandBoard board, VirtualBoard virtualBoard) {
        if (virtualBoard.getSlot(new Coordinate(worker.getOldPosition().getRow(),worker.getOldPosition().getCol())).getLevel() == 2 &&
                board.infoSlot(worker.getPosition()).getConstructionLevel() == 0) {
            return true;
        }
        return super.winningCondition(worker, board, virtualBoard);
    }
}
