package it.polimi.ingsw.model;

import java.util.ArrayList;

public class TreeActionNode {


    private Action data;
    private TreeActionNode parent;
    private ArrayList<TreeActionNode> children;

    public TreeActionNode(Action data){
        this.data = data;
        this.children = new ArrayList<TreeActionNode>();
    }

    private void setParent(TreeActionNode parent){
        this.parent  = parent;
    }

    public void addChild(TreeActionNode child){
        this.children.add(child);
        child.setParent(this);
    }

    public ArrayList<TreeActionNode> getChildren(){
        return this.children;
    }

    public TreeActionNode getParent(){
        return this.parent;
    }

    public boolean isLeaf(){
        return this.children.isEmpty();
    }

    public TreeActionNode search(Action action){
        for(TreeActionNode t: children){
            if(t.data.equals(action)) return t;
        }
        return null;
    }

    public Action getData() {
        return data;
    }
}
