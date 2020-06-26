package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
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
    TurnHandler turnHandler4;

    Model model = new Model();
    Model model2= new Model();
    Model model3= new Model();
    Model model4= new Model();

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
        model.setCard(0,"Poseidon");
        model.setCard(1,"Atlas");
        turnHandler = new TurnHandler(model, 2);
        turnHandler.setTotalTurnCounter(0);
    }

    @Before
    public void initLocked3PlayerMatch() {
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
    public void initLocked2PlayerMatch() throws Exception {
        Player player = new Player(0, "Pluto", Color.BLUE);
        model3.addPlayer(player);
        player = new Player(1, "Paperino", Color.RED);
        model3.addPlayer(player);

        model3.addWorker(0,new Coordinate(0,0),0);
        model3.addWorker(0,new Coordinate(0,1),1);
        model3.addWorker(1,new Coordinate(1,0),0);
        model3.addWorker(1,new Coordinate(1,1),1);
        model3.getBoard().infoSlot(new Coordinate(1,2)).construct(Construction.DOME);
        model3.getBoard().infoSlot(new Coordinate(0,2)).construct(Construction.DOME);
        model3.setCard(0,"Pan");
        model3.setCard(1,"Atlas");
        turnHandler3 = new TurnHandler(model3, 2);
        turnHandler3.setTotalTurnCounter(1);
    }

    @Before
    public void almostWinningBoard() throws Exception {
        Player player = new Player(0, "Pluto", Color.BLUE);
        model4.addPlayer(player);
        player = new Player(1, "Paperino", Color.RED);
        model4.addPlayer(player);

        model4.getBoard().infoSlot(new Coordinate(0,1)).construct(Construction.FLOOR);
        model4.getBoard().infoSlot(new Coordinate(0,1)).construct(Construction.FLOOR);
        model4.getBoard().infoSlot(new Coordinate(0,1)).construct(Construction.FLOOR);
        model4.getBoard().infoSlot(new Coordinate(0,0)).construct(Construction.FLOOR);
        model4.getBoard().infoSlot(new Coordinate(0,0)).construct(Construction.FLOOR);

        model4.addWorker(0,new Coordinate(0,0),0);
        model4.addWorker(0,new Coordinate(0,3),1);
        model4.addWorker(1,new Coordinate(2,0),0);
        model4.addWorker(1,new Coordinate(1,4),1);
        model4.setCard(0,"Poseidon");
        model4.setCard(1,"Atlas");
        turnHandler4 = new TurnHandler(model4, 2);
        turnHandler4.setTotalTurnCounter(0);


    }

    @Test
    public void propertyChangeTest() {

        Message message = new GenericMessage();
        message.setId(0);
        PropertyChangeEvent evt;
        //message.setAction(new Move(new Coordinate(0,0),new Coordinate(0,1)));
        turnHandler.propertyChange(new PropertyChangeEvent(this, "actionsRequest", false, new GenericMessage(0)));
        assertFalse(model.addWorker(0,new Coordinate(0,4),0));
        assertFalse(model.getBoard().infoSlot(new Coordinate(0, 0)).isFree());
        assertTrue(model.getBoard().infoSlot(new Coordinate(0, 1)).isFree());


        message = new ActionMessage();
        message.setId(0);
        ((ActionMessage) message).setAction(new Move(new Coordinate(0, 0), new Coordinate(0, 1)));
        evt = new PropertyChangeEvent(this, "notifyAction", null, message);

        turnHandler.propertyChange(evt);

        assertTrue(model.getBoard().infoSlot(new Coordinate(0, 0)).isFree());
        assertFalse(model.getBoard().infoSlot(new Coordinate(0, 1)).isFree());


        turnHandler.propertyChange(new PropertyChangeEvent(this, "actionsRequest", false, new GenericMessage(0)));
        System.out.println(model.getBoard());

        ((ActionMessage) message).setAction(new Build(new Coordinate(1, 1), new Coordinate(0, 0)));
        evt = new PropertyChangeEvent(this, "notifyAction",
                null, message);
        turnHandler.propertyChange(evt);

        assertEquals(0, model.getBoard().infoSlot(new Coordinate(0, 0)).getConstructionLevel());
        assertEquals(0, turnHandler.actualPlayerID());

        turnHandler.propertyChange(new PropertyChangeEvent(this, "actionsRequest", false, new GenericMessage(0)));

        ((ActionMessage) message).setAction(new Build(new Coordinate(0, 1), new Coordinate(1, 0)));

        evt = new PropertyChangeEvent(this, "notifyAction",
                null, message);
        turnHandler.propertyChange(evt);

        assertEquals(1, model.getBoard().infoSlot(new Coordinate(1, 0)).getConstructionLevel());
//
        assertTrue(model.getPlayer(0).essentialDone());
        System.out.println(model.getBoard());

        assertEquals(0, turnHandler.actualPlayerID());

        turnHandler.propertyChange(new PropertyChangeEvent(this, "endTurn", null, new GenericMessage(0)));


        assertEquals(1, turnHandler.actualPlayerID());

        turnHandler.propertyChange(new PropertyChangeEvent(this, "playerDisconnected", null, 0));

    }
    @Test
    public void playerDisconnected(){
        turnHandler.propertyChange(new PropertyChangeEvent(this, "playerDisconnected", null,0));
        assertFalse(model.isWinnerDetected());
    }

    @Test
    public void playerHasLostTest3players(){
        assertEquals(0, turnHandler2.actualPlayerID());
        assertFalse(model2.getBoard().infoSlot(new Coordinate(0, 0)).isFree());

        turnHandler2.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));
        assertEquals(1, turnHandler2.actualPlayerID());

        assertTrue(model2.getBoard().infoSlot(new Coordinate(0, 0)).isFree());

    }

    @Test
    public void playerHasLostTest2players(){
        assertEquals(1, turnHandler3.actualPlayerID());
        assertFalse(model2.getBoard().infoSlot(new Coordinate(0, 0)).isFree());
        turnHandler2.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(1)));

        ActionMessage message = new ActionMessage();
        message.setId(1);
        message.setAction(new Move(new Coordinate(1,0),new Coordinate(2,0)));

        turnHandler2.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));

        turnHandler2.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(1)));

        message = new ActionMessage();
        message.setId(1);
        message.setAction(new BuildDome(new Coordinate(2,0),new Coordinate(1,0)));

        turnHandler2.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));

        turnHandler2.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));


        assertEquals(1, turnHandler2.actualPlayerID());
        assertTrue(model2.getBoard().infoSlot(new Coordinate(0, 0)).isFree());

    }

    @Test
    public void winningTurn(){
        assertEquals(0, turnHandler4.actualPlayerID());
        System.out.println(model4.getBoard());
        System.out.println(model4.getPlayer(0).getWorker(0).getPosition());
        System.out.println(model4.getPlayer(0).getWorker(1).getPosition());
        System.out.println(model4.getPlayer(0).getCard());
        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));


        ActionMessage message = new ActionMessage();
        message.setId(0);
        message.setAction(new Move(new Coordinate(0,0),new Coordinate(0,1)));

        turnHandler4.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));

        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));


        message = new ActionMessage();
        message.setId(0);
        message.setAction(new Build(new Coordinate(0,1),new Coordinate(0,0)));

        turnHandler4.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));
        System.out.println(model4.getBoard());
        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));

        assertTrue(model4.isWinnerDetected());

    }


    @Test
    public void normalTurn(){
        assertEquals(0, turnHandler4.actualPlayerID());
        model4.setCard(0,"Atlas");
        System.out.println(model4.getBoard());
        System.out.println(model4.getPlayer(0).getWorker(0).getPosition());
        System.out.println(model4.getPlayer(0).getWorker(1).getPosition());
        System.out.println(model4.getPlayer(0).getCard());
        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));


        ActionMessage message = new ActionMessage();
        message.setId(0);
        message.setAction(new Move(new Coordinate(0,0),new Coordinate(1,1)));

        turnHandler4.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));

        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));


        message = new ActionMessage();
        message.setId(0);
        message.setAction(new Build(new Coordinate(1,1),new Coordinate(0,0)));

        turnHandler4.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));
        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(1)));
        assertEquals(1, turnHandler4.actualPlayerID());

        assertFalse(model4.isWinnerDetected());

    }

    @Test
    public void normalTurnFirstBuild(){
        assertEquals(0, turnHandler4.actualPlayerID());
        model4.setCard(0,"PROMETHEUS");
        System.out.println(model4.getBoard());
        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));
        ActionMessage message = new ActionMessage();
        message.setId(0);
        message.setAction(new FirstBuild(new Coordinate(0,0),new Coordinate(0,1)));
        turnHandler4.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));

        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));
        message = new ActionMessage();
        message.setId(0);
        message.setAction(new Move(new Coordinate(0,0),new Coordinate(1,1)));
        turnHandler4.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));

        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));


        message = new ActionMessage();
        message.setId(0);
        message.setAction(new Build(new Coordinate(1,1),new Coordinate(0,0)));

        turnHandler4.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));
        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(1)));
        assertEquals(1, turnHandler4.actualPlayerID());

        assertFalse(model4.isWinnerDetected());

    }

    @Test
    public void normalWinTurn(){
        assertEquals(0, turnHandler4.actualPlayerID());
        model4.setCard(0,"Atlas");
        System.out.println(model4.getBoard());
        System.out.println(model4.getPlayer(0).getWorker(0).getPosition());
        System.out.println(model4.getPlayer(0).getWorker(1).getPosition());
        System.out.println(model4.getPlayer(0).getCard());
        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));


        ActionMessage message = new ActionMessage();
        message.setId(0);
        message.setAction(new Move(new Coordinate(0,0),new Coordinate(0,1)));

        turnHandler4.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));

        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(0)));


        message = new ActionMessage();
        message.setId(0);
        message.setAction(new BuildDome(new Coordinate(0,1),new Coordinate(0,0)));

        turnHandler4.propertyChange(new PropertyChangeEvent(this, "notifyAction",false ,message));
        System.out.println(model4.getBoard());
        turnHandler4.propertyChange(new PropertyChangeEvent(this,"actionsRequest",null, new GenericMessage(1)));

        assertTrue(model4.isWinnerDetected());

    }

}
