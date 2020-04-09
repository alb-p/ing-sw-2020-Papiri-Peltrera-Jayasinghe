package it.polimi.ingsw.model;


import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void isNotAdjacentTest(){
        Coordinate c1= new Coordinate(0,0);
        Coordinate c2= new Coordinate(0,0);
        Assert.assertFalse(c1.isAdjacent(c2));


    }

    @Test
    public void isAdjacentTest(){
        Coordinate c1= new Coordinate(0,0);
        Coordinate c2= new Coordinate(0,1);
        Assert.assertTrue(c1.isAdjacent(c2));


    }


    @Test
    public void equalsTest(){
        Coordinate c1= new Coordinate(0,0);
        Coordinate c2= new Coordinate(0,0);
        Assert.assertTrue(c1.equals(c2));


    }


    @Test
    public void notEqualsTest(){
        Coordinate c1= new Coordinate(0,0);
        Coordinate c2= new Coordinate(1,0);
        assertFalse(c1.equals(c2));
        c2 = new Coordinate(0,1);
        assertFalse(c1.equals(c2));
        c2 = new Coordinate(1,1);
        assertFalse(c1.equals(c2));


    }

    @Test
    public void toStringTest(){
        Coordinate c1= new Coordinate(3,2);

        Assert.assertEquals("\nRow :3\nCol :2", c1.toString());

    }

}
