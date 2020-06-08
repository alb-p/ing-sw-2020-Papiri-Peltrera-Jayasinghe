package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.ActionsEnum;
import it.polimi.ingsw.utils.messages.ActionMessage;
import it.polimi.ingsw.utils.messages.ChoiceMessage;
import it.polimi.ingsw.utils.messages.Message;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;

import static org.junit.Assert.*;

public class TurnHandlerTest {

    TurnHandler turnHandler;
    TurnHandler turnHandler2;
    TurnHandler turnHandler3;
/*
    @Before
    public void init() throws CloneNotSupportedException {
        Model model = new Model();
        Player player = new Player(0, "Pluto", Color.BLUE);
        model.addPlayer(player);
        player = new Player(1, "Paperino", Color.GRAY);
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
    public void initLocked3PlayerMatch() throws CloneNotSupportedException {
        Model model = new Model();
        Player player = new Player(0, "Pluto", Color.BLUE);
        model.addPlayer(player);
        player = new Player(1, "Paperino", Color.GRAY);
        model.addPlayer(player);
        player = new Player(2, "Pippo", Color.WHITE);
        model.addPlayer(player);
        model.addWorker(0,new Coordinate(0,0),0);
        model.addWorker(0,new Coordinate(0,1),1);
        model.addWorker(1,new Coordinate(1,0),0);
        model.addWorker(1,new Coordinate(1,1),1);
        model.addWorker(2,new Coordinate(1,2),0);
        model.addWorker(2,new Coordinate(0,2),1);
        model.setCard(0,"Pan");
        model.setCard(1,"Atlas");
        model.setCard(2,"Athena");
        turnHandler2 = new TurnHandler(model, 3);
        turnHandler2.setTotalTurnCounter(0);
    }

    @Before
    public void initLocked2PlayerMatch() throws CloneNotSupportedException {
        Model model = new Model();
        Player player = new Player(0, "Pluto", Color.BLUE);
        model.addPlayer(player);
        player = new Player(1, "Paperino", Color.GRAY);
        model.addPlayer(player);
        player = new Player(2, "Pippo", Color.WHITE);
        model.addPlayer(player);
        model.addWorker(0,new Coordinate(0,0),0);
        model.addWorker(0,new Coordinate(0,1),1);
        model.addWorker(1,new Coordinate(1,0),0);
        model.addWorker(1,new Coordinate(1,1),1);
        model.addWorker(2,new Coordinate(1,2),0);
        model.addWorker(2,new Coordinate(0,2),1);
        model.setCard(0,"Pan");
        model.setCard(1,"Atlas");
        model.setCard(2,"Athena");
        turnHandler3 = new TurnHandler(model, 2);
        turnHandler3.setTotalTurnCounter(0);
    }

    @Test
    public void propertyChangeTest(){
        ActionMessage message;
        message = new ActionMessage(0, "Pluto");
        message.setAction(new Move(new Coordinate(0,0),new Coordinate(0,1)));
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "gameReadyResponse",
                null , message);

        turnHandler.propertyChange(evt);

        evt = new PropertyChangeEvent(this, "actionMessageResponse",
                null , message);

        turnHandler.propertyChange(evt);
        assertEquals(0,turnHandler.actualPlayer());

        message.setAction(new Build(new Coordinate(1,1),new Coordinate(0,0)));
        evt = new PropertyChangeEvent(this, "actionMessageResponse",
                null , message);
        turnHandler.propertyChange(evt);

        assertEquals(0,turnHandler.actualPlayer());

        message.setAction(new Build(new Coordinate(0,1),new Coordinate(4,0)));
        evt = new PropertyChangeEvent(this, "actionMessageResponse",
                null , message);
        turnHandler.propertyChange(evt);

        assertEquals(0,turnHandler.actualPlayer());


        message.setAction(new Build(new Coordinate(0,1),new Coordinate(0,0)));

        evt = new PropertyChangeEvent(this, "actionMessageResponse",
                null , message);
        turnHandler.propertyChange(evt);

        assertEquals(1,turnHandler.actualPlayer());

    }

    @Test
    public void playerHasLostTest(){
        turnHandler2.propertyChange(new PropertyChangeEvent(this,"gameReadyResponse",null, true));
        assertEquals(1, turnHandler2.actualPlayer());

        turnHandler3.propertyChange(new PropertyChangeEvent(this,"gameReadyResponse",null, true));
    }

    @Test
    public void propertyChangeChoiceTest() throws CloneNotSupportedException {
        init();
        turnHandler.setTotalTurnCounter(0);
        ActionMessage message = new ActionMessage();
        message.addChoice("First build");
        message.addChoice("MOVE");
        message.setId(0);
        message.setActionsAvailable(ActionsEnum.BOTH);
        ChoiceMessage mess = new ChoiceMessage(message);
        mess.setMessage("First build");
        assertEquals(0, turnHandler.actualPlayer());
        turnHandler.propertyChange(new PropertyChangeEvent(this, "gameReadyResponse",null, true));

        turnHandler.propertyChange(new PropertyChangeEvent(this, "choiceResponse",null, mess));

    }

*/
}
