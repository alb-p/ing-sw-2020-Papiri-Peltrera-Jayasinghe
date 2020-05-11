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
    public ArrayList<Action> getChildrenActions(){
        ArrayList<Action> choices = new ArrayList<>();
        boolean found;
        found = false;
        for (TreeActionNode t : this.children) {
            String choice;
            choice = t.getData().getActionName();
            for (int i = 0; i < choices.size(); i++) {
                Action a = choices.get(i);
                if (a.getActionName().equalsIgnoreCase(choice)) found = true;
            }
            if (!found) choices.add(t.getData());
            found = false;
        }
        for(Action a : choices) System.out.println("CHOICES:: "+a.getActionName());
        return choices;
    }


    public Action getData() {
        return data;
    }

    public boolean isInChoices(String choice){
        for (TreeActionNode s : children){
            System.out.print("S:: "+s.getData().getActionName());
            System.out.println("\tC:: "+choice);
            if(s.getData().getActionName().equalsIgnoreCase(choice))return true;
        }
        return false;
    }

    public TreeActionNode getChild(int i) {
        return this.children.get(i);
    }

    public void removeChild(int i) {
        this.children.remove(i);
    }


}
