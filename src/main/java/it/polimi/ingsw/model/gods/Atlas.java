package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.actions.BuildDome;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;

/**
 *Your Build:
 *Your Worker may build
 *a dome at any level.
 *
 */
public class Atlas extends BasicGodCard {

    /**
     * Handle the action chosen by the player
     * and calls the respective methods
     *
     * @param player the player
     * @param board  the board
     * @param action the action wanted to be performed
     * @return the outcome of the action wanted to be performed
     */
    @Override
    public boolean turnHandler(Player player, IslandBoard board, Action action){

        if (action.getActionName().equals("Build a dome")) {

            return buildDome(board, action);
        }else return super.turnHandler(player, board, action);

    }

    /**
     * Perform the built of a dome,
     * power of the god.
     *
     * @param board  the board
     * @param action the action
     * @return the outcome of the build
     */
    private boolean buildDome(IslandBoard board, Action action){
        if(board.infoSlot(action.getEnd()).isFree()){
            board.infoSlot(action.getEnd()).construct(Construction.DOME);
            return true;
        }
        return false;
    }


    /**
     * Create the tree of a worker based
     * on the god's special power
     *
     * @param worker     the worker that will be able
     *              to perform the actions in the tree
     * @param board the board of the game
     * @return the root of the tree
     */
    @Override
    public TreeActionNode cardTreeSetup(Worker worker, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(worker, board);
        ArrayList<TreeActionNode> toAdd = new ArrayList<>();
        for (TreeActionNode m : root.getChildren()) {
            for (TreeActionNode b : m.getChildren()) {
                if (board.infoSlot(b.getData().getEnd()).getConstructionLevel() <= 3) {
                    toAdd.add(new TreeActionNode(new BuildDome(b.getData().getStart(), b.getData().getEnd())));
                }
            }
            m.getChildren().addAll(toAdd);
            toAdd.clear();
        }
        return root;
    }


}
