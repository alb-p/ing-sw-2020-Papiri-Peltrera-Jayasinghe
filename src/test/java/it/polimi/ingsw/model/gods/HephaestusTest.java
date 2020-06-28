package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.model.*;

import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Before;

import org.junit.Test;

import static org.junit.Assert.*;


public class HephaestusTest {

    BasicGodCard card = new Hephaestus();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() {

        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.RED));
        board.infoSlot(new Coordinate(1, 3)).occupy(new Worker(new Coordinate(1, 3), Color.RED));
        board.infoSlot(new Coordinate(0, 1)).occupy(new Worker(new Coordinate(0, 1), Color.RED));
        board.infoSlot(new Coordinate(2, 2)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2, 2)).construct(Construction.FLOOR);

    }

    @Test
    public void cardTreeSetupTest(){
        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(1,3)).getWorker(),board);
        assertTrue(root.search(new Move(new Coordinate(1,3),new Coordinate(1,2)))!=null);
        assertTrue(root.search(new Move(new Coordinate(1,3),new Coordinate(1,2)))
                .search(new Build(new Coordinate(1,2),new Coordinate(2,2)))!=null);
        assertTrue(root.search(new Move(new Coordinate(1,3),new Coordinate(1,2)))
                .search(new Build(new Coordinate(1,2),new Coordinate(2,2))).isLeaf());

        assertTrue(root.search(new Move(new Coordinate(1,3),new Coordinate(1,2)))!=null);
        assertTrue(root.search(new Move(new Coordinate(1,3),new Coordinate(1,2)))
                .search(new Build(new Coordinate(1,2),new Coordinate(0,2)))!=null);
        assertFalse(root.search(new Move(new Coordinate(1,3),new Coordinate(1,2)))
                .search(new Build(new Coordinate(1,2),new Coordinate(0,2))).isLeaf());

    }
}