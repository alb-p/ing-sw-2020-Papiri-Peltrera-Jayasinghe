package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

/**
 * Your Move: Your Worker may
 *     move one additional time, but not
 *     back to its initial space.
 */
public class Artemis extends BasicGodCard {


    /**
     * Create the tree of a worker based
     * on the goddess' special power
     *
     * @param worker     the worker that will be able
     *              to perform the actions in the tree
     * @param board the board of the game
     * @return the root of the tree
     */
    @Override
    public TreeActionNode cardTreeSetup(Worker worker, IslandBoard board) {
        TreeActionNode root =  super.cardTreeSetup(worker, board); //creo l'albero normale

        for(TreeActionNode t: root.getChildren()) { //applico uno pseudo cardtreesetup basic per ogni nodo move dell'albero
            Coordinate firstMoveCoord=t.getData().getEnd();

            for (Coordinate c1 : firstMoveCoord.getAdjacentCoords()) {

                if (board.infoSlot(c1).isFree() &&
                        (board.infoSlot(firstMoveCoord).getConstructionLevel() - board.infoSlot(c1).getConstructionLevel() >= -1 )) {

                    TreeActionNode moveNode = new TreeActionNode(new Move(firstMoveCoord, c1));
                    for (Coordinate c2 : c1.getAdjacentCoords()) {
                        if (board.infoSlot(c2).isFree()) {
                            TreeActionNode buildNode = new TreeActionNode(new Build(c1, c2));
                            moveNode.addChild(buildNode);
                        }
                    }
                    if(worker.getPosition().isAdjacent(c1)) { //se dopo la seconda move sono vicino alla primissima posizione del worker
                        TreeActionNode buildNode = new TreeActionNode(new Build(c1, worker.getPosition()));  //allora posso costruire in quella posizione
                        moveNode.addChild(buildNode);
                    }
                    t.addChild(moveNode);

                }
            }
        }

        return root;
    }

}
