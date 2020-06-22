package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.BasicGodCard;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.TreeActionNode;
import it.polimi.ingsw.model.Worker;

public class Hypnus extends BasicGodCard {

    //Start of Opponent’s Turn: If one
    //of your opponent’s Workers is
    //higher than all of their others, it
    //cannot move

    @Override
    public void specialRule(TreeActionNode root, IslandBoard board) {
        Worker w1 = null;
        Worker w2 = null;
        Worker higher = null;
        for (TreeActionNode node : root.getChildren()) {
            if (w1 == null) w1 = board.infoSlot(node.getData().getStart()).getWorker();
            else if (w2 == null && board.infoSlot(node.getData().getStart()).getWorker() != w1) {
                w2 = board.infoSlot(node.getData().getStart()).getWorker();
            }
        }
        if (w1 != null && w2 != null) {
            if (board.infoSlot(w1.getPosition()).getConstructionLevel() >
                    board.infoSlot(w2.getPosition()).getConstructionLevel()) {
                higher = w1;
            } else if (board.infoSlot(w2.getPosition()).getConstructionLevel() >
                    board.infoSlot(w1.getPosition()).getConstructionLevel()) {
                higher = w2;
            }
        }
        if (higher != null) {
            for (int i = 0; i < root.getChildren().size(); i++) {
                if (root.getChild(i).getData().getStart().equals(higher.getPosition())) {
                    root.getChildren().remove(root.getChild(i));
                    i--;
                }
            }
        }
    }
}
