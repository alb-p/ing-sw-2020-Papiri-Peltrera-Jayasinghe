package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HypnusTest {

    BasicGodCard card = new Hypnus();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() throws Exception {
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1,1)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(3,3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(3,3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2,3)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(2,3)).construct(Construction.FLOOR);
        //board.infoSlot(new Coordinate(0, 1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(0, 1)).construct(Construction.FLOOR);
    }

    @Test
    public void specialRuleTest() throws Exception {
        Player player = new Player(0, "Pippo", Color.RED);
        player.setCard("HYPNUS");
        Player enemy = new Player(1, "Pluto", Color.BLUE);
        enemy.setCard("PAN");
        board.infoSlot(new Coordinate(2, 0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(2, 0));
        player.selectWorker(new Coordinate(2,0));
        board.infoSlot(new Coordinate(3,3)).occupy(enemy.getWorker(0));
        enemy.getWorker(0).setPosition(new Coordinate(3,3));
        enemy.selectWorker(new Coordinate(3,3));
        board.infoSlot(new Coordinate(0,0)).occupy(enemy.getWorker(1));
        enemy.getWorker(1).setPosition(new Coordinate(0,0));
        assertTrue(card.turnHandler(enemy,board,new Move(new Coordinate(3,3),new Coordinate(2,3))));
        TreeActionNode root = card.cardTreeSetup(board.infoSlot(new Coordinate(2,3)).getWorker(),board);
        TreeActionNode tempW1 = card.cardTreeSetup(board.infoSlot(new Coordinate(0,0)).getWorker(),board);
        TreeActionNode rootplayer = card.cardTreeSetup(board.infoSlot(new Coordinate(2,0)).getWorker(),board);
        //Hypnus modify opponent tree
        for (TreeActionNode t : tempW1.getChildren())
            root.addChild(t);
        assertEquals(9, root.getChildren().size());
        card.specialRule(root,board);
        assertEquals(4, rootplayer.getChildren().size());
        assertEquals(1, root.getChildren().size());
    }
}
