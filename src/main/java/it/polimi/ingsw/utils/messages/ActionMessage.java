package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.utils.ActionsEnum;

import java.io.Serializable;

public class ActionMessage implements Message, Serializable {

    private String message;
    private String nickname;
    private ActionsEnum actionAvailable;
    private int id;
    private Action action;

    public ActionMessage(){}
    public ActionMessage(int id, ActionsEnum actionAvailable, String nickname){
        this.id = id;
        this.actionAvailable = actionAvailable;
        this.nickname = nickname;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setActionsAvailable(ActionsEnum actionsAvailable){
        this.actionAvailable = actionAvailable;
    }

    public ActionsEnum getActionsAvailable() {
        return actionAvailable;
    }
}
