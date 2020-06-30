package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisTest {

    BasicGodCard card;
    IslandBoard board;

    @Before
    public void init() {
        card = new Artemis();
        board = new IslandBoard();

    }


    @Test
    public void cardTreeSetupTest() {
        Player player = new Player(0, "Pippo", Color.RED);
        player.setCard("ARTEMIS");
        player.selectWorker(new Coordinate(2, 0));
        board.infoSlot(new Coordinate(2, 0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(2, 0));
        TreeActionNode root= card.cardTreeSetup(player.getWorker(0),board);
        assertEquals(1, root.getChildrenActions().size());
        assertEquals(2, root.getChildren().get(0).getChildrenActions().size());
        assertEquals("move", root.getChildrenActions().get(0).getActionName());
        assertEquals("move", root.getChildren().get(0).getChildrenActions().get(1).getActionName());
        assertFalse(root.getChildren().get(0).getChildren().get(5).isLeaf());
    }
}