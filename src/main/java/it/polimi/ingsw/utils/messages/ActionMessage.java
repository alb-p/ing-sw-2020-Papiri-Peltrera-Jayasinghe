package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.Action;

public class ActionMessage implements Message {

    private String message; //MOVE OR BUILD
    private int id;
    private Action action;

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

    @Override
    public String toString() {
        return message;
    }
}
