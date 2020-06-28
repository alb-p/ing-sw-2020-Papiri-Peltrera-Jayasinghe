package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Coordinate;

import java.util.ArrayList;

/**
 * Your Move: Your Worker may
 *      move into an opponent Worker’s
 *      space, if their Worker can be
 *      forced one space straight backwards to an
 *      unoccupied space at any level.
 */
public class Minotaur extends BasicGodCard {


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
     * The implementation of the
     * special move, power of Apollo.
     *
     * @param worker  the worker
     * @param coord   the end of the move
     * @param board   the board
     * @return the outcome of the move
     */
    private boolean modMove(Worker worker, Coordinate coord, IslandBoard board) {
        String relPos = relativePosition(worker.getPosition(), coord);
        Coordinate next = nextRelCoord(coord, relPos);
        Worker oppWorker;
        if (relPos.equals("ERR") || !next.isValid()) {
            return false;
        }
        if (!board.infoSlot(coord).isFree() && !board.infoSlot(coord).hasADome() &&
                board.infoSlot(coord).getWorker().getColor() != worker.getColor() &&
                board.infoSlot(coord).getConstructionLevel() -
                            board.infoSlot(worker.getPosition()).getConstructionLevel() < 2){
            oppWorker= board.infoSlot(coord).getWorker();
            board.infoSlot(coord).free();
            board.infoSlot(next).occupy(oppWorker);
            oppWorker.setPosition(next);
            board.infoSlot(worker.getPosition()).free();
            board.infoSlot(coord).occupy(worker);
            worker.setPosition(coord);

        }
            return true;
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
        TreeActionNode newMove;
        newMove = null;
        for (Coordinate c : worker.getPosition().getAdjacentCoords()) {
            if (!board.infoSlot(c).isFree() && !board.infoSlot(c).hasADome() &&
                    board.infoSlot(c).getWorker().getColor() != worker.getColor() &&
                    board.infoSlot(c).getConstructionLevel() -
                            board.infoSlot(worker.getPosition()).getConstructionLevel() < 2) {

                String relPos = relativePosition(worker.getPosition(), c);
                if (!relPos.equals("ERR")) {
                    Coordinate nextRel = nextRelCoord(c, relPos);
                    if (nextRel.isValid() && board.infoSlot(nextRel).isFree()) {
                        newMove = new TreeActionNode(new Move(worker.getPosition(), c));
                        for (Coordinate coord : c.getAdjacentCoords()) {
                            if (coord.equals(worker.getPosition()) || (board.infoSlot(coord).isFree() && !coord.equals(nextRel))) {
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
     * Relative position between two coordinates.
     *
     * @param baseCoord the base coord
     * @param relCoord  the rel coord
     * @return the value in a string with
     *          the compass point
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
     * Next coordinate in the same directon.
     *
     * @param baseCoord the base coord
     * @param relPos    the rel pos
     * @return the the coordinate in the direction of
     *          the relative position
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
