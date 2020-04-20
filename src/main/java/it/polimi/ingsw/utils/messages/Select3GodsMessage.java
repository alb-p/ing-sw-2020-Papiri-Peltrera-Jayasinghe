package it.polimi.ingsw.utils.messages;

import java.util.ArrayList;

public class Select3GodsMessage {

    private ArrayList<String> list;


    private ArrayList<String> selectedList;
    private String message;
    private int id;


    public Select3GodsMessage(ArrayList<String> l, int i){
        this.id=i;
        this.list=l;
        this.message="Select 3 God Cards "+ list;


    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void setSelectedList(ArrayList<String> selectedList) {
        this.selectedList = selectedList;
    }


    public ArrayList<String> getSelectedList() {
        return selectedList;
    }

}
