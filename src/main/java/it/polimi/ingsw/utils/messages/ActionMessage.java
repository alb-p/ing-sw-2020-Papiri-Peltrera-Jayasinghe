package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.actions.Action;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Action message is a message
 *  * that vehicles actions information..
 */
public class ActionMessage implements Message, Serializable {

    private static final long serialVersionUID = -7780168581217375462L;
    private int id;
    private boolean isOptional;
    private ArrayList<Action> choices;
    private Action action;


    /**
     * Instantiates a new Action message.
     *
     * @param list the list of the available actions
     * @param b    the possibility of end turn without doing any action
     * @param id   the id of the playing player
     */
    public ActionMessage(ArrayList<Action> list, boolean b, int id) {
        this.choices=list;
        this.isOptional=b;
        this.id =id;
    }

    /**
     * Instantiates a new Action message.
     */
    public ActionMessage() {

    }

    /**
     * Get action action.
     *
     * @return the action
     */
    public Action getAction(){
        return this.action;
    }

    /**
     * Set action.
     *
     * @param a the a
     */
    public void setAction(Action a){
         this.action=a;
    }

    /**
     * Get choices array list.
     *
     * @return the array list of the available actions
     */
    public ArrayList<Action> getChoices(){
        return this.choices;
    }

    /**
     * Is optional boolean.
     *
     * @return the boolean
     */
    public boolean isOptional(){
        return this.isOptional;
    }

    /**
     * Sets optional.
     *
     * @param optional true if the actions are optionals
     */
    public void setOptional(boolean optional) {
        isOptional = optional;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    @Override
    public String getMessage() {
        return null;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Override
    public int getId() {
        return this.id;
    }
}
