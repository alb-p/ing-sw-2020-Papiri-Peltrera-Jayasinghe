package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Atlas extends BasicGodCard {

    @Override
    public boolean turnHandler(Player player, IslandBoard board, Action action) throws Exception {

        if (action.getActionName().equals("Build a dome")) {
            System.out.println("BUILDING DOME ::");

            return buildDome(player, board, action);
        }else return super.turnHandler(player, board, action);

    }

    private boolean buildDome(Player player, IslandBoard board, Action action) throws Exception {
        System.out.println("BUILDING DOME ::");
        board.infoSlot(action.getEnd()).construct(Construction.DOME);
        return (board.infoSlot(action.getEnd()).hasADome());
    }

    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(w, board);
        ArrayList<TreeActionNode> toAdd = new ArrayList<>();
        for (TreeActionNode m : root.getChildren()) {
            for (TreeActionNode b : m.getChildren()) {
                if (board.infoSlot(b.getData().getEnd()).getConstructionLevel() < 3) {
                    toAdd.add(new TreeActionNode(new BuildDome(b.getData().getStart(), b.getData().getEnd())));
                }
            }
            m.getChildren().addAll(toAdd);
            toAdd.clear();
        }
        return root;
    }


}
