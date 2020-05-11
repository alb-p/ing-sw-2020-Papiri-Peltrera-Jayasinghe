package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;


public class Prometheus extends BasicGodCard {

    //Your Turn: If your Worker does
    //not move up, it may build both
    //before and after moving
    // MOVE 0,0 IN 0,1 & BUILD IN 1,1 ok
    // BUILD 0,1 IN 0,2 & MOVE IN 1,1 & BUILD IN 1,2
/*
    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode root = super.cardTreeSetup(w, board);
        for (Coordinate c1 : w.getPosition().getAdjacentCoords()) {     //first build
            if (board.infoSlot(c1).isFree()) {
                TreeActionNode firstBuildNode = new TreeActionNode(new FirstBuild(w.getPosition(), c1));
                for (Coordinate c2 : w.getPosition().getAdjacentCoords()) {     //move
                    if (board.infoSlot(c2).isFree() && board.infoSlot(w.getPosition()).getConstructionLevel() <=
                            board.infoSlot(c2).getConstructionLevel()  && (!c2.equals(c1) && board.infoSlot(w.getPosition()).getConstructionLevel() >
                            board.infoSlot(c1).getConstructionLevel() + 1) ) {
                        if (!(c2.equals(c1) && board.infoSlot(w.getPosition()).getConstructionLevel() <=
                                board.infoSlot(c1).getConstructionLevel() + 1) ) {


                        TreeActionNode moveNode = new TreeActionNode(new Move(w.getPosition(), c2));
                        for (Coordinate c3 : c2.getAdjacentCoords()) {
                            if (board.infoSlot(c3).isFree()) {
                                TreeActionNode secondBuildNode = new TreeActionNode(new Build(c2, c3));
                                moveNode.addChild(secondBuildNode);
                            }
                        }
                        firstBuildNode.addChild(moveNode);
                    }
                }
                root.addChild(firstBuildNode);
            }
        }
        return root;
    }*/

    @Override
    public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
        TreeActionNode moveNode;
        moveNode = null;
        TreeActionNode root = super.cardTreeSetup(w, board);
        for (Coordinate c1 : w.getPosition().getAdjacentCoords()) {     //first build
            if (board.infoSlot(c1).isFree()) {
                TreeActionNode firstBuildNode = new TreeActionNode(new FirstBuild(w.getPosition(), c1));
                for (Coordinate c2 : w.getPosition().getAdjacentCoords()) {     //move
                    if (board.infoSlot(c2).isFree()) {
                        if (c2.equals(c1)) {
                            if (board.infoSlot(w.getPosition()).getConstructionLevel() >=
                                    board.infoSlot(c1).getConstructionLevel() + 1) {
                                System.out.println("COORD, C1:: " + c1 + " C2:: " + c2);
                                System.out.println("SONO NELL'IF.WPOS:: " + board.infoSlot(w.getPosition()).getConstructionLevel() +
                                        "SLOT:: " + board.infoSlot(c1).getConstructionLevel());
                                moveNode = new TreeActionNode(new Move(w.getPosition(), c2));
                            }
                        } else {
                            if (board.infoSlot(w.getPosition()).getConstructionLevel() >=
                                    board.infoSlot(c2).getConstructionLevel()) {
                                moveNode = new TreeActionNode(new Move(w.getPosition(), c2));
                            }
                        }
                        for (Coordinate c3 : c2.getAdjacentCoords()) {
                            if (moveNode != null) {
                                if (board.infoSlot(c3).isFree() && !c3.equals(w.getPosition()) && !c3.equals(c1)) {
                                    TreeActionNode secondBuildNode = new TreeActionNode(new Build(c2, c3));
                                    moveNode.addChild(secondBuildNode);
                                    System.out.println("AGGIUNGO SECONDBUILD IN MOVENODE, CASO NORMALE");
                                } else if (c3.equals(w.getPosition())) {
                                    TreeActionNode secondBuildNode = new TreeActionNode(new Build(c2, c3));
                                    moveNode.addChild(secondBuildNode);
                                    System.out.println("AGGIUNGO SECONDBUILD IN MOVENODE, w.getPosition()");
                                } else if (c3.equals(c1)) {
                                    if (board.infoSlot(c3).getConstructionLevel() < 3) {
                                        TreeActionNode secondBuildNode = new TreeActionNode(new Build(c2, c3));
                                        moveNode.addChild(secondBuildNode);
                                        System.out.println("AGGIUNGO SECONDBUILD IN MOVENODE, SULLA FIRST BUILD");
                                    }
                                }
                            }
                        }
                        if (moveNode != null && !moveNode.getChildren().isEmpty()) {
                            firstBuildNode.addChild(moveNode);
                        }
                    }
                }
                if (!firstBuildNode.getChildren().isEmpty() &&
                        !firstBuildNode.getChildren().get(0).getChildren().isEmpty()) {
                    root.addChild(firstBuildNode);
                }

            }
            moveNode = null;
        }
        return root;

    }

    /*
        @Override
        public TreeActionNode cardTreeSetup(Worker w, IslandBoard board) {
            ArrayList<TreeActionNode> promList = new ArrayList<>();
            Coordinate start = w.getPosition();
            Coordinate firstBuilding;
            Coordinate destination;
            Coordinate secondBuilding;
            TreeActionNode root = super.cardTreeSetup(w, board);

            for (Coordinate c1 : w.getPosition().getAdjacentCoords()) {     //first build
                if (board.infoSlot(c1).isFree()) {
                    System.out.println("SONO IN PROM");
                    TreeActionNode firstBuildNode = new TreeActionNode(new FirstBuild(w.getPosition(), c1));
                    firstBuilding = c1;
                    for (Coordinate c2 : w.getPosition().getAdjacentCoords()) {
                        TreeActionNode moveNode;
                        if (!(c2.equals(firstBuilding) && board.infoSlot(w.getPosition()).getConstructionLevel() >=
                                board.infoSlot(firstBuilding).getConstructionLevel() + 1) || (!(board.infoSlot(c2).isFree() && board.infoSlot(w.getPosition()).getConstructionLevel() >=
                                board.infoSlot(c2).getConstructionLevel()))) {
                            break;
                        }
                        moveNode = new TreeActionNode(new Move(w.getPosition(), c2));
                        destination = c2;
                        for (Coordinate c3 : destination.getAdjacentCoords()) {
                            //speciali: start e firstbuilding
                            if (board.infoSlot(c3).isFree() && !c3.equals(start) && !c3.equals(firstBuilding)) {
                                TreeActionNode secondBuildNode = new TreeActionNode(new Build(destination, c3));
                                moveNode.addChild(secondBuildNode);
                                System.out.println("AGGIUNGO SECONDBUILD IN MOVENODE, CASO NORMALE");
                                secondBuilding = c3;
                            } else if (c3.equals(start)) {
                                TreeActionNode secondBuildNode = new TreeActionNode(new Build(destination, c3));
                                moveNode.addChild(secondBuildNode);
                                System.out.println("AGGIUNGO SECONDBUILD IN MOVENODE, START");
                                secondBuilding = c3;
                            } else if (c3.equals(firstBuilding)) {
                                if (board.infoSlot(c3).getConstructionLevel() < 3) {
                                    TreeActionNode secondBuildNode = new TreeActionNode(new Build(destination, c3));
                                    moveNode.addChild(secondBuildNode);
                                    System.out.println("AGGIUNGO SECONDBUILD IN MOVENODE, SULLA FIRST BUILD");
                                    secondBuilding = c3;
                                }
                            }
                        }
                        //verificare che il for c3 abbia prodotoo almeno un figlio per moveNode
                        if (!moveNode.isLeaf()) {
                            System.out.println("AGGIUNGO MOVENODE IN FIRSTBUILDNODE");
                            firstBuildNode.addChild(moveNode);
                        }
                    }
                    if (!firstBuildNode.isLeaf()) {
                        System.out.println("AGGIUNGO FIRSTBUILD IN PROMLIST");
                        promList.add(firstBuildNode);
                    }

                }
            }
            root.getChildren().addAll(promList);
            return root;
        }
    */
    @Override
    public boolean turnHandler(Player player, IslandBoard board, Action action) throws Exception {
        if (action instanceof Move) {
            if (this.move(player.getActualWorker(), action.getEnd(), board)) {
                player.setNotBuildDone();
                return true;
            }
        } else if (action.getActionName().equalsIgnoreCase("Build") ||
                action.getActionName().equalsIgnoreCase("First build")) {
            return (this.build(player.getActualWorker(), action.getEnd(), board));
        }
        return false;
    }

}