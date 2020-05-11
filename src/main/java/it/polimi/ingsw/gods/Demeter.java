package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

import java.util.Scanner;

public class Demeter extends BasicGodCard {

    //Your Worker may
    //build one additional time, but not
    //on the same space.

    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(w, board);
        for (TreeActionNode first : root.getChildren()) {
            for (TreeActionNode second : first.getChildren()) {
                for (Coordinate c : second.getData().getStart().getAdjacentCoords()) {
                    if ((board.infoSlot(c).isFree() || c.equals(first.getData().getStart()))
                            && (!second.getData().getEnd().equals(c))) {                                          //stesso controllo che si fa anche nel build del basicGod
                        TreeActionNode buildNode = new TreeActionNode(new Build(second.getData().getStart(), c));
                        second.addChild(buildNode);
                    }
                }
            }
        }
        return root;
    }
}
