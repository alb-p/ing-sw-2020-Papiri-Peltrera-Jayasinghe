package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class WorkerTest {
    @Test
    public void getPositionTest() {
        Worker worker = new Worker(3, 3, "WHITE");
        assertEquals(true, new Coordinate(3, 3).equals(worker.getPosition()));
        assertEquals(new Coordinate(3, 3).getRow(), worker.getPosition().getRow());
        assertEquals(new Coordinate(3, 3).getCol(), worker.getPosition().getCol());
    }

    @Test
    public void getOldPositionTest() {
        Worker worker = new Worker(3, 3, "WHITE");
        worker.setPosition(new Coordinate(0, 0));
        assertEquals(new Coordinate(3, 3).getRow(), worker.getOldPosition().getRow());
        assertEquals(new Coordinate(3, 3).getCol(), worker.getOldPosition().getCol());
    }

    @Test
    public void getColorTest() {
        Worker worker = new Worker(0, 0, "WHITE");

        assertEquals(Color.WHITE, worker.getColor());
    }

}
