package it.polimi.ingsw.model;

import it.polimi.ingsw.gods.Demeter;
import org.junit.Assert;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player=new Player("name","BLUE");


    @org.junit.jupiter.api.Test
    void setGodCard() {
        player.setCard("DEMETER");
        Assert.assertTrue(player.getCard() instanceof Demeter);
    }

    @org.junit.jupiter.api.Test
    void getCard() {
        player.setCard("DEMETER");
        Assert.assertTrue(player.getCard() instanceof Demeter);
    }

}