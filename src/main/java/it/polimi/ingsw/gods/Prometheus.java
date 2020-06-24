package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 * Your Turn: If your Worker does
 * not move up, it may build both
 * before and after moving
 * MOVE 0,0 IN 0,1 & BUILD IN 1,1 ok
 * BUILD 0,1 IN 0,2 & MOVE IN 1,1 & BUILD IN 1,2
 */

public class Prometheus extends BasicGodCard {

    /**
     * Card tree setup tree action node.
     *
     * @param w     the w
     * @param board the board
     * @return the tree action node
     */
    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode moveNode;
        moveNode = null;
        TreeActionNode root = super.cardTreeSetup(w, board);
        for (Coordinate c1 : w.getPosition().getAdjacentCoords()) {     //first build
            if (board.infoSlot(c1).isFree()) {
                TreeActionNode firstBuildNode = new TreeActionNode(new FirstBuild(w.getPosition(), c1));
                for (Coordinate c2 : w.getPosition().getAdjacentCoords()) {     //move
                    moveNode = null; //initialization of the move node
                    if (board.infoSlot(c2).isFree()) {
                        if (c2.equals(c1)) {
                            if (board.infoSlot(w.getPosition()).getConstructionLevel() >=
                                    (board.infoSlot(c1).getConstructionLevel() + 1)) { // increment of the constructionlevel bc firstbuilt there
                                moveNode = new TreeActionNode(new Move(w.getPosition(), c2));
                            }
                        } else {
                            if (board.infoSlot(w.getPosition()).getConstructionLevel() >=
                                    board.infoSlot(c2).getConstructionLevel()) {
                                moveNode = new TreeActionNode(new Move(w.getPosition(), c2));
                            }
                        }
                        if (moveNode != null) {
                            for (Coordinate c3 : c2.getAdjacentCoords()) {
                                if (board.infoSlot(c3).isFree() && !c3.equals(w.getPosition()) && !c3.equals(c1)) { // normal care and i remove the tricky ones that may not be free
                                    TreeActionNode secondBuildNode = new TreeActionNode(new Build(c2, c3));
                                    moveNode.addChild(secondBuildNode);
                                } else if (c3.equals(w.getPosition())) { //for sure i can build in my originary position bc can't have more than 3 floors
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
     * Turn handler boolean.
     *
     * @param player the player
     * @param board  the board
     * @param action the action
     * @return the boolean
     * @throws Exception the exception
     */
    @Override
    public boolean turnHandler(Player player, IslandBoard board, Action action) throws Exception {
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