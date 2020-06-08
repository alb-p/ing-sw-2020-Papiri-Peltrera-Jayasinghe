package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisTest {

    BasicGodCard card = new Artemis();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() throws Exception {

        board.infoSlot(new Coordinate(1, 3)).occupy(new Worker(new Coordinate(1, 3), Color.RED));

    }


    @Test
    public void cardTreeSetupTest() {

        TreeActionNode root= card.cardTreeSetup(board.infoSlot(new Coordinate(1,3)).getWorker(),board);
        assertTrue(root.getChildrenActions().size()==1);
        assertTrue(root.getChildren().get(0).getChildrenActions().size()==2);
        assertTrue(root.getChildren().get(0).getChildrenActions().get(1).getActionName().equals("move"));

        assertFalse(root.getChildren().get(0).getChildren().get(5).isLeaf());

    }
}