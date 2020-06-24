package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

import java.util.stream.Collectors;

/**
 * The type Hypnus.
 */
public class Hypnus extends BasicGodCard {

    //Start of Opponent’s Turn: If one
    //of your opponent’s Workers is
    //higher than all of their others, it
    //cannot move

    /**
     * Special rule.
     *
     * @param root  the root
     * @param board the board
     */
    @Override
    public void specialRule(TreeActionNode root, IslandBoard board) {
        Coordinate higher = searchHigherWorker(root, board);
        if (higher != null) {

            for (int i = 0; i < root.getChildren().size(); i++) {
                if (root.getChild(i).getData().getStart().equals(higher)) {
                    root.getChildren().remove(root.getChild(i));
                    i--;
                }
            }
        }
    }

    /**
     * Search higher worker coordinate.
     *
     * @param root  the root
     * @param board the board
     * @return the coordinate
     */
    private Coordinate searchHigherWorker(TreeActionNode root, IslandBoard board) {
        Coordinate firstWorkerCoordinate = null;
        if (root.getChildren().isEmpty()) return null;
        Color colorPlayer = board.infoSlot(root.getChildren().get(0).getData().getStart()).getWorker().getColor();
        firstWorkerCoordinate = root.getChildren().get(0).getData().getStart();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Worker worker = board.infoSlot(new Coordinate(i, j)).getWorker();
                if (worker != null && worker.getColor().equals(colorPlayer) && !firstWorkerCoordinate.equals(worker.getPosition())) {
                    if (board.infoSlot(firstWorkerCoordinate).getConstructionLevel() > board.infoSlot(worker.getPosition()).getConstructionLevel()) {
                        return firstWorkerCoordinate;
                    } else if (board.infoSlot(firstWorkerCoordinate).getConstructionLevel() ==
                            board.infoSlot(worker.getPosition()).getConstructionLevel()) return null;
                    else return worker.getPosition();
                }
            }
        }
        return null;
    }
}
