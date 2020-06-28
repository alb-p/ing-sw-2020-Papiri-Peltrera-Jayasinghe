package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class IslandBoardTest {

    IslandBoard board;

    @Before
    public void init() throws Exception {
        board = new IslandBoard();
        board.infoSlot(new Coordinate(4, 4)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 4)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 4)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2, 2)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2, 2)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(0, 4)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(1, 1)).occupy(new Worker(new Coordinate(1, 1), Color.BLUE));


    }

    @Test
    public void infoSlotTest() {

        assertNull(board.infoSlot(new Coordinate(-1, 1)));
        assertNull(board.infoSlot(new Coordinate(-1, -1)));
        assertNotNull(board.infoSlot(new Coordinate(0, 0)));
        assertNotNull(board.infoSlot(new Coordinate(4, 0)));
        assertNotNull(board.infoSlot(new Coordinate(3, 1)));
        assertFalse(board.infoSlot(new Coordinate(4, 4)).hasADome());
        assertTrue(board.infoSlot(new Coordinate(4, 4)).isFree());
        assertNull(board.infoSlot(new Coordinate(4, 4)).getWorker());
        assertEquals(3, board.infoSlot(new Coordinate(4, 4)).getConstructionLevel());
        assertEquals(2, board.infoSlot(new Coordinate(2, 2)).getConstructionLevel());
        assertTrue(board.infoSlot(new Coordinate(0, 4)).hasADome());
        assertFalse(board.infoSlot(new Coordinate(0, 4)).isFree());
        assertEquals(0, board.infoSlot(new Coordinate(1, 1)).getConstructionLevel());
        assertNotNull(board.infoSlot(new Coordinate(1,1)).getWorker());
        assertFalse(board.infoSlot(new Coordinate(1, 1)).isFree());

    }
}
