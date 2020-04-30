package it.polimi.ingsw.model;

//TODO renderla classe astratta


public class BasicGodCard {
    private boolean flag;

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
            //construction.build(slot);
            if (board.infoSlot(coord).getConstructionLevel() < 3) {
                slot.construct(Construction.FLOOR);
            } else {
                slot.construct(Construction.DOME);
            }
        } else {
            return false;
            //throw new Exception("Invalid build");
        }
        return true;
    }

    public void specialRule(Object o, Object o2, IslandBoard board) {
        System.out.println("SPECIALRULE");
        return;
    }

    public boolean hasWon(Worker w, IslandBoard board) {
        if (board.infoSlot(w.getOldPosition()).getConstructionLevel() == 2 &&
                board.infoSlot(w.getPosition()).getConstructionLevel() == 3) {
            return true;
        }
        return false;
    }


    public boolean turnHandler(Player player, IslandBoard board, Action action) throws Exception {
        if (action instanceof Move) {
            return (this.move(player.getActualWorker(), action.getEnd(), board));
        } else if (action instanceof Build) {
            return (this.build(player.getActualWorker(), action.getEnd(), board));
        }
        return false;
    }

    public Coordinate stringToCoord(String string) {
        String[] coords = string.split(",");
        return new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));


    }

    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board){
        TreeActionNode tree = new TreeActionNode(null);

        for (Coordinate c1 : w.getPosition().getAdiacentCoords()) {  //controllo intorno al worker per fare move

            if (board.infoSlot(c1).isFree() &&
                    (board.infoSlot(w.getPosition()).getConstructionLevel() - board.infoSlot(c1).getConstructionLevel() >= -1 )) {

                TreeActionNode moveNode = new TreeActionNode(new Move(w.getPosition(), c1));
                for (Coordinate c2 : c1.getAdiacentCoords()) {                          //controllo intorno ad ogni posizione per fare build con un worker falso
                    if (board.infoSlot(c2).isFree()) {                                          //stesso controllo che si fa anche nel build del basicGod
                        TreeActionNode buildNode = new TreeActionNode(new Build(c1, c2));
                        moveNode.addChild(buildNode);
                    }
                }
                TreeActionNode buildNode = new TreeActionNode(new Build(c1, w.getPosition()));  //aggiungo possibilità di build nella primissima posizione di partenza
                moveNode.addChild(buildNode);
                tree.addChild(moveNode);
                //per apollo mettere controllo collegamento per nodo move

            }
        }
        return tree;

    }

    public boolean winningCondition(Worker w) {

        return false;
    }
}

