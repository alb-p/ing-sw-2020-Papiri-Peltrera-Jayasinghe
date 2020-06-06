package it.polimi.ingsw.model;

import java.util.ArrayList;

public class TreeActionNode {


    private final Action data;
    private TreeActionNode parent;
    private final ArrayList<TreeActionNode> children;

    public TreeActionNode(Action data){
        this.data = data;
        this.children = new ArrayList<>();
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
    public ArrayList<Action> getChildrenActions(){
        ArrayList<Action> choices = new ArrayList<>();
        boolean found;
        found = false;
        for (TreeActionNode t : this.children) {
            String choice;
            choice = t.getData().getActionName();
            for (int i = 0; i < choices.size(); i++) {
                Action a = choices.get(i);
                if (a.getActionName().equalsIgnoreCase(choice)) {
                    found = true;
                    break;
                }
            }
            if (!found) choices.add(t.getData());
            found = false;
        }
        return choices;
    }


    public Action getData() {
        return data;
    }

    public TreeActionNode getChild(int i) {
        return this.children.get(i);
    }

    public void removeChild(int i) {
        this.children.remove(i);
    }


}
