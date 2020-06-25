package it.polimi.ingsw.model;

/**
 * The type Basic god card.
 */
public class BasicGodCard {

    /**
     * Move boolean.
     *
     * @param w     the w
     * @param coord the coord
     * @param board the board
     * @return the boolean
     */
    public boolean move(Worker w, Coordinate coord, IslandBoard board) {
        Slot workerSlot = board.infoSlot(w.getPosition());
        Slot destSlot = board.infoSlot(coord);
        if (w.getPosition().isAdjacent(coord) &&
                (workerSlot.getConstructionLevel() - destSlot.getConstructionLevel() >= -1) && destSlot.isFree()) {
            workerSlot.free();
            destSlot.occupy(w);
            w.setPosition(coord);
            return true;
        }
        return false;
    }

    /**
     * Build boolean.
     *
     * @param w     the w
     * @param coord the coordinate where the
     *              worker wants to build
     * @param board the board
     * @return the outcome of the build
     */
    public boolean build(Worker w, Coordinate coord, IslandBoard board){
        if (w.getPosition().isAdjacent(coord) && board.infoSlot(coord).isFree()) {
            Slot slot = board.infoSlot(coord);
            if (board.infoSlot(coord).getConstructionLevel() < 3) {
                return slot.construct(Construction.FLOOR);
            } else {
                return slot.construct(Construction.DOME);
            }
        } else {
            return false;
        }
    }

    /**
     * Special rule.
     *
     * @param root  the root
     * @param board the board
     */
    public void specialRule(TreeActionNode root, IslandBoard board) {
        return;
    }



    /**
     * Handle the action chose from the player
     * and calls the respective methods
     *
     * @param player the player
     * @param board  the board
     * @param action the action wanted to be performed
     * @return the outcome of the action wanted to be performed
     */
    public boolean turnHandler(Player player, IslandBoard board, Action action){
        if (action instanceof Move) {
            return (this.move(player.getActualWorker(), action.getEnd(), board));
        } else if (action instanceof Build) {
            return (this.build(player.getActualWorker(), action.getEnd(), board));
        }
        return false;
    }


    /**
     * Card tree setup tree action node.
     *
     * @param w     the w
     * @param board the board
     * @return the tree action node
     */
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode tree = new TreeActionNode(null);
        if(w == null) System.out.println("worker nulll");
        for (Coordinate c1 : w.getPosition().getAdjacentCoords()) {  //check around worker position to perform a Move

            if (board.infoSlot(c1).isFree() &&
                    (board.infoSlot(w.getPosition()).getConstructionLevel() - board.infoSlot(c1).getConstructionLevel() >= -1)) {

                TreeActionNode moveNode = new TreeActionNode(new Move(w.getPosition(), c1));
                for (Coordinate c2 : c1.getAdjacentCoords()) {                       //check around every position to perform a Build
                    if (board.infoSlot(c2).isFree()) {
                        TreeActionNode buildNode = new TreeActionNode(new Build(c1, c2));
                        moveNode.addChild(buildNode);
                    }
                }
                //adding the initial worker position as a possible Build position
                TreeActionNode buildNode = new TreeActionNode(new Build(c1, w.getPosition()));
                moveNode.addChild(buildNode);
                tree.addChild(moveNode);

            }
        }
        return tree;

    }

    /**
     * Winning condition boolean.
     *
     * @param w            the w
     * @param board        the board
     * @param virtualBoard the virtual board
     * @return the boolean
     */
    public boolean winningCondition(Worker w, IslandBoard board, VirtualBoard virtualBoard) {

        if (virtualBoard.getSlot(new Coordinate(w.getOldPosition().getRow(), w.getOldPosition().getCol())).getLevel() == 2 &&
                board.infoSlot(w.getPosition()).getConstructionLevel() == 3) {
            return true;
        }
        return false;
    }
}

