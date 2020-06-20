package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Action;

import java.io.Serializable;
import java.util.ArrayList;

public class ActionMessage implements Message, Serializable {

    private static final long serialVersionUID = -7780168581217375462L;
    private int id;
    private boolean isOptional;
    private ArrayList<Action> choices;
    private Action action;


    public ActionMessage(ArrayList<Action> list, boolean b, int id) {
        this.choices=list;
        this.isOptional=b;
        this.id =id;
    }

    public ActionMessage() {

    }

    public Action getAction(){
        return this.action;
    }

    public void setAction(Action a){
         this.action=a;
    }

    public ArrayList<Action> getChoices(){
        return this.choices;
    }

    public boolean isOptional(){
        return this.isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
