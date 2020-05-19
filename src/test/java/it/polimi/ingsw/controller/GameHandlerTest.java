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
    FirstPlayerMessage firstPlayerMessage;
    WorkerMessage workerMessage;
    ArrayList<Color> colors;
    ArrayList<String> gods;
    ArrayList<String> nickname;
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
        nicknameMessage.setNick("Pippo");
        colorMessage = new ColorMessage(0, colors);
        colorMessage.setColor(Color.WHITE);
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "nickMessageResponse", null, nicknameMessage));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "nickMessageResponse", null, nicknameMessage));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "colorMessageResponse", null, colorMessage));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "colorMessageResponse", null, colorMessage));
        assertEquals("Pippo", model.getPlayer(0).getNickName());
        assertEquals(Color.WHITE, model.getPlayer(0).getWorker(0).getColor());

        nicknameMessage = new NicknameMessage(1);
        nicknameMessage.setNick("Pluto");
        colorMessage = new ColorMessage(0, colors);
        colorMessage.setColor(Color.BLUE);
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "nickMessageResponse", null, nicknameMessage));
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "colorMessageResponse", null, colorMessage));
        gods.add("PAN");
        initialCardsMessage = new InitialCardsMessage(gods, 0, playersNum);
        initialCardsMessage.addToSelectedList("PAN");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "initialCardsResponse", null, initialCardsMessage));
        gods.add("APOLLO");
        initialCardsMessage.addToSelectedList("APOLLO");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "initialCardsResponse", null, initialCardsMessage));
        godMessage = new GodMessage(0, gods);
        godMessage.setGod("POTTER");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "godMessageResponse", null, godMessage));
        godMessage.setGod("PAN");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "godMessageResponse", null, godMessage));
        assertTrue(model.getPlayer(0).getCard() instanceof Pan);
        nickname = new ArrayList<>();
        initSetup.setUsername("Pippo");
        initSetup.setUsername("Pluto");
        nickname.add("Pippo");
        nickname.add("Pluto");
        firstPlayerMessage = new FirstPlayerMessage(0, nickname);
        firstPlayerMessage.setChosenName("Plutonio");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "firstPlayerResponse", null, firstPlayerMessage));
        firstPlayerMessage.setChosenName("Pluto");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "firstPlayerResponse", null, firstPlayerMessage));
        workerMessage = new WorkerMessage(1, 0);
        workerMessage.setCoordinate(new Coordinate(1,1));

    }

    @Test
    public void godTest(){
        Player player = new Player(0, "Pippo", Color.WHITE);
        model.addPlayer(player);
        nickname = new ArrayList<>();
        initSetup.setUsername("Pippo");
        initSetup.setUsername("Pluto");
        nickname.add("Pippo");
        nickname.add("Pluto");

        gods.add("PAN");
        initialCardsMessage = new InitialCardsMessage(gods, 0, playersNum);
        initialCardsMessage.addToSelectedList("PAN");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "initialCardsResponse", null, initialCardsMessage));
        gods.add("APOLLO");
        initialCardsMessage.addToSelectedList("APOLLO");
        initialCardsMessage = new InitialCardsMessage(gods, 0, playersNum);
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "initialCardsResponse", null, initialCardsMessage));
        godMessage = new GodMessage(0, gods);
        godMessage.setGod("POTTER");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "godMessageResponse", null, godMessage));
        godMessage.setGod("PAN");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "godMessageResponse", null, godMessage));
        assertTrue(model.getPlayer(0).getCard() instanceof Pan);
        firstPlayerMessage = new FirstPlayerMessage(0, nickname);
        firstPlayerMessage.setChosenName("Plutonio");
        gameHandler.propertyChange(new PropertyChangeEvent(this,
                "firstPlayerResponse", null, firstPlayerMessage));


    }
}
