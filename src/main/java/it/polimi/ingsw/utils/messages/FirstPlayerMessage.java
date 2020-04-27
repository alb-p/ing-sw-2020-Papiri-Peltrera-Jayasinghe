package it.polimi.ingsw.utils.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FirstPlayerMessage implements Message, Serializable {

    private String message;
    private String chosenName;
    private int id;
    private List<String> names;

    public FirstPlayerMessage(int i, ArrayList<String> list){
        this.id=i;
        this.names=new ArrayList<>();
        this.names.addAll(list);
        this.message="Choose the first player: "+this.names;
    }

    public void setChosenName(String s){
        this.chosenName=s;
    }

    public String getChosenName(){
        return this.chosenName;
    }

    public List<String> getNames() {
        return this.names;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setId(int i) {
        this.id=i;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
