package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

public class Hephaestus extends BasicGodCard {


    //Your Build:
    //Your Worker may build
    //one additional block
    //(not dome) on top of
    //your first block.


    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode root =  super.cardTreeSetup(w, board);

        for (TreeActionNode m : root.getChildren()){
            for (TreeActionNode b : m.getChildren()){
                if (board.infoSlot(b.getData().getEnd()).getConstructionLevel()<2){
                    b.addChild(new TreeActionNode(new Build(b.getData().getStart(),b.getData().getEnd())));
                }
            }
        }

        return root;
    }
}
