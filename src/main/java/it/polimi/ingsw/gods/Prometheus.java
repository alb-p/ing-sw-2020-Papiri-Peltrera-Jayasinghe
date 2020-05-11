package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;


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
                                   board.infoSlot(c1).getConstructionLevel()+1) {
                                System.out.println("COORD, C1:: "+c1+" C2:: "+c2);
                                System.out.println("SONO NELL'IF.WPOS:: "+board.infoSlot(w.getPosition()).getConstructionLevel()+
                                        "SLOT:: "+ board.infoSlot(c1).getConstructionLevel());
                                moveNode = new TreeActionNode(new Move(w.getPosition(), c2));
                            }
                        } else {
                            System.out.println();
                            if (board.infoSlot(w.getPosition()).getConstructionLevel() >=
                                    board.infoSlot(c2).getConstructionLevel()) {
                                moveNode = new TreeActionNode(new Move(w.getPosition(), c2));
                            }
                        }
                        for (Coordinate c3 : c2.getAdjacentCoords()) {
                            if (board.infoSlot(c3).isFree() && moveNode != null) {
                                TreeActionNode secondBuildNode = new TreeActionNode(new Build(c2, c3));
                                moveNode.addChild(secondBuildNode);
                            }
                        }
                        if(moveNode != null && !moveNode.getChildren().isEmpty()) {
                            firstBuildNode.addChild(moveNode);
                        }
                    }
                }
                if(!firstBuildNode.getChildren().isEmpty() &&
                        !firstBuildNode.getChildren().get(0).getChildren().isEmpty()){
                    root.addChild(firstBuildNode);
                }

            }
            moveNode = null;
        }
        return root;
    }

    @Override
    public boolean turnHandler(Player player, IslandBoard board, Action action) throws Exception {
        if (action instanceof Move) {
            return (this.move(player.getActualWorker(), action.getEnd(), board));
        } else if (action instanceof Build || action instanceof FirstBuild) {
            return (this.build(player.getActualWorker(), action.getEnd(), board));
        }
        return false;
    }

}