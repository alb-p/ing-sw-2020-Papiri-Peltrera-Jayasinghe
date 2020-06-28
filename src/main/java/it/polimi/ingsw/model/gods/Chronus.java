package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.VirtualBoard;

/**
 * Win Condition: You also win
 *     when there are at least five
 *     Complete Towers on the board
 */
public class Chronus extends BasicGodCard {



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
        return (super.winningCondition(worker,board,virtualBoard) || fiveTower(board));
    }

    /**
     * Check if 5 tower are built
     * on the board.
     *
     * @param board the board
     * @return true if there are 5 complete
     *          towers in the board
     *
     */
    private boolean fiveTower(IslandBoard board) {
        int completeTowersNumber = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.infoSlot(new Coordinate(i, j)).getConstructionLevel() == 3 && board.infoSlot(new Coordinate(i, j)).hasADome()) {
                    completeTowersNumber++;
                }
            }
            if (completeTowersNumber >= 5) return true;
        }
        return false;
    }
}
