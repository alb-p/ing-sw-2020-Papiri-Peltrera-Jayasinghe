package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PoseidonTest {
    BasicGodCard card = new Poseidon();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() throws Exception {
        board.infoSlot(new Coordinate(0, 0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(4, 4)).construct(Construction.FLOOR);
        board.infoSlot(new Coordinate(4, 3)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(3, 4)).construct(Construction.DOME);
    }


}