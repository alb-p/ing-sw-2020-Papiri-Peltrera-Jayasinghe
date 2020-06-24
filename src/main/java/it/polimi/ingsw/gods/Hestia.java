package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 * The type Hestia.
 */
public class Hestia extends BasicGodCard {

    //Your Build: Your Worker may
    //build one additional time, but this
    //cannot be on a perimeter space

    /**
     * Card tree setup tree action node.
     *
     * @param w     the w
     * @param board the board
     * @return the tree action node
     */
    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {

        TreeActionNode root = super.cardTreeSetup(w, board);;
        for(TreeActionNode move : root.getChildren()){
            for(TreeActionNode firstBuild : move.getChildren()){
                for(Coordinate c : firstBuild.getData().getStart().getAdjacentCoords()){
                    if(!board.isPerimeter(c)){
                        if(!c.equals(firstBuild.getData().getEnd()) ||
                                (board.infoSlot(firstBuild.getData().getEnd()).getConstructionLevel() <= 2)){
                            if(board.infoSlot(c).isFree() || c.equals(move.getData().getStart())){
                                firstBuild.addChild(new TreeActionNode(new Build(firstBuild.getData().getStart(), c)));
                            }
                        }

                    }
                }
            }
        }
        return root;
    }
}
