package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChronusTest {

    BasicGodCard card = new Chronus();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() throws Exception {
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);

        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 0)).construct(Construction.DOME);

        board.infoSlot(new Coordinate(4, 3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 3)).construct(Construction.DOME);

        board.infoSlot(new Coordinate(1, 2)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 2)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 2)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 2)).construct(Construction.DOME);

        board.infoSlot(new Coordinate(4, 4)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 4)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 4)).construct(Construction.FLOOR);

        board.infoSlot(new Coordinate(2,4)).construct(Construction.FLOOR);

        //fifth dome but inadequate to win
        board.infoSlot(new Coordinate(2, 2)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2, 2)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2, 2)).construct(Construction.DOME);

        //normal winning condition
        board.infoSlot(new Coordinate(3,4)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(3,4)).construct(Construction.FLOOR);

    }

    @Test
    public void winningConditionWithPowerTest() throws Exception {
        Player player = new Player(0, "Pippo", Color.RED);
        player.setCard("CHRONUS");
        player.selectWorker(new Coordinate(2, 4));
        board.infoSlot(new Coordinate(2, 4)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(2, 4));

        VirtualBoard virtualBoard = new VirtualBoard();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Color color = null;
                Coordinate coordinate = new Coordinate(row, col);
                if (board.infoSlot(coordinate).getWorker() != null) {
                    color = board.infoSlot(coordinate).getWorker().getColor();
                }
                VirtualSlot temp = new VirtualSlot(color, board.infoSlot(coordinate).getConstructionLevel(),
                        board.infoSlot(coordinate).hasADome(), coordinate);
                virtualBoard.setSlot(temp);
            }
        }

        player.selectWorker(new Coordinate(2, 4));
        assertFalse(card.winningCondition(player.getWorker(0), board, virtualBoard));
        assertTrue(card.turnHandler(player, board, new Move(new Coordinate(2, 4), new Coordinate(3, 4))));

        assertFalse(card.winningCondition(player.getWorker(0), board, virtualBoard));
        assertTrue(card.turnHandler(player, board, new Build(new Coordinate(3, 4), new Coordinate(4, 4))));
        assertTrue(card.winningCondition(player.getWorker(0), board, virtualBoard));
    }

    @Test
    public void winningConditionNormalTest() throws Exception {
        Player player = new Player(0, "Pluto", Color.RED);
        player.setCard("CHRONUS");
        player.selectWorker(new Coordinate(3,4));
        board.infoSlot(new Coordinate(3,4)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(3,4));

        VirtualBoard virtualBoard = new VirtualBoard();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Color color = null;
                Coordinate coordinate = new Coordinate(row, col);
                if (board.infoSlot(coordinate).getWorker() != null) {
                    color = board.infoSlot(coordinate).getWorker().getColor();
                }
                VirtualSlot tempo = new VirtualSlot(color,
                        board.infoSlot(coordinate).getConstructionLevel(), board.infoSlot(coordinate).hasADome(), coordinate);
                virtualBoard.setSlot(tempo);
            }
        }
        player.selectWorker(new Coordinate(3,4));
        assertFalse(card.winningCondition(player.getWorker(0), board, virtualBoard));
        assertTrue(card.turnHandler(player, board, new Move(new Coordinate(3,4), new Coordinate(4, 4))));
        assertTrue(card.winningCondition(player.getWorker(0), board, virtualBoard));
    }

}