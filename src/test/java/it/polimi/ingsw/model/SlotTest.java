package it.polimi.ingsw.model;


import it.polimi.ingsw.utils.Coordinate;
import org.junit.Assert;
import org.junit.Test;


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

    @Test
    public void occupyTest() throws Exception {
        Slot s = new Slot();
        s.occupy(new Worker(3,4, "WHITE"));
        Assert.assertFalse(s.isFree());
    }

    @Test
    public void freeTest() throws Exception {
        Coordinate c = new Coordinate(2,3);
        Slot s = new Slot();
        Worker w = new Worker(c, "BLUE");
        s.occupy(w);
        s.free();
        Assert.assertTrue(s.isFree());
    }

}