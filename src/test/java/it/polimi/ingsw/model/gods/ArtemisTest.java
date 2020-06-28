package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisTest {

    BasicGodCard card = new Artemis();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() {

        board.infoSlot(new Coordinate(1, 3)).occupy(new Worker(new Coordinate(1, 3), Color.RED));

    }


    @Test
    public void cardTreeSetupTest() {
        TreeActionNode root= card.cardTreeSetup(board.infoSlot(new Coordinate(1,3)).getWorker(),board);
        assertEquals(1, root.getChildrenActions().size());
        assertEquals(2, root.getChildren().get(0).getChildrenActions().size());
        assertEquals("move", root.getChildren().get(0).getChildrenActions().get(1).getActionName());
        assertFalse(root.getChildren().get(0).getChildren().get(5).isLeaf());
    }
}