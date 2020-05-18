package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrometheusTest {


    BasicGodCard card = new Prometheus();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() throws Exception {
        board = new IslandBoard();
        board.infoSlot(new Coordinate(0, 1)).occupy(new Worker(new Coordinate(0, 1), Color.WHITE));
        board.infoSlot(new Coordinate(0, 0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(4, 4)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 3)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(3, 4)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.GRAY));
        board.infoSlot(new Coordinate(4, 4)).occupy(new Worker(new Coordinate(4, 4), Color.GRAY));
    }

    @Test
    public void cardTreeSetupTest() {

        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(1, 0)).getWorker(), board);
        assertEquals(2, root.getChildrenActions().size());
        assertTrue(root.getChildren().get(0).getChildren().get(0).isLeaf());
        assertTrue(root.getChildren().get(2).getChildren().get(0).getChildren().get(0).isLeaf());
        TreeActionNode root2 = card.cardTreeSetup(board.infoSlot(new Coordinate(4, 4)).getWorker(), board);
        assertEquals(2, root2.getChildren().size());

    }

    @Test
    public void modMoveTest() throws Exception {
        Player player = new Player("pippo", "gray");
        player.setCard("PROMETHEUS");
        board.infoSlot(new Coordinate(1, 0)).occupy(player.getWorker(0));
        board.infoSlot(new Coordinate(4, 4)).occupy(player.getWorker(1));
        player.getWorker(0).setPosition(new Coordinate(1, 0));
        player.getWorker(1).setPosition(new Coordinate(4, 4));
        player.selectWorker(new Coordinate(1, 0));
        assertTrue(card.turnHandler(player, board, new FirstBuild(new Coordinate(1,0), new Coordinate(1,1))));
        player.selectWorker(new Coordinate(4, 4));
        assertTrue(card.turnHandler(player, board, new FirstBuild(new Coordinate(4,4), new Coordinate(3,3))));
    }
}
