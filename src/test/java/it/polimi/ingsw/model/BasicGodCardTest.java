package it.polimi.ingsw.model;

import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.actions.FirstBuild;
import it.polimi.ingsw.actions.Move;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BasicGodCardTest {

    IslandBoard board = new IslandBoard();
    BasicGodCard card = new BasicGodCard();

    @Before
    public void init() throws Exception {
        board = new IslandBoard();

        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.RED));
        board.infoSlot(new Coordinate(1, 3)).occupy(new Worker(new Coordinate(1, 3), Color.RED));
        board.infoSlot(new Coordinate(0, 1)).occupy(new Worker(new Coordinate(0, 1), Color.WHITE));
        board.infoSlot(new Coordinate(4, 4)).occupy(new Worker(new Coordinate(4, 4), Color.WHITE));
        board.infoSlot(new Coordinate(4, 0)).occupy(new Worker(new Coordinate(4, 0), Color.BLUE));
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(3, 3)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(3, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(3, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(3, 1)).construct(Construction.FLOOR);

    }

    @Test
    public void CardTreeSetup(){

        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(4,4)).getWorker(),board);

        assertNotNull(root.search(new Move(new Coordinate(4, 4), new Coordinate(4, 3))));
        assertNull(root.search(new Move(new Coordinate(4, 4), new Coordinate(3, 3))));
        assertEquals(1,root.getChildrenActions().size());
        assertTrue(root.getChildren().get(0).getChildren().get(0).getData().getActionName().equalsIgnoreCase("Build"));
        assertEquals(1,root.getChildren().get(0).getChildrenActions().size());
        assertTrue(root.getChildren().get(0).getChildren().get(0).isLeaf());

        root = card.cardTreeSetup(board.infoSlot(new Coordinate(4,0)).getWorker(),board);
        assertNotNull(root.search(new Move(new Coordinate(4, 0), new Coordinate(4, 1))));
        assertNull(root.search(new Move(new Coordinate(4, 0), new Coordinate(3, 0))));
        assertEquals(1,root.getChildrenActions().size());
        assertEquals(1,root.getChildren().get(0).getChildrenActions().size());
        assertTrue(root.getChildren().get(0).getChildren().get(0).isLeaf());
        assertNull(root.search(new Move(new Coordinate(4,0), new Coordinate(3,0))));
        assertNotNull(root.search(new Move(new Coordinate(4,0), new Coordinate(3,1))));



    }

    @Test
    public void moveTest() {
        Player player = new Player(0, "Paperino", Color.BLUE);

        board.infoSlot(new Coordinate(4,1)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(4,1));

        assertFalse(card.move(player.getWorker(0),new Coordinate(4,0),board));
        assertFalse(card.move(player.getWorker(0),new Coordinate(3,0),board));
        assertTrue(card.move(player.getWorker(0),new Coordinate(3,1),board));
    }

    @Test
    public void buildTest() {
        Player player = new Player(0, "Paperino", Color.BLUE);

        board.infoSlot(new Coordinate(4,1)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(4,1));


        assertFalse(card.build(player.getWorker(0),new Coordinate(4,0),board));
        assertTrue(card.build(player.getWorker(0),new Coordinate(3,0),board));
        assertTrue(card.build(player.getWorker(0),new Coordinate(3,1),board));
    }

    @Test
    public void turnHandlerTest() throws Exception {
        Player player = new Player(0,"Pluto", Color.WHITE);

        board.infoSlot(new Coordinate(2,1)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(2,1));
        player.selectWorker(new Coordinate(2,1));

        assertFalse(card.turnHandler(player, board, new Build(new Coordinate(2,1), new Coordinate(2,0))));
        assertTrue(card.turnHandler(player,board, new Build(new Coordinate(2,1), new Coordinate(3,0))));
        assertTrue(card.turnHandler(player,board, new Build(new Coordinate(2,1), new Coordinate(3,0))));
        assertFalse(card.turnHandler(player,board, new Build(new Coordinate(2,1), new Coordinate(3,0))));
        assertFalse(card.turnHandler(player, board, new FirstBuild(new Coordinate(2,1), new Coordinate(3,0))));
    }


}
