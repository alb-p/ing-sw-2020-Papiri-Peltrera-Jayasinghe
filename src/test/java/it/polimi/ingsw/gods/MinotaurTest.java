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
        board.infoSlot(new Coordinate(1, 1)).occupy(new Worker(new Coordinate(1, 1), Color.GRAY));
        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.GRAY));
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);
    }

    @Test
    public void treeSetupTest() {

        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(0, 0)).getWorker(), board);
        assertFalse(root.isLeaf());
        assertTrue(root.search(new Move(new Coordinate(0, 0), new Coordinate(0, 1))) == null);
        assertTrue(root.search(new Move(new Coordinate(0, 0), new Coordinate(1, 1))) != null);
        assertTrue(root.search(new Move(new Coordinate(0, 0), new Coordinate(1, 0))) == null);
        assertTrue(root.getChildrenActions().size() == 1);
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
    }
}
