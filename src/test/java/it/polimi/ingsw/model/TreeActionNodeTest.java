package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreeActionNodeTest {

    TreeActionNode node;

    TreeActionNode nodemove;
    TreeActionNode nodebuild;


    @Before
    public void init(){

        node=new TreeActionNode(null);

        nodemove=new TreeActionNode(new Move(new Coordinate(0,0),new Coordinate(1,1)));
        nodebuild=new TreeActionNode(new Build(new Coordinate(1,1),new Coordinate(0,0)));

    }

    @Test
    void addChild() {

       assertTrue(node.isLeaf());

        node.addChild(nodemove);
        node.addChild(nodebuild);

        assertFalse(node.isLeaf());
        assertTrue(node.getChildren().get(0) != null);
        assertTrue(node.getChild(0).getData().getActionName().equals("move"));
        assertTrue(node.getChild(1).getData().getActionName().equals("build"));


    }

    @Test
    void getChildren() {


        node.addChild(nodemove);
        node.addChild(nodebuild);

        ArrayList<TreeActionNode> list=node.getChildren();
        assertTrue(list.size()==2);
        assertTrue(list.get(0).getData().getActionName().equals("move"));
        assertTrue(list.get(1).getData().getActionName().equals("build"));

    }

    @Test
    void getParent() {

        nodemove.addChild(nodebuild);
        node.addChild(nodemove);

        assertTrue(nodebuild.getParent()!=null);
        assertTrue(nodebuild.getParent().getData() instanceof Move);

    }

    @Test
    void isLeaf() {

        assertTrue(nodemove.isLeaf());
        nodemove.addChild(nodebuild);
        assertFalse(nodemove.isLeaf());




    }


}