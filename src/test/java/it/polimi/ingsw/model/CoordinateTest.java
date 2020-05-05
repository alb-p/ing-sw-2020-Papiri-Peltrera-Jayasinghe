package it.polimi.ingsw.model;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CoordinateTest {
    Coordinate c1;
    Coordinate c2;

    @Test
    public void isNotAdjacentTest(){
         c1= new Coordinate(0,0);
         c2= new Coordinate(0,0);
        Assert.assertFalse(c1.isAdjacent(c2));
    }

    @Test
    public void isAdjacentTest(){
         c1= new Coordinate(0,0);
         c2= new Coordinate(0,1);
        Assert.assertTrue(c1.isAdjacent(c2));
    }


    @Test
    public void equalsTest(){
        c1= new Coordinate(0,0);
        c2= new Coordinate(0,0);
        assertTrue(c1.equals(c2));
        c1= new Coordinate();
        c2= new Coordinate();
        assertTrue(c1.equals(c2));
    }


    @Test
    public void notEqualsTest(){
        c1= new Coordinate(0,0);
        c2= new Coordinate(1,0);
        assertFalse(c1.equals(c2));
        c2 = new Coordinate(0,1);
        assertFalse(c1.equals(c2));
        c2 = new Coordinate(1,1);
        assertFalse(c1.equals(c2));
    }

    @Test
    public void toStringTest(){
        c1= new Coordinate(3,2);
        Assert.assertEquals("\nRow :3\nCol :2", c1.toString());
    }

    @Test
    public void adjacentTest() {
        c1 = new Coordinate(4, 4);
        ArrayList<Coordinate> adjacentList = c1.getAdjacentCoords();
        assertFalse(adjacentList.isEmpty());
        assertFalse(new Coordinate(4, 4).equals(adjacentList.get(0)));
        assertTrue(new Coordinate(3, 3).equals(adjacentList.get(0)));
        assertTrue(new Coordinate(4, 3).equals(adjacentList.get(1)));
        assertTrue(new Coordinate(3, 4).equals(adjacentList.get(2)));
        assertFalse(new Coordinate(5, 7).equals(adjacentList.get(0)));
    }

    @Test
    public void isValidTest(){
        c1 = new Coordinate(0,0);
        c2 = new Coordinate(5,5);
        assertTrue(c1.isValid());
        assertFalse(c2.isValid());
        c1 = new Coordinate(4,4);
        assertTrue(c1.isValid());
    }

}
