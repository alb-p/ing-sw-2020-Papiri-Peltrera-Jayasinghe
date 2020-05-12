package it.polimi.ingsw.model;

import it.polimi.ingsw.gods.Demeter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;


public class PlayerTest {

    Player player;
    IslandBoard board;

    @Before
    public void init(){
        player = new Player(0, "playerTest", "BLUE");
        player.setCard("DEMETER");
        board = new IslandBoard();
        board.infoSlot(new Coordinate(0,0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(0,0));
        board.infoSlot(new Coordinate(0,4)).occupy(player.getWorker(1));
        player.getWorker(1).setPosition(new Coordinate(0,4));


    }

    @Test
    public void playerSetupTest() throws Exception {
        assertEquals("playerTest", player.getNickName());
        assertEquals(Color.BLUE, player.getWorker(0).getColor());
        assertEquals(Color.BLUE, player.getWorker(1).getColor());
        assertTrue(player.getCard() instanceof Demeter);
        assertFalse(player.hasDone());
        assertNull(player.getActualWorker());
        player.playerTreeSetup(board);
        assertNotNull(player.getAvailableAction());
        assertFalse(player.selectWorker(new Coordinate(4,4)));

    }
}