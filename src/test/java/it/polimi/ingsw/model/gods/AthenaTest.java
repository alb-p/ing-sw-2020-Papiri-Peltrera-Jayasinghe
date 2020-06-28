package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.actions.Move;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AthenaTest {

    BasicGodCard card = new Athena();
    IslandBoard board = new IslandBoard();


    @Before
    public void init() {
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(0, 0)).construct(Construction.FLOOR);

        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 0)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(1, 1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(0, 1)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(0, 1)).construct(Construction.FLOOR);
    }


    @Test
    public void moveAndSpecialRuleTest() {

        Player player = new Player(0, "Pippo", Color.RED);
        player.setCard("ATHENA");
        Player enemy = new Player(1, "Pluto", Color.BLUE);
        player.setCard("PAN");

        //aggiungo un worker mio in 2,0
        board.infoSlot(new Coordinate(2, 0)).occupy(player.getWorker(0));
        player.getWorker(0).setPosition(new Coordinate(2, 0));
        player.selectWorker(new Coordinate(2, 0));
        //muovo il worker in 1,0 per attivare athena
        assertTrue(card.turnHandler(player,board,new Move(new Coordinate(2,0),new Coordinate(1,0))));
        //metto worker avversario in 0,0
        board.infoSlot(new Coordinate(0, 0)).occupy(enemy.getWorker(0));
        enemy.getWorker(0).setPosition(new Coordinate(0, 0));
        //creo albero dell'avversario
        TreeActionNode root= card.cardTreeSetup(board.infoSlot(new Coordinate(0,0)).getWorker(),board);

        //faccio correggere albero avversario da athena
        card.specialRule(root,board);


        assertTrue(root.getChildren().size()==0);





    }
}