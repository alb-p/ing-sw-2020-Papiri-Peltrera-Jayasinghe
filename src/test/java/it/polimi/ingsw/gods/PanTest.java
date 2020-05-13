package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanTest {

    BasicGodCard card = new Pan();
    IslandBoard board = new IslandBoard();


    @Before
    public void init() throws Exception {


        board.infoSlot(new Coordinate(2, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.FLOOR);

        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);


    }

    @Test
    public void winningConditionWithPowerTest() throws Exception {
        Player player = new Player(0, "Pippo", Color.GRAY);
        player.setCard("PAN");
        player.selectWorker(new Coordinate(2, 0));
        board.infoSlot(new Coordinate(2, 0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(2, 0));


        VirtualBoard virtualBoard=new VirtualBoard(board);


        player.selectWorker(new Coordinate(2, 0));

        assertTrue(card.turnHandler(player,board,new Move(new Coordinate(2,0),new Coordinate(2,1))));

        assertTrue(card.winningCondition(player.getWorker(0),board,virtualBoard));
    }

    @Test
    public void winningConditionNormalTest() throws Exception {
        Player player = new Player(0, "Pippo", Color.GRAY);
        player.setCard("PAN");
        player.selectWorker(new Coordinate(2, 0));
        board.infoSlot(new Coordinate(2, 0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(2, 0));


        VirtualBoard virtualBoard=new VirtualBoard(board);


        player.selectWorker(new Coordinate(2, 0));

        assertTrue(card.turnHandler(player,board,new Move(new Coordinate(2,0),new Coordinate(1,0))));

        assertTrue(card.winningCondition(player.getWorker(0),board,virtualBoard));
    }
}