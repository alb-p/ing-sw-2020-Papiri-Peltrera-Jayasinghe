package it.polimi.ingsw.model;

import it.polimi.ingsw.gods.Demeter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


class PlayerTest {

    Player player=new Player("name","BLUE");


    @Test
    void setGodCard() {
        player.setCard("DEMETER");
        Assert.assertTrue(player.getCard() instanceof Demeter);
    }

    @Test
    void getCard() {
        player.setCard("DEMETER");
        Assert.assertTrue(player.getCard() instanceof Demeter);
    }

}