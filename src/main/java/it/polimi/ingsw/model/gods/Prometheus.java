package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.actions.FirstBuild;
import it.polimi.ingsw.actions.Move;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Coordinate;

/**
 * Your Turn: If your Worker does
 *      not move up, it may build both
 *      before and after
 */

public class Prometheus extends BasicGodCard {


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
        TreeActionNode moveNode;
        moveNode = null;
        TreeActionNode root = super.cardTreeSetup(worker, board);
        for (Coordinate c1 : worker.getPosition().getAdjacentCoords()) {     //first build
            if (board.infoSlot(c1).isFree()) {
                TreeActionNode firstBuildNode = new TreeActionNode(new FirstBuild(worker.getPosition(), c1));
                for (Coordinate c2 : worker.getPosition().getAdjacentCoords()) {     //move
                    moveNode = null; //initialization of the move node
                    if (board.infoSlot(c2).isFree()) {
                        if (c2.equals(c1)) {
                            if (board.infoSlot(worker.getPosition()).getConstructionLevel() >=
                                    (board.infoSlot(c1).getConstructionLevel() + 1)) { // increment of the constructionlevel bc firstbuilt there
                                moveNode = new TreeActionNode(new Move(worker.getPosition(), c2));
                            }
                        } else {
                            if (board.infoSlot(worker.getPosition()).getConstructionLevel() >=
                                    board.infoSlot(c2).getConstructionLevel()) {
                                moveNode = new TreeActionNode(new Move(worker.getPosition(), c2));
                            }
                        }
                        if (moveNode != null) {
                            for (Coordinate c3 : c2.getAdjacentCoords()) {
                                if (board.infoSlot(c3).isFree() && !c3.equals(worker.getPosition()) && !c3.equals(c1)) { // normal care and i remove the tricky ones that may not be free
                                    TreeActionNode secondBuildNode = new TreeActionNode(new Build(c2, c3));
                                    moveNode.addChild(secondBuildNode);
                                } else if (c3.equals(worker.getPosition())) { //for sure i can build in my originary position bc can't have more than 3 floors
                                    TreeActionNode secondBuildNode = new TreeActionNode(new Build(c2, c3));
                                    moveNode.addChild(secondBuildNode);
                                } else if (c3.equals(c1)) { // i need to verify that originally my first build was not a dome construction
                                    if (board.infoSlot(c3).getConstructionLevel() < 3) {
                                        TreeActionNode secondBuildNode = new TreeActionNode(new Build(c2, c3));
                                        moveNode.addChild(secondBuildNode);
                                    }
                                }
                            }
                            if (!moveNode.getChildren().isEmpty()) { // i can do this firstbuild if i have at least both a move not up and another build available
                                //if i am here i know that i might do atleast a move after the firstbuild
                                firstBuildNode.addChild(moveNode);
                            }
                        }
                    }
                }
                if (!firstBuildNode.getChildren().isEmpty() &&
                        !firstBuildNode.getChildren().get(0).getChildren().isEmpty()) { // i can add to the root the firstbuild node if i can finish a turn with a move and a build
                    root.addChild(firstBuildNode);
                }
            }
        }
        return root;

    }

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
        if (action instanceof Move) {
            if (this.move(player.getActualWorker(), action.getEnd(), board)) {
                player.setNotBuildDone();
                return true;
            }
        } else if (action.getActionName().equalsIgnoreCase("Build") ||
                action.getActionName().equalsIgnoreCase("First build")) {
            return (this.build(player.getActualWorker(), action.getEnd(), board));
        }
        return false;
    }

}