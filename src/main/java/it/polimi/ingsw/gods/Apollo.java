package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

import java.util.HashMap;
import java.util.Set;

public class Apollo extends BasicGodCard {

    //Your Move: Your Worker may
    //move into an opponent Worker’s
    //space by forcing their Worker to
    //the space yours just vacated

    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(w, board);
        for (Coordinate c1 : (w.getPosition()).getAdjacentCoords()) {
            if (board.infoSlot(c1).workerOnSlot() && board.infoSlot(c1).getWorker().getColor() != w.getColor() &&
                    (board.infoSlot(w.getPosition()).getConstructionLevel() - board.infoSlot(c1).getConstructionLevel() >= -1)) {
                TreeActionNode moveNode = new TreeActionNode(new Move(w.getPosition(), c1));
                for (Coordinate c2 : c1.getAdjacentCoords()) {
                    if (board.infoSlot(c2).isFree()) {
                        TreeActionNode buildNode = new TreeActionNode(new Build(c1, c2));
                        moveNode.addChild(buildNode);
                    }
                }
                root.addChild(moveNode);
            }
        }
        return root;
    }

    @Override
    public boolean turnHandler(Player player, IslandBoard board, Action action) throws Exception {
        if (action.getActionName().equalsIgnoreCase("move")) {
            if(modMove(player.getActualWorker(), action.getStart(), action.getEnd(), board) ){
                return true;
            }
            return this.move(player.getActualWorker(), action.getEnd(), board);
        } else if (action.getActionName().equalsIgnoreCase("build")) {
            return (this.build(player.getActualWorker(), action.getEnd(), board));
        }
        return false;
    }

    private boolean modMove(Worker w, Coordinate start, Coordinate end, IslandBoard board){
        Worker oppWorker;

        if(board.infoSlot(end).workerOnSlot() && board.infoSlot(end).getWorker().getColor() != w.getColor() &&
                (board.infoSlot(w.getPosition()).getConstructionLevel() - board.infoSlot(end).getConstructionLevel() >= -1)){
            oppWorker = board.infoSlot(end).getWorker();
            System.out.println("OPPWORKER:: "+board.infoSlot(end).getWorker());
            oppWorker.setPosition(start);
            board.infoSlot(start).free();
            board.infoSlot(start).occupy(oppWorker);
            System.out.println("OPP POS:: "+oppWorker.getPosition()+" " +oppWorker );
            w.setPosition(end);
            board.infoSlot(end).free();
            board.infoSlot(end).occupy(w);
            System.out.println("ACTWOR POS:: "+w.getPosition()+" " +w );
            return true;
        }
        return false;
    }
}

