package it.polimi.ingsw.model;

import it.polimi.ingsw.actions.Action;

import java.util.ArrayList;

/**
 * The type Tree action node represent a node
 * of the tree of the available actions
 */
public class TreeActionNode {


    private final Action data;
    private TreeActionNode parent;
    private final ArrayList<TreeActionNode> children;

    /**
     * Instantiates a new Tree action node.
     *
     * @param data the data
     */
    public TreeActionNode(Action data){
        this.data = data;
        this.children = new ArrayList<>();
    }

    /**
     * Set parent.
     *
     * @param parent the parent
     */
    private void setParent(TreeActionNode parent){
        this.parent  = parent;
    }

    /**
     * Add child.
     *
     * @param child the child
     */
    public void addChild(TreeActionNode child){
        this.children.add(child);
        child.setParent(this);
    }

    /**
     * Get children array list.
     *
     * @return the array list
     */
    public ArrayList<TreeActionNode> getChildren(){
        return this.children;
    }

    /**
     * Get parent tree action node.
     *
     * @return the tree action node
     */
    public TreeActionNode getParent(){
        return this.parent;
    }

    /**
     * Is leaf.
     *
     * @return true if the node has no child
     */
    public boolean isLeaf(){
        return this.children.isEmpty();
    }

    /**
     * Search tree action node.
     *
     * @param action the action wanted to be found
     * @return the tree action node if found, else null
     */
    public TreeActionNode search(Action action){
        for(TreeActionNode t: children){
            if(t.data.equals(action)) return t;
        }
        return null;
    }

    /**
     * Get the actions of the children.
     *
     * @return the array list of the names of the actions available
     */
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


    /**
     * Gets the action.
     *
     * @return the data
     */
    public Action getData() {
        return data;
    }

    /**
     * Gets child.
     *
     * @param i the
     * @return the child
     */
    public TreeActionNode getChild(int i) {
        return this.children.get(i);
    }

    /**
     * Remove child.
     *
     * @param i the
     */
    public void removeChild(int i) {
        this.children.remove(i);
    }


}
