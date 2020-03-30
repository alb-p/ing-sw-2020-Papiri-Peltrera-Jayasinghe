package it.polimi.ingsw.model;


public class BasicGodCard {
    private boolean flag;

    public void move(Worker w, Coordinate coord, IslandBoard board) throws Exception {
        //necessario dare anche la board alla carta per poter segnare la propria mossa
        if (w.getPosition().isAdjacent(coord)) {
            Slot slot = board.infoSlot(coord);
            if (slot.isFree()) {
                board.infoSlot(w.getPosition()).free();
                slot.occupy(w);
                w.setPosition(coord);
            } else {
                throw new Exception("The selected slot is not free");
            }
        }
    }

    public void build(Worker w, Coordinate coord, IslandBoard board, Construction construction) throws Exception {
        if (w.getPosition().isAdjacent(coord)) {
            Slot slot = board.infoSlot(coord);
            //construction.build(slot);
            slot.construct(construction);
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

    public void turnHandler(Player player, IslandBoard board, String string) throws Exception {
        // MOVE 0,0 IN 3,2 & BUILD 2,1
        String[] words = string.split(" ");
        System.out.println("QUI");
        if (words[0].toUpperCase().equals("MOVE")) {

            this.move(player.getWorker(stringToCoord(words[1])), stringToCoord(words[3]), board);

            if (words[5].toUpperCase().equals("BUILD")) {
                this.build(player.getWorker(stringToCoord(words[3])),stringToCoord(words[6]), board, new Floor());
            }

        }

        return;
    }

    public Coordinate stringToCoord (String string){
        String[] coords = string.split(",");
        return new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));


    }
}

