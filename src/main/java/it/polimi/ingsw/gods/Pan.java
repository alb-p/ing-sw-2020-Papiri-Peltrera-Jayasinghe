package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.BasicGodCard;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.VirtualBoard;
import it.polimi.ingsw.model.Worker;

public class Pan extends BasicGodCard {

    //VINCO SE SCENDO DI DUE LIVELLI

    @Override
    public boolean winningCondition(Worker w, IslandBoard board, VirtualBoard virtualBoard) {
        if (virtualBoard.getSlot(w.getOldPosition().getRow(),w.getOldPosition().getCol()).getHeight() == 2 &&
                board.infoSlot(w.getPosition()).getConstructionLevel() == 0) {
            return true;
        }
        return super.winningCondition(w, board, virtualBoard);
    }
}
