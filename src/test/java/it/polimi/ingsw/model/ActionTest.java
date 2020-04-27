package it.polimi.ingsw.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.naming.InsufficientResourcesException;

import static org.junit.Assert.*;

public class ActionTest {
    Player player;
    IslandBoard board;
    BasicGodCard card;

    @Before
    public void init() {
        player = new Player(0, "NICK", "WHITE");
        board = new IslandBoard();
        card = new BasicGodCard();
        player.setCard("STDGOD");

        try {
            setupBoardTest();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @BeforeEach
    public void restartBoard(){

    }

    @BeforeEach
    public void setupBoardTest() throws Exception {
        board.infoSlot(new Coordinate(4,0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(1,1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1,1)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(3,0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1,0)).occupy(new Worker(new Coordinate(1,0), "BLUE"));
        board.infoSlot(new Coordinate(0,2)).occupy(new Worker(new Coordinate(0,2), "BLUE"));
        board.infoSlot(new Coordinate(1,4)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2,2)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(1,2));

    }

    @Test
    public void cardMoveTest() throws Exception {
        player.playerTreeSetup(board);
        player.selectWorker(new Coordinate(1,2));

        assertTrue(card.turnHandler(player, board, new Move(new Coordinate(1,2),
                new Coordinate(0,1)), false));

        assertFalse(board.infoSlot(new Coordinate(0, 1)).isFree());
        assertTrue(board.infoSlot(new Coordinate(1,2)).isFree());
        //assertEquals(board.infoSlot(new Coordinate(1, 2)).getWorker(), player.getWorker(0));
        //0,0 is occupied by player's worker2
        assertTrue(card.turnHandler(player, board,
                new Move(new Coordinate(0, 1), new Coordinate(0, 0)), false));
    }

    @Test
    public void playerMoveTest() throws Exception {
        player.playerTreeSetup(board);
        player.selectWorker(new Coordinate(1,2));
        assertTrue(player.turnHandler(board,
                new Move(new Coordinate(1,2), new Coordinate(0,1))));
        assertFalse(board.infoSlot(new Coordinate(0, 1)).isFree());
        assertTrue(board.infoSlot(new Coordinate(1,2)).isFree());
        player.playerTreeSetup(board);
        //0,0 is occupied by player's worker2
        //assertFalse(player.turnHandler(board,
        //        new Move(new Coordinate(0, 1), new Coordinate(0, 0))));
    }


}
