package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

public class Poseidon extends BasicGodCard {
    //End of Your Turn: If your
    //unmoved Worker is on the
    //ground level, it may build up to
    //three times.


    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(w, board);
        Worker otherWorker;


        for (TreeActionNode moveNode : root.getChildren()) {
            if (moveNode.getData() instanceof Move) {
                otherWorker = getOtherWorker(board.infoSlot(moveNode.getData().getStart()).getWorker(), board);
                for (TreeActionNode buildNode : moveNode.getChildren()) {
                    Coordinate endBuild = buildNode.getData().getEnd();
                    int buildLevel = board.infoSlot(endBuild).getConstructionLevel() + 1;
                    if (board.infoSlot(otherWorker.getPosition()).getConstructionLevel() == 0) {
                        for (Coordinate c1 : otherWorker.getPosition().getAdjacentCoords()) {
                            boolean bE1 = c1.equals(buildNode.getData().getEnd());
                            if (extraBuildConditions(c1, moveNode.getData().getStart(), moveNode.getData().getEnd(), buildNode.getData().getEnd(), board)) {
                                TreeActionNode extraBuild1 = new TreeActionNode(new Build(otherWorker.getPosition(), c1));
                                int levelE1 = board.infoSlot(c1).getConstructionLevel() + 1;
                                if (bE1) {
                                    levelE1++;
                                    buildLevel++;
                                }
                                buildNode.addChild(extraBuild1);
                                for (Coordinate c2 : otherWorker.getPosition().getAdjacentCoords()) {
                                    boolean bE2 = c2.equals(buildNode.getData().getEnd());
                                    int levelE2 = board.infoSlot(c2).getConstructionLevel();
                                    if (bE1 && bE2) { //c1 == c2
                                        if (levelE1 == 3) {
                                            break;
                                        }
                                        levelE1++;
                                        levelE2 = levelE1;
                                        buildLevel++;
                                    } else if (bE2) {
                                        if (buildLevel > 3) break;
                                        buildLevel++;
                                        levelE2 = buildLevel;
                                    } else if (c1.equals(c2)) {
                                        if (levelE1 > 3) break;
                                        levelE1++;
                                        levelE2 = levelE1;
                                    } else {
                                        if (!(extraBuildConditions(c2, moveNode.getData().getStart(), moveNode.getData().getEnd(), buildNode.getData().getEnd(), board))) {
                                            break;
                                        }
                                        levelE2++;
                                    }
                                    TreeActionNode extraBuild2 = new TreeActionNode(new Build(otherWorker.getPosition(), c2));
                                    extraBuild1.addChild(extraBuild2);
                                    for (Coordinate c3 : otherWorker.getPosition().getAdjacentCoords()) {
                                        boolean bE3 = c3.equals(buildNode.getData().getEnd());
                                        if (bE3) {
                                            if( buildLevel > 3) break;
                                        } else if (c3.equals(c2) && c3.equals(c1)) {
                                            if(levelE1 > 3) break;
                                        } else if (c3.equals(c2)) {
                                            if(levelE2 > 3) break;
                                        } else if (c3.equals(c1)) {
                                            if(levelE1 > 3) break;
                                        } else {
                                            if (!(extraBuildConditions(c3, moveNode.getData().getStart(), moveNode.getData().getEnd(), buildNode.getData().getEnd(), board))) {
                                                break;
                                            }
                                        }
                                        TreeActionNode extraBuild3 = new TreeActionNode(new Build(otherWorker.getPosition(), c3));
                                        extraBuild2.addChild(extraBuild3);
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

    private boolean extraBuildConditions(Coordinate c, Coordinate startMove, Coordinate endMove, Coordinate endBuild, IslandBoard board) {
        return (!c.equals(endMove) && (board.infoSlot(c).isFree() || c.equals(startMove)) && (!c.equals(endBuild) || (c.equals(endBuild)
                && board.infoSlot(endBuild).getConstructionLevel() < 3)));
    }


    private Worker getOtherWorker(Worker actual, IslandBoard board) {
        Worker selected = null;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                selected = board.infoSlot(new Coordinate(i, j)).getWorker();
                if(selected != null) {
                    if (selected != actual && selected.getColor() == actual.getColor()) {
                        return selected;
                    }
                }
            }
        }
        return selected;
    }


}
