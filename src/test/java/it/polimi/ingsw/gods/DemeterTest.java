package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.ActionsEnum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DemeterTest {

    BasicGodCard card = new Demeter();
    IslandBoard board = new IslandBoard();

    @Before
    public void init() throws Exception {
        board.infoSlot(new Coordinate(1, 0)).occupy(new Worker(new Coordinate(1, 0), Color.RED));
        board.infoSlot(new Coordinate(1, 3)).occupy(new Worker(new Coordinate(1, 3), Color.RED));
        board.infoSlot(new Coordinate(0, 1)).occupy(new Worker(new Coordinate(0, 1), Color.WHITE));
        board.infoSlot(new Coordinate(2, 0)).construct(Construction.DOME);
        board.infoSlot(new Coordinate(1, 1)).construct(Construction.DOME);
    }


    @Test
    public void cardTreeSetupTest() {
        TreeActionNode root= card.cardTreeSetup(board.infoSlot(new Coordinate(1,3)).getWorker(),board);
        assertTrue(root.getChildrenActions().size()==1);
        assertTrue(root.getChildren().get(0).getChildrenActions().size()==1);
        assertTrue(root.getChildren().get(0).getChildren().get(0).getChildren().get(0).isLeaf());
        assertTrue(root.getChildren().get(0).getChildren().get(0).getChildrenActions().size()==1);
        TreeActionNode root1= card.cardTreeSetup(board.infoSlot(new Coordinate(1,0)).getWorker(),board);
        assertTrue(root1.getChildren().size()==2);
        assertTrue(root.getChildren().get(0).getChildrenActions().size()==1);
        assertTrue(root.getChildren().get(0).getChildrenActions().get(0).getActionName().equalsIgnoreCase("build"));
    }



}