package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.BasicGodCard;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.TreeActionNode;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.utils.ANSIColor;

public class Hypnus extends BasicGodCard {

    //Start of Opponent’s Turn: If one
    //of your opponent’s Workers is
    //higher than all of their others, it
    //cannot move

    @Override
    public void specialRule(TreeActionNode root, IslandBoard board) {
        System.out.println("SPECIAL:: ");
        Worker w1 = null;
        Worker w2 = null;
        Worker higher = null;
        for (TreeActionNode node : root.getChildren()) {
            if (w1 == null) w1 = board.infoSlot(node.getData().getStart()).getWorker();
            else if (w2 == null && board.infoSlot(node.getData().getStart()).getWorker() != w1) {
                w2 = board.infoSlot(node.getData().getStart()).getWorker();
            }
        }

        if (board.infoSlot(w1.getPosition()).getConstructionLevel() >
                board.infoSlot(w2.getPosition()).getConstructionLevel()){
            System.out.println("w1>w2");
            higher = w1;
        }
        else if (board.infoSlot(w2.getPosition()).getConstructionLevel() >
                board.infoSlot(w1.getPosition()).getConstructionLevel()) {
            System.out.println("w2>w1");
            higher = w2;
        }
        if (higher != null) {
            System.out.println("higher!=null");
            //cancello tutti i primi nodi da root che partono con w1
            /*for (TreeActionNode nodeE : root.getChildren()) {
                if (nodeE.getData().getStart().equals(higher.getPosition())) {
                    System.out.println("Rimuovo "+ nodeE.getData().getStart());
                    root.getChildren().remove(nodeE);
                    System.out.println("Rimosso");
                }
            }*/
            for(int i= 0; i<root.getChildren().size(); i++){
                if (root.getChild(i).getData().getStart().equals(higher.getPosition())) {
                    System.out.println("Rimuovo "+ root.getChild(i).getData().getStart());
                    root.getChildren().remove(root.getChild(i));
                    i--;
                    System.out.println("Rimosso");
                }
            }
        }
        System.out.println(ANSIColor.BLUE + "finisco SPECIAL"+ANSIColor.RESET);
    }
}
