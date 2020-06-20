package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasTest {

    BasicGodCard card = new Atlas();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() throws Exception {
        board = new IslandBoard();

        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.RED));
        board.infoSlot(new Coordinate(1, 3)).occupy(new Worker(new Coordinate(1, 3), Color.RED));
        board.infoSlot(new Coordinate(0, 1)).occupy(new Worker(new Coordinate(0, 1), Color.RED));
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);
    }

    @Test
    public void cardTreeSetupTest() throws Exception {
        TreeActionNode root= card.cardTreeSetup(board.infoSlot(new Coordinate(1,3)).getWorker(),board);
        assertTrue(root.getChildrenActions().size()==1);
        assertTrue(root.getChildren().get(0).getChildrenActions().size()==2);
        assertTrue(root.getChildren().get(0).getChildren().get(0).isLeaf());
    }


    @Test
    public void buildDomeTest() throws Exception {
        board.infoSlot(new Coordinate(1, 3)).free();
        Player player = new Player(0, "Pluto", Color.BLUE);
        board.infoSlot(new Coordinate(0,0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(0,0));
        board.infoSlot(new Coordinate(3,0)).occupy(player.getWorker(1));
        player.getWorker(1).setPosition(new Coordinate(3,0));

        assertFalse(card.turnHandler(player,board,new BuildDome(new Coordinate(0,0),new Coordinate(0,1))));
        assertFalse(card.turnHandler(player,board,new BuildDome(new Coordinate(3,0),new Coordinate(2,0))));
        assertTrue(card.turnHandler(player,board,new BuildDome(new Coordinate(3,0),new Coordinate(2,1))));
        assertTrue(card.turnHandler(player,board,new BuildDome(new Coordinate(0,0),new Coordinate(1,1))));
        assertTrue(board.infoSlot(new Coordinate(1,1)).hasADome());
        assertTrue(board.infoSlot(new Coordinate(2,1)).hasADome());
        assertFalse(board.infoSlot(new Coordinate(0,1)).hasADome());

    }

}
