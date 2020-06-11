package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

public class Zeus extends BasicGodCard {
    //Your Build: Your Worker may
    //build a block under itself.

    boolean specialBuild = false;

    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(w, board);
        for(TreeActionNode move : root.getChildren()){
            if(board.infoSlot(move.getData().getEnd()).getConstructionLevel() < 3){
                move.addChild(new TreeActionNode(new Build(move.getData().getEnd(), move.getData().getEnd())));
            }
        }
        return root;
    }

    @Override
    public boolean build(Worker w, Coordinate coord, IslandBoard board) throws Exception {
        if(!super.build(w, coord, board)){
            if(w.getPosition().equals(coord)){
                board.infoSlot(coord).free();
                board.infoSlot(coord).construct(Construction.FLOOR);
                board.infoSlot(coord).occupy(w);
                specialBuild = true;
                return true;
            }
            return false;
        }else return true;
    }

    @Override
    public boolean winningCondition(Worker w, IslandBoard board, VirtualBoard virtualBoard) {
        boolean won = super.winningCondition(w, board, virtualBoard) && !specialBuild;
        specialBuild = false;
        return won;
    }
}
