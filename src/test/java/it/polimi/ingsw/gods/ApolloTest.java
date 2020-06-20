package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class ApolloTest {

    BasicGodCard card = new Apollo();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() throws Exception {
        board = new IslandBoard();
        //Gray player can only move switching with the opponent player
        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.RED));
        board.infoSlot(new Coordinate(0, 1)).occupy(new Worker(new Coordinate(0, 1), Color.WHITE));
        board.infoSlot(new Coordinate(0, 0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(1, 1)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(2, 1)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);
        //Gray player can't move
        board.infoSlot(new Coordinate(4, 4)).occupy(new Worker(new Coordinate(4, 4), Color.RED));
        board.infoSlot(new Coordinate(3, 3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(3, 3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(3, 3)).occupy(new Worker(new Coordinate(3, 3), Color.WHITE));
        board.infoSlot(new Coordinate(4, 3)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(3, 4)).construct(Construction.DOME);

    }

    @Test
    public void modMoveTest() throws Exception {
        Player player = new Player("pippo", "red");
        player.setCard("APOLLO");
        board.infoSlot(new Coordinate(1,0)).free();
        board.infoSlot(new Coordinate(4,4)).free();
        board.infoSlot(new Coordinate(1, 0)).occupy(player.getWorker(0));
        board.infoSlot(new Coordinate(4, 4)).occupy(player.getWorker(1));
        player.getWorker(0).setPosition(new Coordinate(1, 0));
        player.getWorker(1).setPosition(new Coordinate(4, 4));
        player.selectWorker(new Coordinate(1, 0));
        assertTrue(card.turnHandler(player, board, new Move(new Coordinate(1,0), new Coordinate(0,1))));
        player.selectWorker(new Coordinate(4, 4));
        assertFalse(card.turnHandler(player, board, new Move(new Coordinate(4,4), new Coordinate(3,3))));


    }

    @Test
    public void cardTreeSetupTest(){
        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(1,0)).getWorker(), board);
        assertEquals(1, root.getChildren().size());
        assertNotNull(root.search(new Move((new Coordinate(1,0)), new Coordinate(0,1))));
        assertTrue(root.getChildren().get(0).getChildren().get(0).isLeaf());
        TreeActionNode root2 = card.cardTreeSetup(board.infoSlot(new Coordinate(4,4)).getWorker(), board);
        assertEquals(0,root2.getChildren().size());

    }



}
