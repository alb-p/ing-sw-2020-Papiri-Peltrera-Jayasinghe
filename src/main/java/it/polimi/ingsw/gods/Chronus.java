package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 * The type Chronus.
 */
public class Chronus extends BasicGodCard {

    //Win Condition: You also win
    //when there are at least five
    //Complete Towers on the board


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
        return (super.winningCondition(w,board,virtualBoard) || fiveTower(board));
    }

    /**
     * Five tower boolean.
     *
     * @param board the board
     * @return the boolean
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
