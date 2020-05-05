package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.utils.ActionsEnum;

import java.io.Serializable;
import java.util.ArrayList;

public class ActionMessage implements Message, Serializable {

    private String message;
    private String nickname;
    private ActionsEnum actionAvailable;
    private int id;
    private Action action;
    private ArrayList<String> choices = new ArrayList<>();

    public ActionMessage() {
    }

    public ActionMessage(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public ActionMessage(int id, ActionsEnum actionAvailable, String nickname) {
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

    public String getNickname() {
        return nickname;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public ArrayList<String> getChoices() {
        return this.choices;
    }

    public void addChoice(String choice) {
        this.choices.add(choice);
    }

    public void setActionsAvailable(ActionsEnum actionsAvailable) {
        this.actionAvailable = actionsAvailable;
    }

    public String getActionsAvailableName() {
        return this.actionAvailable.getName();
    }

    public ActionsEnum getActionsAvailable() {
        return this.actionAvailable;
    }
}
