package it.polimi.ingsw.model;

//TODO renderla classe astratta

public class BasicGodCard {

    public boolean move(Worker w, Coordinate coord, IslandBoard board) throws Exception {
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

    public boolean build(Worker w, Coordinate coord, IslandBoard board) throws Exception {
        if (w.getPosition().isAdjacent(coord) && board.infoSlot(coord).isFree()) {
            Slot slot = board.infoSlot(coord);
            if (board.infoSlot(coord).getConstructionLevel() < 3) {
                slot.construct(Construction.FLOOR);
            } else {
                slot.construct(Construction.DOME);
            }
        } else {
            return false;
        }
        return true;
    }

    public void specialRule(TreeActionNode root, IslandBoard board) {
        return;
    }


    public boolean turnHandler(Player player, IslandBoard board, Action action) throws Exception {
        if (action instanceof Move) {
            return (this.move(player.getActualWorker(), action.getEnd(), board));
        } else if (action instanceof Build) {
            return (this.build(player.getActualWorker(), action.getEnd(), board));
        }
        return false;
    }


    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board){
        TreeActionNode tree = new TreeActionNode(null);
        for (Coordinate c1 : w.getPosition().getAdjacentCoords()) {  //check around worker position to perform a Move

            if (board.infoSlot(c1).isFree() &&
                    (board.infoSlot(w.getPosition()).getConstructionLevel() - board.infoSlot(c1).getConstructionLevel() >= -1 )) {

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

    public boolean winningCondition(Worker w, IslandBoard board, VirtualBoard virtualBoard) {

        if (virtualBoard.getSlot(w.getOldPosition().getRow(),w.getOldPosition().getCol()).getLevel() == 2 &&
                board.infoSlot(w.getPosition()).getConstructionLevel() == 3) {
            return true;
        }
        return false;
    }
}

