package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

public class Pan extends BasicGodCard {


    /**Win Condition: You also win if
     your Worker moves down two or
     more levels.**/

    @Override
    public boolean winningCondition(Worker w, IslandBoard board, VirtualBoard virtualBoard) {
        if (virtualBoard.getSlot(new Coordinate(w.getOldPosition().getRow(),w.getOldPosition().getCol())).getLevel() == 2 &&
                board.infoSlot(w.getPosition()).getConstructionLevel() == 0) {
            return true;
        }
        return super.winningCondition(w, board, virtualBoard);
    }
}
