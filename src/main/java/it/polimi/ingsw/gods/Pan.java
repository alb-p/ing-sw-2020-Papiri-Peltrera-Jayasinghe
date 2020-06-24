package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 * The type Pan.
 */
public class Pan extends BasicGodCard {


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
        if (virtualBoard.getSlot(new Coordinate(w.getOldPosition().getRow(),w.getOldPosition().getCol())).getLevel() == 2 &&
                board.infoSlot(w.getPosition()).getConstructionLevel() == 0) {
            return true;
        }
        return super.winningCondition(w, board, virtualBoard);
    }
}
