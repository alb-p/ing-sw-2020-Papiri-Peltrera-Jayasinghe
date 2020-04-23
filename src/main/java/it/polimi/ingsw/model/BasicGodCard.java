package it.polimi.ingsw.model;

//TODO renderla classe astratta


public class BasicGodCard {
    private boolean flag;

    public void move(Worker w, Coordinate coord, IslandBoard board) throws Exception {
        //necessario dare anche la board alla carta per poter segnare la propria mossa
        Slot workerSlot = board.infoSlot(w.getPosition());
        Slot destSlot = board.infoSlot(coord);
        if (w.getPosition().isAdjacent(coord) && (workerSlot.getConstructionLevel() - destSlot.getConstructionLevel() >= -1 ||
                workerSlot.getConstructionLevel() - destSlot.getConstructionLevel() <= 1)) {

            if (destSlot.isFree()) {
                workerSlot.free();
                destSlot.occupy(w);
                w.setPosition(coord);
            } else {
                throw new Exception("The selected slot is not free");
            }
        }
    }

    public void build(Worker w, Coordinate coord, IslandBoard board) throws Exception {
        if (w.getPosition().isAdjacent(coord) && board.infoSlot(coord).isFree()) {
            Slot slot = board.infoSlot(coord);
            //construction.build(slot);
            if (board.infoSlot(coord).getConstructionLevel() < 3) {
                slot.construct(Construction.FLOOR);
            } else {
                slot.construct(Construction.DOME);
            }
        } else {
            throw new Exception("Invalid build");
        }
    }

    public void specialRule(Object o, Object o2, IslandBoard board) {
    }

    public boolean hasWon(Worker w, IslandBoard board) throws Exception {
        if (board.infoSlot(w.getOldPosition()).getConstructionLevel() == 2 &&
                board.infoSlot(w.getPosition()).getConstructionLevel() == 3) {
            return true;
        }
        return false;
    }


    public boolean turnHandler(Player player, IslandBoard board, Action action, boolean halfDone) throws Exception {
        // MOVE 0,0 IN 3,2 & BUILD IN 2,1
        /*String[] words = string.split(" ");
        if (!halfDone && words[0].toUpperCase().equals("MOVE")) {

            this.move(player.getWorker(stringToCoord(words[1])), stringToCoord(words[3]), board);

        }
        if (!halfDone && words.length > 4) {
            this.build(player.getWorker(stringToCoord(words[3])), stringToCoord(words[7]), board);
            return true;
        }
        if (halfDone && words[0].toUpperCase().equals("BUILD")) {
            this.build(player.getWorker(stringToCoord(words[1])), stringToCoord(words[3]), board);
            return true;

        }
        */
        if(action instanceof Move) {
            this.move(player.getActualWorker(), action.getEnd(), board);
        }else if(action instanceof Build){
            this.build(player.getActualWorker(), action.getEnd(), board);
        }
        return false;
    }

    public Coordinate stringToCoord(String string) {
        String[] coords = string.split(",");
        return new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));


    }

    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) throws Exception {
        TreeActionNode tree= new TreeActionNode(null);

        for(Coordinate c1: w.getPosition().getAdiacentCoords()){  //controllo intorno al worker per fare move

            if(board.infoSlot(c1).isFree()&&(board.infoSlot(w.getPosition()).getConstructionLevel() - board.infoSlot(c1).getConstructionLevel() >= -1 || //stesso controllo che si fa anche nel move del basicGod
                    board.infoSlot(w.getPosition()).getConstructionLevel() - board.infoSlot(c1).getConstructionLevel() <= 1)) {

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

