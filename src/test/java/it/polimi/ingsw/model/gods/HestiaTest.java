package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HestiaTest {
    BasicGodCard card = new Hestia();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() {
        board.infoSlot(new Coordinate(0, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(0, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(0, 1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(0, 1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 2)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.RED));
        board.infoSlot(new Coordinate(1, 3)).occupy(new Worker(new Coordinate(1, 3), Color.RED));
    }

    @Test
    public void cardTreeSetupTest() {
        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(1, 0)).getWorker(), board);
        assertEquals(1, root.getChildrenActions().size());
        assertEquals(5, root.getChildren().get(0).getChildren().size());
        assertEquals(1, root.getChildren().get(0).getChildren().get(0).getChildrenActions().size());
        assertEquals(3, root.getChildren().get(0).getChildren().get(0).getChildren().size());
        TreeActionNode root1 = card.cardTreeSetup(board.infoSlot(new Coordinate(1, 3)).getWorker(), board);
        assertEquals(1, root1.getChildrenActions().size());
        assertEquals(7, root1.getChildren().size());
        assertEquals(4, root1.getChildren().get(0).getChildren().size());
        assertEquals(2, root1.getChildren().get(0).getChildren().get(0).getChildren().size());
    }

}