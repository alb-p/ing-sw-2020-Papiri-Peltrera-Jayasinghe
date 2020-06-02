package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

public class Chronus extends BasicGodCard {

    //Win Condition: You also win
    //when there are at least five
    //Complete Towers on the board


    @Override
    public boolean winningCondition(Worker w, IslandBoard board, VirtualBoard virtualBoard) {
        return (virtualBoard.getSlot(w.getOldPosition().getRow(), w.getOldPosition().getCol()).getLevel() == 2 &&
                board.infoSlot(w.getPosition()).getConstructionLevel() == 3) //standard winning condition
                && fiveTower(board); //Chronus special winning condition
    }

    private boolean fiveTower(IslandBoard board) {
        int towersNumber = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getSlot(i, j).getConstructionLevel() == 3) {
                    towersNumber++;
                }
            }
            if (towersNumber >= 5) return true;
        }
        return false;
    }
}
