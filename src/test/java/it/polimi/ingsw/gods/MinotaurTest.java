package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinotaurTest {

    BasicGodCard card = new Minotaur();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() throws Exception {

        board.infoSlot(new Coordinate(0, 0)).occupy(new Worker(new Coordinate(0, 0), Color.BLUE));
        board.infoSlot(new Coordinate(0, 1)).occupy(new Worker(new Coordinate(0, 1), Color.BLUE));
        board.infoSlot(new Coordinate(1, 1)).occupy(new Worker(new Coordinate(1, 1), Color.RED));
        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.RED));
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);
    }

    @Test
    public void relPositionTest(){
        Minotaur specCard = (Minotaur) card;
        assertEquals("N", specCard.relativePosition(new Coordinate(2,2),new Coordinate(3,2)));
        assertEquals("S", specCard.relativePosition(new Coordinate(2,2),new Coordinate(1,2)));
        assertEquals("E", specCard.relativePosition(new Coordinate(2,2),new Coordinate(2,3)));
        assertEquals("W", specCard.relativePosition(new Coordinate(2,2),new Coordinate(2,1)));
        assertEquals("NE", specCard.relativePosition(new Coordinate(2,2),new Coordinate(3,3)));
        assertEquals("NW", specCard.relativePosition(new Coordinate(2,2),new Coordinate(3,1)));
        assertEquals("SW", specCard.relativePosition(new Coordinate(2,2),new Coordinate(1,1)));
        assertEquals("SE", specCard.relativePosition(new Coordinate(2,2),new Coordinate(1,3)));
        assertEquals("ERR", specCard.relativePosition(new Coordinate(2,2),new Coordinate(1,-3)));

        assertEquals(board.infoSlot(new Coordinate(3,2)), board.infoSlot(specCard.nextRelCoord(new Coordinate(2,2),"N")));
        assertEquals(board.infoSlot(new Coordinate(1,2)), board.infoSlot(specCard.nextRelCoord(new Coordinate(2,2),"S")));
        assertEquals(board.infoSlot(new Coordinate(2,3)), board.infoSlot(specCard.nextRelCoord(new Coordinate(2,2),"E")));
        assertEquals(board.infoSlot(new Coordinate(2,1)), board.infoSlot(specCard.nextRelCoord(new Coordinate(2,2),"W")));
        assertEquals(board.infoSlot(new Coordinate(3,1)), board.infoSlot(specCard.nextRelCoord(new Coordinate(2,2),"NW")));
        assertEquals(board.infoSlot(new Coordinate(3,3)), board.infoSlot(specCard.nextRelCoord(new Coordinate(2,2),"NE")));
        assertEquals(board.infoSlot(new Coordinate(1,1)), board.infoSlot(specCard.nextRelCoord(new Coordinate(2,2),"SW")));
        assertEquals(board.infoSlot(new Coordinate(1,3)), board.infoSlot(specCard.nextRelCoord(new Coordinate(2,2),"SE")));
        assertEquals(board.infoSlot(new Coordinate(2,2)), board.infoSlot(specCard.nextRelCoord(new Coordinate(2,2),"ERR")));
    }
    @Test
    public void treeSetupTest() {

        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(0, 0)).getWorker(), board);
        assertFalse(root.isLeaf());
        assertNull(root.search(new Move(new Coordinate(0, 0), new Coordinate(0, 1))));
        assertNotNull(root.search(new Move(new Coordinate(0, 0), new Coordinate(1, 1))));
        assertNull(root.search(new Move(new Coordinate(0, 0), new Coordinate(1, 0))));
        assertEquals(1, root.getChildrenActions().size());
        assertFalse(root.getChildren().get(0).isLeaf());
        assertTrue(root.getChildren().get(0).getChildren().get(0).isLeaf());
    }

    @Test
    public void modMoveTest() throws Exception {
        Player player = new Player(0, "Pippo", Color.BLUE);
        player.setCard("MINOTAUR");
        board.infoSlot(new Coordinate(0, 0)).free();
        board.infoSlot(new Coordinate(0, 1)).free();
        board.infoSlot(new Coordinate(0, 0)).occupy(player.getWorker(0));
        board.infoSlot(new Coordinate(0, 1)).occupy(player.getWorker(1));
        player.selectWorker(new Coordinate(0, 0));
        player.getWorker(0).setPosition(new Coordinate(0, 0));
        player.getWorker(1).setPosition(new Coordinate(0, 1));
        assertFalse(card.turnHandler(player, board, new Move(new Coordinate(0,0),new Coordinate(0,1))));
        assertFalse(card.turnHandler(player, board, new Move(new Coordinate(0,0),new Coordinate(1,0))));
        assertTrue(card.turnHandler(player, board, new Move(new Coordinate(0,0),new Coordinate(1,1))));
        player.selectWorker(new Coordinate(0, 1));
        assertTrue(card.turnHandler(player, board, new Build(new Coordinate(0,1),new Coordinate(0,2))));
        assertTrue(card.turnHandler(player, board, new Move(new Coordinate(0,1),new Coordinate(0,2))));
        assertFalse(card.turnHandler(player, board, new Build(new Coordinate(0,1),new Coordinate(3,1))));
        assertFalse(card.turnHandler(player, board, new Move(new Coordinate(0,1),new Coordinate(0,0))));
        assertFalse(card.turnHandler(player, board, new FirstBuild(new Coordinate(0,1),new Coordinate(0,0))));

    }
}
