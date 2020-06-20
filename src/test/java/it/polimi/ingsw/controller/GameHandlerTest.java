package it.polimi.ingsw.controller;

import it.polimi.ingsw.gods.Pan;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.messages.*;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameHandlerTest {

    InitSetup initSetup;
    Model model;
    int playersNum;
    GameHandler gameHandler;
    NicknameMessage nicknameMessage;
    ColorMessage colorMessage;
    GodMessage godMessage;
    InitialCardsMessage initialCardsMessage;
    WorkerMessage workerMessage;
    ArrayList<Color> colors;
    ArrayList<String> gods;

    TurnHandler turnHandler;

    @Before
    public void init() {
        playersNum = 2;
        model = new Model();
        initSetup = new InitSetup();
        colors = new ArrayList<>();
        colors.add(Color.WHITE);
        colors.add(Color.BLUE);
        turnHandler = new TurnHandler(model, playersNum);
        gods = new ArrayList<>();
        gameHandler = new GameHandler(initSetup, model, playersNum);
        gameHandler.setTurnHandler(turnHandler);
    }

    @Test
    public void propertyChangeTest() {
        nicknameMessage = new NicknameMessage(0);
        nicknameMessage.setNickname("Pippo");
        colorMessage = new ColorMessage(0);
        colorMessage.setColor(Color.WHITE);


        assertFalse(initSetup.isInUser("Pippo"));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyNickname", null, nicknameMessage));
        assertTrue(initSetup.isInUser("Pippo"));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyNickname", null, nicknameMessage));
        assertTrue(initSetup.isInUser("Pippo"));


        nicknameMessage = new NicknameMessage(1);
        nicknameMessage.setNickname("Pluto");

        assertFalse(initSetup.isInUser("Pluto"));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyNickname", null, nicknameMessage));
        assertTrue(initSetup.isInUser("Pluto"));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyNickname", null, nicknameMessage));
        assertTrue(initSetup.isInUser("Pluto"));





        assertTrue(initSetup.isInColor(Color.WHITE));
        assertTrue(initSetup.isInColor(Color.BLUE));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyColor", null, colorMessage));
        assertFalse(initSetup.isInColor(Color.WHITE));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyColor", null, colorMessage));
        assertFalse(initSetup.isInColor(Color.WHITE));

        colorMessage = new ColorMessage(1);
        colorMessage.setColor(Color.BLUE);
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyColor", null, colorMessage));
        assertFalse(initSetup.isInColor(Color.BLUE));



        initialCardsMessage = new InitialCardsMessage();
        initialCardsMessage.addToSelectedList("PAN");
        initialCardsMessage.setId(gameHandler.getCurrentPlayerID()%playersNum);
        assertTrue(initSetup.isInListGod("PAN"));
        assertEquals(0, initSetup.chosenGodsSize());
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notify1ofNGod", null, initialCardsMessage));
        assertFalse(initSetup.isInListGod("PAN"));
        assertEquals(1, initSetup.chosenGodsSize());
        initialCardsMessage.addToSelectedList("APOLLO");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notify1ofNGod", null, initialCardsMessage));
        assertEquals(2, initSetup.chosenGodsSize());
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notify1ofNGod", null, initialCardsMessage));
        assertEquals(2, initSetup.chosenGodsSize());



        godMessage = new GodMessage();
        godMessage.setGod("PAN");
        godMessage.setId(gameHandler.getCurrentPlayerID()%playersNum);
        assertTrue(initSetup.isInGod("PAN"));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyGod", null, godMessage));
        assertFalse(initSetup.isInGod("PAN"));
        godMessage.setGod("APOLLO");
        godMessage.setId(gameHandler.getCurrentPlayerID()%playersNum);
        assertTrue(initSetup.isInGod("APOLLO"));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyGod", null, godMessage));
        assertFalse(initSetup.isInGod("APOLLO"));



        NicknameMessage m1=new NicknameMessage((gameHandler.getCurrentPlayerID()-1)%playersNum,"Pluto");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "firstPlayerSelected", null, m1));
        assertEquals(1, gameHandler.getCurrentPlayerID());


        assertTrue(model.getBoard().infoSlot(new Coordinate(1,1)).isFree());
        workerMessage = new WorkerMessage(gameHandler.getCurrentPlayerID()%playersNum, 0);
        workerMessage.setCoordinate(new Coordinate(1,1));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyWorker", null, workerMessage));
        assertFalse(model.getBoard().infoSlot(new Coordinate(1,1)).isFree());
        assertTrue(model.getBoard().infoSlot(new Coordinate(1,0)).isFree());
        workerMessage = new WorkerMessage(gameHandler.getCurrentPlayerID()%playersNum, 1);
        workerMessage.setCoordinate(new Coordinate(1,0));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "notifyWorker", null, workerMessage));
        assertFalse(model.getBoard().infoSlot(new Coordinate(1,0)).isFree());


    }
}
