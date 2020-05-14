package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

import java.util.HashMap;
import java.util.Set;

public class Athena extends BasicGodCard {

    //SE SALGO DI LIVELLO GLI AVVERSARI NON POSSONO FARLO DURANTE QUESTO TURNO

    private boolean specialRuleActivated=false;


    @Override
    public void specialRule(HashMap<Worker, TreeActionNode> trees, Set<Worker> hashList, IslandBoard board) {
        if(specialRuleActivated){
            for(Worker w: hashList){
                TreeActionNode root=trees.get(w);
                elaboration(root,board,0);
            }

        }
    }

    private void elaboration(TreeActionNode node, IslandBoard board, int index) {

        if(!node.isLeaf()) {
            for (int i = 0; i < node.getChildren().size(); i++) {
                if (node.getChild(i).getData() instanceof Move) {
                    int start = board.infoSlot(node.getChild(i).getData().getStart()).getConstructionLevel();
                    int end = board.infoSlot(node.getChild(i).getData().getEnd()).getConstructionLevel();

                    if (end - start > 0) node.removeChild(i);
                    else elaboration(node.getChild(i), board,i);
                } else elaboration(node.getChild(i), board,i);


            }
        }else{
            if(node.getData() instanceof Move){
                int start = board.infoSlot(node.getData().getStart()).getConstructionLevel();
                int end = board.infoSlot(node.getData().getEnd()).getConstructionLevel();

                if (end - start > 0) node.getParent().removeChild(index);
            }

        }

    }

    @Override
    public boolean move(Worker w, Coordinate coord, IslandBoard board) throws Exception {
        specialRuleActivated=false;
        int workerSlotLevel = board.infoSlot(w.getPosition()).getConstructionLevel();
        int destSlotLevel = board.infoSlot(coord).getConstructionLevel();
        if(super.move(w, coord, board)){
           if(destSlotLevel-workerSlotLevel>0) {
               specialRuleActivated = true;
               System.out.println("Athena u akbar");
           }
            return true;
        }
        return false;
    }
}
