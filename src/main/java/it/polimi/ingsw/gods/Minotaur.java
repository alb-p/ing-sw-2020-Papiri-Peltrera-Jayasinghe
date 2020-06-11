package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

/**
 * Your Move: Your Worker may
 * move into an opponent Worker’s
 * space, if their Worker can be
 * forced one space straight backwards to an
 * unoccupied space at any level
 */

public class Minotaur extends BasicGodCard {



    @Override
    public boolean turnHandler(Player player, IslandBoard board, Action action) throws Exception {
        if (action.getActionName().equalsIgnoreCase("move")) {
            if(!this.move(player.getActualWorker(), action.getEnd(), board)){
                return this.modMove(player.getActualWorker(), action.getEnd(), board);
            }
            return true;
        } else if (action.getActionName().equalsIgnoreCase("build")) {
            return (this.build(player.getActualWorker(), action.getEnd(), board));
        }
        return false;
    }


    private boolean modMove(Worker w, Coordinate coord, IslandBoard board) {
        String relPos = relativePosition(w.getPosition(), coord);
        Coordinate next = nextRelCoord(coord, relPos);
        Worker oppWorker;
        if (relPos.equals("ERR") || !next.isValid()) {
            return false;
        }
        if (!board.infoSlot(coord).isFree() && !board.infoSlot(coord).hasADome() &&
                board.infoSlot(coord).getWorker().getColor() != w.getColor() &&
                board.infoSlot(coord).getConstructionLevel() -
                            board.infoSlot(w.getPosition()).getConstructionLevel() < 2){
            oppWorker= board.infoSlot(coord).getWorker();
            board.infoSlot(coord).free();
            board.infoSlot(next).occupy(oppWorker);
            oppWorker.setPosition(next);
            board.infoSlot(w.getPosition()).free();
            board.infoSlot(coord).occupy(w);
            w.setPosition(coord);

        }


            return true;
    }

    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(w, board);
        ArrayList<TreeActionNode> toAdd = new ArrayList<>();
        TreeActionNode newMove;
        newMove = null;
        for (Coordinate c : w.getPosition().getAdjacentCoords()) {
            if (!board.infoSlot(c).isFree() && !board.infoSlot(c).hasADome() &&
                    board.infoSlot(c).getWorker().getColor() != w.getColor() &&
                    board.infoSlot(c).getConstructionLevel() -
                            board.infoSlot(w.getPosition()).getConstructionLevel() < 2) {

                String relPos = relativePosition(w.getPosition(), c);
                if (!relPos.equals("ERR")) {
                    Coordinate nextRel = nextRelCoord(c, relPos);
                    if (nextRel.isValid() && board.infoSlot(nextRel).isFree()) {
                        newMove = new TreeActionNode(new Move(w.getPosition(), c));
                        for (Coordinate coord : c.getAdjacentCoords()) {
                            if (coord.equals(w.getPosition()) || (board.infoSlot(coord).isFree() && !coord.equals(nextRel))) {
                                newMove.addChild(new TreeActionNode(new Build(c, coord)));
                            }
                        }
                    }
                }


            }
            if (newMove != null && newMove.getChildren().size() > 0) {
                toAdd.add(newMove);
            }
        }
        root.getChildren().addAll(toAdd);

        return root;
    }

    public String relativePosition(Coordinate baseCoord, Coordinate relCoord) {
        if (baseCoord.getRow() == relCoord.getRow()) {
            if (baseCoord.getCol() + 1 == relCoord.getCol()) {
                return "E";
            } else if (baseCoord.getCol() - 1 == relCoord.getCol()) {
                return "W";
            }
        } else if (baseCoord.getRow() + 1 == relCoord.getRow()) {
            if ((baseCoord.getCol() == relCoord.getCol())) {
                return "N";
            } else if ((baseCoord.getCol() + 1 == relCoord.getCol())) {
                return "NE";
            } else if ((baseCoord.getCol() - 1 == relCoord.getCol())) {
                return "NW";
            }

        } else if (baseCoord.getRow() - 1 == relCoord.getRow())
            if ((baseCoord.getCol() == relCoord.getCol())) {
                return "S";
            } else if ((baseCoord.getCol() + 1 == relCoord.getCol())) {
                return "SE";
            } else if ((baseCoord.getCol() - 1 == relCoord.getCol())) {
                return "SW";
            }
        return "ERR";
    }

    public Coordinate nextRelCoord(Coordinate baseCoord, String relPos) {
        int diffRow = 0;
        int diffCol = 0;
        if (relPos.equals("N")) {
            diffRow = +1;
            diffCol = 0;
        } else if (relPos.equals("NE")) {
            diffRow = +1;
            diffCol = +1;
        } else if (relPos.equals("E")) {
            diffRow = 0;
            diffCol = +1;
        } else if (relPos.equals("SE")) {
            diffRow = -1;
            diffCol = +1;
        } else if (relPos.equals("S")) {
            diffRow = -1;
            diffCol = 0;
        } else if (relPos.equals("SW")) {
            diffRow = -1;
            diffCol = -1;
        } else if (relPos.equals("W")) {
            diffRow = 0;
            diffCol = -1;
        } else if (relPos.equals("NW")) {
            diffRow = +1;
            diffCol = -1;
        }

        return new Coordinate(baseCoord.getRow() + diffRow, baseCoord.getCol() + diffCol);

    }
}
