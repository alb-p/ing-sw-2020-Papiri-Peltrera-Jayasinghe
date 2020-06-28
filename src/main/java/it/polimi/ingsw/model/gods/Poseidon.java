package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Coordinate;

/**
 * The type Poseidon.
 */
public class Poseidon extends BasicGodCard {


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
        Worker otherWorker = getOtherWorker(worker, board);
        Coordinate otherCoordinate = otherWorker.getPosition();

        if (board.infoSlot(otherWorker.getPosition()).getConstructionLevel() == 0) {
            for (TreeActionNode moveNode : root.getChildren()) {
                if (moveNode.getData().getActionName().equalsIgnoreCase("move")) {
                    Move move = (Move) moveNode.getData();
                    for (TreeActionNode buildNode : moveNode.getChildren()) {
                        if (buildNode.getData().getActionName().equalsIgnoreCase("build")) {
                            Build build = (Build) buildNode.getData();
                            int standardBuildLevel = board.infoSlot(build.getEnd()).getConstructionLevel() + 1;
                            for (Coordinate extraBuild1 : otherCoordinate.getAdjacentCoords()) {
                                if ((board.infoSlot(extraBuild1).isFree() && !extraBuild1.equals(move.getEnd())) ||
                                        extraBuild1.equals(move.getStart())) {
                                    //coordinate extraBuild1 is free
                                    if (board.infoSlot(extraBuild1).getConstructionLevel() < 4) {
                                        int standardTemp1Level = standardBuildLevel;
                                        int extraBuild1Level = board.infoSlot(extraBuild1).getConstructionLevel() + 1;
                                        if (extraBuild1.equals(build.getEnd())) {
                                            standardTemp1Level += 1;
                                            extraBuild1Level = standardTemp1Level;
                                        }
                                        if (extraBuild1Level <= 4) {
                                            //standardBuildLevel = standardTemp1Level;
                                            TreeActionNode build1 = new TreeActionNode(new Build(otherCoordinate, extraBuild1));
                                            buildNode.addChild(build1);
                                            for (Coordinate extraBuild2 : otherCoordinate.getAdjacentCoords()) {
                                                if ((board.infoSlot(extraBuild2).isFree() && !extraBuild2.equals(move.getEnd())) ||
                                                        (extraBuild2.equals(move.getStart()))) {
                                                    if (board.infoSlot(extraBuild2).getConstructionLevel() < 4) {
                                                        int standardTemp2Level = standardTemp1Level;
                                                        int extra1Temp2Level = extraBuild1Level;
                                                        int extraBuild2Level = board.infoSlot(extraBuild2).getConstructionLevel() + 1;

                                                        if (extraBuild2.equals(build.getEnd())) {
                                                            standardTemp2Level += 1;
                                                            extraBuild2Level = standardTemp2Level;
                                                        }
                                                        if (extraBuild2.equals(extraBuild1)) {
                                                            extra1Temp2Level += 1;
                                                            extraBuild2Level = extra1Temp2Level;
                                                        }
                                                        if (extraBuild2Level <= 4) {
                                                            //standardBuildLevel = standardTemp2Level;
                                                            //extraBuild1Level = extra1Temp2Level;
                                                            TreeActionNode build2 = new TreeActionNode(new Build(otherCoordinate, extraBuild2));
                                                            build1.addChild(build2);
                                                            for (Coordinate extraBuild3 : otherCoordinate.getAdjacentCoords()) {
                                                                if ((board.infoSlot(extraBuild3).isFree() && !extraBuild3.equals(move.getEnd())) || (extraBuild3.equals(move.getStart()))) {
                                                                    if (board.infoSlot(extraBuild3).getConstructionLevel() < 4) {
                                                                        int standardTemp3Level = standardTemp2Level;
                                                                        int extra1Temp3Level = extra1Temp2Level;
                                                                        int extra2Temp3Level = extraBuild2Level;
                                                                        int extraBuild3Level = board.infoSlot(extraBuild3).getConstructionLevel() + 1;

                                                                        if (extraBuild3.equals(build.getEnd())) {
                                                                            standardTemp3Level += 1;
                                                                            extraBuild3Level = standardTemp3Level;
                                                                        }
                                                                        if (extraBuild3.equals(extraBuild1)) {
                                                                            extra1Temp3Level += 1;
                                                                            extraBuild3Level = extra1Temp3Level;
                                                                        }
                                                                        if (extraBuild3.equals(extraBuild2)) {
                                                                            extra2Temp3Level += 1;
                                                                            extraBuild3Level = extra2Temp3Level;
                                                                        }
                                                                        if (extraBuild3Level <= 4) {
                                                                            TreeActionNode build3 = new TreeActionNode(new Build(otherCoordinate, extraBuild3));
                                                                            build2.addChild(build3);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return root;
    }

    /**
     * Gets other worker.
     *
     * @param actual the actual worker
     * @param board  the board
     * @return the non plaaying worker
     */
    private Worker getOtherWorker(Worker actual, IslandBoard board) {
        Worker selected = null;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                selected = board.infoSlot(new Coordinate(i, j)).getWorker();
                if (selected != null && selected != actual && selected.getColor() == actual.getColor()) return selected;
            }
        }
        return selected;
    }

}
