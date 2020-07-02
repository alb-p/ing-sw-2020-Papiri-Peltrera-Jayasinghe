package it.polimi.ingsw.model;

import it.polimi.ingsw.actions.Build;
import it.polimi.ingsw.actions.Move;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;


public class TreeActionNodeTest {

    TreeActionNode node;

    TreeActionNode nodeMove;
    TreeActionNode nodeBuild;


    @Before
    public void init() {
        node = new TreeActionNode(null);
        nodeMove = new TreeActionNode(new Move(new Coordinate(0, 0), new Coordinate(1, 1)));
        nodeBuild = new TreeActionNode(new Build(new Coordinate(1, 1), new Coordinate(0, 0)));
    }

    @Test
    public void addChildTest() {
        assertTrue(node.isLeaf());
        node.addChild(nodeMove);
        node.addChild(nodeBuild);
        assertFalse(node.isLeaf());
        assertTrue(node.getChildren().get(0) != null);
        assertTrue(node.getChild(0).getData().getActionName().equals("move"));
        assertTrue(node.getChild(1).getData().getActionName().equals("build"));


    }

    @Test
    public void getChildrenTest() {
        node.addChild(nodeMove);
        node.addChild(nodeBuild);
        ArrayList<TreeActionNode> list = node.getChildren();
        assertEquals(2, list.size());
        assertEquals("move", list.get(0).getData().getActionName());
        assertEquals("build", list.get(1).getData().getActionName());
    }

    @Test
    public void getParentTest() {
        nodeMove.addChild(nodeBuild);
        node.addChild(nodeMove);
        assertNotNull(nodeBuild.getParent());
        assertTrue(nodeBuild.getParent().getData() instanceof Move);

    }

    @Test
    public void isLeafTest() {
        assertTrue(nodeMove.isLeaf());
        nodeMove.addChild(nodeBuild);
        assertFalse(nodeMove.isLeaf());
    }


}