package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.GodMessage;
import it.polimi.ingsw.utils.messages.InitialCardsMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class InitSetupTest {

    InitSetup initSetup;
    ArrayList<String> gods = new ArrayList<>();

    @Before
    public void init(){
       initSetup = new InitSetup();

        gods.add("APOLLO");
        gods.add("ARTEMIS");
        gods.add("ATHENA");
        gods.add("ATLAS");
        gods.add("DEMETER");
        gods.add("HEPHAESTUS");
        gods.add("MINOTAUR");
        gods.add("PAN");
        gods.add("PROMETHEUS");
    }
    @Test
    public void usernameTest(){
        assertFalse(initSetup.isInUser("Pippo"));
        NicknameMessage message = new NicknameMessage();
        message.setNickname("Pippo");
        initSetup.setNicknames(message);
        message.setNickname("Pluto");
        assertTrue(initSetup.isInUser("Pippo"));
        initSetup.setNicknames(message);
        assertTrue(initSetup.isInUser("Pluto"));
    }

    @Test
    public void colorsTest(){
        assertTrue(initSetup.isInColor(Color.BLUE));
        ArrayList<Color> colors = new ArrayList<>();
        Collections.addAll(colors, Color.values());
        ColorMessage message = new ColorMessage(0);
        message.setColor(Color.BLUE);
        initSetup.delColor(message);
        assertFalse(initSetup.isInColor(Color.BLUE));
    }

    @Test
    public void godsTest(){
        assertFalse(initSetup.isInGod("Pippo"));
        assertFalse(initSetup.isInGod("atlas"));
        assertTrue(initSetup.isInListGod("atlas"));
        InitialCardsMessage message = new InitialCardsMessage();
        message.addToSelectedList("Atlas");
        message.addToSelectedList("Pan");
        message.addToSelectedList("Minotaur");
        initSetup.addChosenGod("atlas",message);
        initSetup.addChosenGod("pan",message);
        initSetup.addChosenGod("minotaur",message);
        GodMessage mess1 = new GodMessage();
        mess1.setGod("atlas");
        assertTrue(initSetup.isInGod("atlas"));
        initSetup.delGod(mess1);
        assertFalse(initSetup.isInGod("atlas"));
        assertTrue(initSetup.isInGod("pan"));
        GodMessage mess2 = new GodMessage();
        mess2.setGod("pan");
        initSetup.delGod(mess2);
        assertFalse(initSetup.isInGod("pan"));


    }
}
