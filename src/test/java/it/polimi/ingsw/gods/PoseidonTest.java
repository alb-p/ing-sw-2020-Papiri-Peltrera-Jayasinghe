package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PoseidonTest {
    BasicGodCard card = new Poseidon();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() throws Exception {
        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.RED));
        board.infoSlot(new Coordinate(0, 0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(3, 3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(3, 3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(3, 3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 3)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(3, 4)).construct(Construction.DOME);
    }

    @Test
    public void cardTreeSetupTest() {
        board.infoSlot(new Coordinate(1, 3)).occupy(new Worker(new Coordinate(1, 3), Color.RED));
        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(1, 0)).getWorker(), board);
        TreeActionNode endFirstPlayerNode = root.getChildren().get(0).getChildren().get(0);
        assertEquals(1, root.getChildrenActions().size());
        assertEquals(1, root.getChildren().get(0).getChildrenActions().size());
        assertTrue(root.getChildrenActions().get(0) instanceof Move);
        assertTrue(root.getChildren().get(0).getChildrenActions().get(0) instanceof Build);
        assertTrue(endFirstPlayerNode.getChildrenActions().get(0) instanceof Build);
        assertTrue(new Coordinate(1, 3).equals(endFirstPlayerNode.getChildren().get(0).getData().getStart()));
        assertTrue(new Coordinate(1, 3).equals(endFirstPlayerNode.getChildren().get(0).getChildren().get(0).getData().getStart()));
        assertTrue(new Coordinate(1, 3).equals(endFirstPlayerNode.getChildren().get(0).getChildren().get(0).getChildren().get(0).getData().getStart()));
        assertEquals(0, endFirstPlayerNode.getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().size());
    }

    @Test
    public void cardTreeSetup2Test() {
        board.infoSlot(new Coordinate(4, 1)).occupy(new Worker(new Coordinate(4, 1), Color.RED));
        //unMoved Worker is not at ground level, I expect no extra build
        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(1, 0)).getWorker(), board);
        TreeActionNode endFirstPlayerNode = root.getChildren().get(0).getChildren().get(0);
        assertEquals(1, root.getChildrenActions().size());
        assertEquals(1, root.getChildren().get(0).getChildrenActions().size());
        assertTrue(root.getChildrenActions().get(0) instanceof Move);
        assertTrue(root.getChildren().get(0).getChildrenActions().get(0) instanceof Build);
        assertEquals(0, endFirstPlayerNode.getChildren().size());
    }

    @Test
    public void cardTreeSetup3Test() {
        board.infoSlot(new Coordinate(4, 4)).occupy(new Worker(new Coordinate(4, 4), Color.RED));
        //unMoved Worker is not allowed to build more than a dome
        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(1, 0)).getWorker(), board);
        TreeActionNode endFirstPlayerNode = root.getChildren().get(0).getChildren().get(0);
        assertEquals(1, root.getChildrenActions().size());
        assertEquals(1, root.getChildren().get(0).getChildrenActions().size());
        assertTrue(root.getChildrenActions().get(0) instanceof Move);
        assertTrue(root.getChildren().get(0).getChildrenActions().get(0) instanceof Build);
        assertEquals(1, endFirstPlayerNode.getChildren().size());
        assertEquals(0, endFirstPlayerNode.getChildren().get(0).getChildren().size());
    }

}