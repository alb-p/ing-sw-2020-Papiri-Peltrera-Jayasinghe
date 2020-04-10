package it.polimi.ingsw.model;


import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SlotTest {



    @Test
    public void constructionOneDomeTest() throws Exception {
        Slot s= new Slot();
        s.construct(Construction.FLOOR);
        //testo che costruita una cosa quella cella non è più libera
        Assert.assertEquals(1, s.getConstructionLevel());
        s.construct(Construction.FLOOR);
        Assert.assertEquals(2,s.getConstructionLevel());
        s.construct(Construction.FLOOR);
        Assert.assertEquals(3,s.getConstructionLevel());
        s.construct(Construction.DOME);
        Assert.assertEquals(3,s.getConstructionLevel());
    }
/*
    @Test
    public void constructionTwoDomeTest() throws Exception{
        Slot s = new Slot();
        s.construct(Construction.DOME);
        Assert.assertEquals(0, s.getConstructionLevel());

        Exception exception = assertThrows(Exception.class, () -> s.construct(Construction.DOME));
        assertEquals("Invalid construction error", exception.getMessage());
    }
*/
    @Test
    public void hasADomeTest() throws Exception {
        Slot s = new Slot();
        if(s.isFree()){
            Assert.assertFalse(s.hasADome());
        }
        s.construct(Construction.DOME);
        Assert.assertTrue(s.hasADome());
        s = new Slot();
        s.construct(Construction.FLOOR);
        Assert.assertFalse(s.hasADome());

    }
}