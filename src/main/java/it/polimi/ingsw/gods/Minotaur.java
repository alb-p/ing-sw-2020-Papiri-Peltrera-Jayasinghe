package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

/**
 * The type Minotaur.
 */
public class Minotaur extends BasicGodCard {


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


    /**
     * Mod move boolean.
     *
     * @param w     the w
     * @param coord the coord
     * @param board the board
     * @return the boolean
     */
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

    /**
     * Card tree setup tree action node.
     *
     * @param w     the w
     * @param board the board
     * @return the tree action node
     */
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

    /**
     * Relative position string.
     *
     * @param baseCoord the base coord
     * @param relCoord  the rel coord
     * @return the string
     */
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

    /**
     * Next rel coord coordinate.
     *
     * @param baseCoord the base coord
     * @param relPos    the rel pos
     * @return the coordinate
     */
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
