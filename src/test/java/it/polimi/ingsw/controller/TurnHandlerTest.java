package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.ActionsEnum;
import it.polimi.ingsw.utils.messages.ActionMessage;
import it.polimi.ingsw.utils.messages.GenericMessage;
import it.polimi.ingsw.utils.messages.Message;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;

import static org.junit.Assert.*;

public class TurnHandlerTest {

    TurnHandler turnHandler;
    TurnHandler turnHandler2;
    TurnHandler turnHandler3;

    Model model = new Model();
    Model model2;
    Model model3;

    @Before
    public void init() {
        Player player = new Player(0, "Pluto", Color.BLUE);
        model.addPlayer(player);
        player = new Player(1, "Paperino", Color.RED);
        model.addPlayer(player);
        model.addWorker(0,new Coordinate(0,0),0);
        model.addWorker(0,new Coordinate(0,4),1);
        model.addWorker(1,new Coordinate(4,0),0);
        model.addWorker(1,new Coordinate(4,4),1);
        model.setCard(0,"Prometheus");
        model.setCard(1,"Atlas");
        turnHandler = new TurnHandler(model, 2);
        turnHandler.setTotalTurnCounter(0);
    }

    @Before
    public void initLocked3PlayerMatch() {
        model2 = new Model();
        Player player = new Player(0, "Pluto", Color.BLUE);
        model2.addPlayer(player);
        player = new Player(1, "Paperino", Color.RED);
        model2.addPlayer(player);
        player = new Player(2, "Pippo", Color.WHITE);
        model2.addPlayer(player);
        model2.addWorker(0,new Coordinate(0,0),0);
        model2.addWorker(0,new Coordinate(0,1),1);
        model2.addWorker(1,new Coordinate(1,0),0);
        model2.addWorker(1,new Coordinate(1,1),1);
        model2.addWorker(2,new Coordinate(1,2),0);
        model2.addWorker(2,new Coordinate(0,2),1);
        model2.setCard(0,"Pan");
        model2.setCard(1,"Atlas");
        model2.setCard(2,"Athena");
        turnHandler2 = new TurnHandler(model2, 3);
        turnHandler2.setTotalTurnCounter(0);
    }

    @Before
    public void initLocked2PlayerMatch() {
        model3 = new Model();
        Player player = new Player(0, "Pluto", Color.BLUE);
        model3.addPlayer(player);
        player = new Player(1, "Paperino", Color.RED);
        model3.addPlayer(player);
        player = new Player(2, "Pippo", Color.WHITE);
        model3.addPlayer(player);
        model3.addWorker(0,new Coordinate(0,0),0);
        model3.addWorker(0,new Coordinate(0,1),1);
        model3.addWorker(1,new Coordinate(1,0),0);
        model3.addWorker(1,new Coordinate(1,1),1);
        model3.addWorker(2,new Coordinate(1,2),0);
        model3.addWorker(2,new Coordinate(0,2),1);
        model3.setCard(0,"Pan");
        model3.setCard(1,"Atlas");
        model3.setCard(2,"Athena");
        turnHandler3 = new TurnHandler(model3, 2);
        turnHandler3.setTotalTurnCounter(0);
    }

    @Test
    public void propertyChangeTest(){

        Message message = new GenericMessage();
        message.setId(0);
        PropertyChangeEvent evt;
        //message.setAction(new Move(new Coordinate(0,0),new Coordinate(0,1)));
        turnHandler.propertyChange(new PropertyChangeEvent(this,"actionsRequest", false,new GenericMessage(0)));

        assertFalse(model.getBoard().infoSlot(new Coordinate(0,0)).isFree());
        assertTrue(model.getBoard().infoSlot(new Coordinate(0,1)).isFree());


        message = new ActionMessage();
        message.setId(0);
        ((ActionMessage)message).setAction(new Move(new Coordinate(0,0),new Coordinate(0,1)));
        evt = new PropertyChangeEvent(this, "notifyAction",null , message);

        turnHandler.propertyChange(evt);

        assertTrue(model.getBoard().infoSlot(new Coordinate(0,0)).isFree());
        assertFalse(model.getBoard().infoSlot(new Coordinate(0,1)).isFree());



        turnHandler.propertyChange(new PropertyChangeEvent(this, "actionsRequest", false,new GenericMessage(0)));
        System.out.println(model.getBoard());

        ((ActionMessage)message).setAction(new Build(new Coordinate(1,1),new Coordinate(0,0)));
        evt = new PropertyChangeEvent(this, "notifyAction",
                null , message);
        turnHandler.propertyChange(evt);

        assertEquals(0,model.getBoard().infoSlot(new Coordinate(0,0)).getConstructionLevel());
        assertEquals(0,turnHandler.actualPlayerID());

        turnHandler.propertyChange(new PropertyChangeEvent(this, "actionsRequest", false,new GenericMessage(0)));

        ((ActionMessage)message).setAction(new Build(new Coordinate(0,1),new Coordinate(1,0)));

        evt = new PropertyChangeEvent(this, "notifyAction",
                null , message);
        turnHandler.propertyChange(evt);

        assertEquals(1,model.getBoard().infoSlot(new Coordinate(1,0)).getConstructionLevel());

        assertEquals(1,turnHandler.actualPlayerID());
        turnHandler.propertyChange(new PropertyChangeEvent(this, "actionsRequest", false,new GenericMessage(0)));

        ((ActionMessage)message).setAction(new Build(new Coordinate(0,1),new Coordinate(1,0)));
        evt = new PropertyChangeEvent(this, "notifyAction", null , message);
        turnHandler.propertyChange(evt);
        assertEquals(0,model.getBoard().infoSlot(new Coordinate(0,0)).getConstructionLevel());


        System.out.println(model.getBoard());



        assertEquals(0,model.getBoard().infoSlot(new Coordinate(0,0)).getConstructionLevel());
        assertEquals(1,turnHandler.actualPlayerID());



    }

    @Test
    public void playerHasLostTest(){
        assertEquals(0, turnHandler2.actualPlayerID());

        turnHandler2.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));
        assertEquals(1, turnHandler2.actualPlayerID());

    }

}
