package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.utils.ActionsEnum;

import java.io.Serializable;
import java.util.ArrayList;

public class ActionMessage implements Message, Serializable {

    private int ID;
    private boolean isOptional;
    private ArrayList<Action> choices;
    private Action action;


    public ActionMessage(ArrayList<Action> list, boolean b, int i) {
        this.choices=list;
        this.isOptional=b;
        this.ID=i;
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

    public boolean getIsOptional(){
        return this.isOptional;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return this.ID;
    }
}
