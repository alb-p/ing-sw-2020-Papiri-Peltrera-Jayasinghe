package it.polimi.ingsw.model;

import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class ActionTest {
    Player player;
    IslandBoard board;
    BasicGodCard card;

    @Before
    public void setupBoardTest() throws Exception {

        player = new Player(0, "NICK", "WHITE");
        board = new IslandBoard();
        card = new BasicGodCard();
        player.setCard("ATLAS");
        board.infoSlot(new Coordinate(4,0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(1,1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1,1)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(3,0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1,0)).occupy(new Worker(new Coordinate(1,0), "BLUE"));
        board.infoSlot(new Coordinate(0,2)).occupy(new Worker(new Coordinate(0,2), "BLUE"));
        board.infoSlot(new Coordinate(1,4)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2,2)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(2,2));
        board.infoSlot(new Coordinate(4,4)).occupy(player.getWorker(1));
        player.getWorker(1).setPosition(new Coordinate(4,4));

    }

    @Test
    public void cardMoveTest() throws Exception {
        player.playerTreeSetup(board);
        player.selectWorker(new Coordinate(2,2));

        assertTrue(card.turnHandler(player, board, new Move(new Coordinate(2,2),
                new Coordinate(2,1))));

        assertFalse(board.infoSlot(new Coordinate(2, 1)).isFree());
        assertTrue(board.infoSlot(new Coordinate(2,2)).isFree());
        assertTrue(card.turnHandler(player, board,
                new Move(new Coordinate(2, 1), new Coordinate(2, 0))));
    }

    @Test
    public void playerMoveTest() throws Exception {
        player.playerTreeSetup(board);
        player.selectWorker(new Coordinate(2,2));
        assertTrue(player.turnHandler(board,
                new Move(new Coordinate(2,2), new Coordinate(2,1))));
        assertFalse(board.infoSlot(new Coordinate(2, 1)).isFree());
        assertTrue(board.infoSlot(new Coordinate(2,2)).isFree());
        player.playerTreeSetup(board);
    }

    @Test
    public void startEndTest(){
        Action action = new Move(new Coordinate(0,0), new Coordinate(2,1));
        action.setStart(new Coordinate(2,2));
        action.setEnd(new Coordinate(5,4));
        assertTrue(action.getStart().equals(new Coordinate(2,2)));
        assertTrue(action.getEnd().equals(new Coordinate(5,4)));
        Action action1 = new Move(new Coordinate(2,2), new Coordinate(5,4));
        assertTrue(action.equals(action1));
    }


}
