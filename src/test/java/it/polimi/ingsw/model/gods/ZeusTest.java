package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.VirtualBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZeusTest {

    BasicGodCard card = new Zeus();
    IslandBoard board = new IslandBoard();
    VirtualBoard vBoard = new VirtualBoard();
    Model model = new Model();

    @Before
    public void init() {
        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.RED));
        board.infoSlot(new Coordinate(1, 3)).occupy(new Worker(new Coordinate(1, 3), Color.RED));
        board.infoSlot(new Coordinate(0, 1)).occupy(new Worker(new Coordinate(0, 1), Color.WHITE));
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(1,1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1,1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1,1)).construct(Construction.FLOOR);
    }

    @Test
    public void cardTreeSetupTest() {
        TreeActionNode root= card.cardTreeSetup(board.infoSlot(new Coordinate(1,0)).getWorker(),board);
        assertTrue(root.getChildrenActions().size()==1);
        assertEquals(1, root.getChildren().get(0).getChildrenActions().size());
        assertEquals(3, root.getChildren().get(0).getChildren().size());
        assertTrue(root.getChildren().get(0).getChildren().get(0).isLeaf());
    }

    @Test
    public void buildUnderOwnPosition() {
        board.infoSlot(new Coordinate(1, 0)).free();
        Player player = new Player(0, "Pluto", Color.BLUE);
        player.setCard("ZUES");
        board.infoSlot(new Coordinate(0,0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(0,0));
        board.infoSlot(new Coordinate(3,0)).occupy(player.getWorker(1));
        player.getWorker(1).setPosition(new Coordinate(3,0));
        assertFalse(card.build(player.getWorker(0),new Coordinate(0,1),board));
        assertTrue(card.build(player.getWorker(0),new Coordinate(1,1),board));
        assertTrue(board.infoSlot(new Coordinate(1,1)).hasADome());
        assertTrue(card.build(player.getWorker(0),new Coordinate(0,0),board));
        assertTrue(card.build(player.getWorker(0),new Coordinate(0,0),board));
        assertTrue(card.build(player.getWorker(0),new Coordinate(0,0),board));
        assertFalse(card.build(player.getWorker(0),new Coordinate(0,0),board));
        assertEquals(3, board.infoSlot(new Coordinate(0,0)).getConstructionLevel());
        assertFalse(board.infoSlot(new Coordinate(0,0)).hasADome());
    }

    @Test
    public void winningCondition() {
        board.infoSlot(new Coordinate(1, 0)).free();
        Player player = new Player(0, "Pluto", Color.BLUE);
        player.setCard("ZUES");
        board.infoSlot(new Coordinate(0,0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(0,0));
        board.infoSlot(new Coordinate(3,0)).occupy(player.getWorker(1));
        player.getWorker(1).setPosition(new Coordinate(3,0));
        card.build(player.getWorker(0), new Coordinate(0,0), board);
        card.build(player.getWorker(0), new Coordinate(0,0), board);
        vBoard = model.cloneVBoard(board);
        assertFalse(card.move(player.getWorker(0), new Coordinate(0,0), board));
        assertTrue(card.build(player.getWorker(0), new Coordinate(0,0), board));
        assertFalse(card.winningCondition(player.getWorker(0), board,vBoard));
    }
}