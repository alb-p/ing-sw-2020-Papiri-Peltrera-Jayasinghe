package it.polimi.ingsw.model;

import it.polimi.ingsw.actions.Move;
import it.polimi.ingsw.model.gods.Atlas;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModelTest {

    Model model;

    @Before
    public void init() {
        model = new Model();
        Player player = new Player(0,"Pippo", Color.BLUE);
        model.addPlayer(player);
        model.setCard(0, "Atlas");
        player= new Player(1,"Pluto", Color.RED);
        model.addPlayer(player);
        model.setCard(1,"Prometheus");

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
        model.buildTree(0);
        model.turnHandler(0, new Move(new Coordinate(0,0),new Coordinate(0,1)));
        assertFalse(model.checkWinner(0));
    }

    //It will produce SEVERE: null
    @Test
    public void selectChoiceTest() {
        assertTrue(model.addWorker(0, new Coordinate(0,0),0));
        assertTrue(model.addWorker(0, new Coordinate(2,0),1));
        assertTrue(model.getPlayer(0).getCard() instanceof Atlas);
        model.turnHandler(0,new Move(new Coordinate(0,0), new Coordinate(0,1)));
        assertFalse(model.getPlayer(0).hasDone());
        model.getPlayer(0).selectWorker(new Coordinate(0,1));
    }


}
