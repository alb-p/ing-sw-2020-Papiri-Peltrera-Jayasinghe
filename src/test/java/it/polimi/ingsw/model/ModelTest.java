package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModelTest {

    Model model = new Model();

    @Before
    public void init() throws CloneNotSupportedException {

        Player player = new Player(0,"Pippo", Color.BLUE);
        model.addPlayer(player);
        model.setCard(0, "Atlas");
        player= new Player(1,"Pluto", Color.GRAY);
        model.addPlayer(player);
        model.setCard(1,"Pan");



    }

    @Test
    public void addWorkerTest(){
        assertTrue(model.addWorker(0, new Coordinate(0,0),0));
        assertFalse(model.addWorker(1, new Coordinate(0,0),0));

    }
    @Test
    public void getPlayerTest(){
        assertEquals(0,model.getPlayer(0).getId());
        assertNull(model.getPlayer(2));
    }

    @Test
    public void TurnHandlerTest(){
        model.addWorker(0, new Coordinate(0,0),0);
        model.addWorker(0, new Coordinate(2,0),1);
        model.buildPlayerTree(0);
        model.turnHandler(0, new Move(new Coordinate(0,0),new Coordinate(0,1)));
        assertFalse(model.checkWinner(0));
    }

}
