package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.VirtualBoard;
import it.polimi.ingsw.utils.VirtualSlot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanTest {

    BasicGodCard card = new Pan();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() {
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.FLOOR);

        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
    }

    @Test
    public void winningConditionWithPowerTest() {
        Player player = new Player(0, "Pippo", Color.RED);
        player.setCard("PAN");
        player.selectWorker(new Coordinate(2, 0));
        board.infoSlot(new Coordinate(2, 0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(2, 0));
        VirtualBoard virtualBoard= new VirtualBoard();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Color color = null;
                Coordinate c = new Coordinate(row, col);
                if (board.infoSlot(c).getWorker() != null) {
                    color = board.infoSlot(c).getWorker().getColor();
                }
                VirtualSlot tempo = new VirtualSlot(color,
                        board.infoSlot(c).getConstructionLevel(), board.infoSlot(c).hasADome(), c);
                virtualBoard.setSlot(tempo);
            }
        }

        player.selectWorker(new Coordinate(2, 0));
        assertTrue(card.turnHandler(player,board,new Move(new Coordinate(2,0),new Coordinate(2,1))));
        System.out.println(virtualBoard);
        System.out.println(board);
        assertTrue(card.winningCondition(player.getWorker(0),board,virtualBoard));
    }

    @Test
    public void winningConditionNormalTest() {
        Player player = new Player(0, "Pippo", Color.RED);
        player.setCard("PAN");
        player.selectWorker(new Coordinate(2, 0));
        board.infoSlot(new Coordinate(2, 0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(2, 0));
        VirtualBoard virtualBoard= new VirtualBoard();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Color color = null;
                Coordinate c = new Coordinate(row, col);
                if (board.infoSlot(c).getWorker() != null) {
                    color = board.infoSlot(c).getWorker().getColor();
                }
                VirtualSlot tempo = new VirtualSlot(color,
                        board.infoSlot(c).getConstructionLevel(), board.infoSlot(c).hasADome(), c);
                virtualBoard.setSlot(tempo);
            }
        }


        player.selectWorker(new Coordinate(2, 0));
        assertTrue(card.turnHandler(player,board,new Move(new Coordinate(2,0),new Coordinate(1,0))));
        assertTrue(card.winningCondition(player.getWorker(0),board,virtualBoard));
    }
}