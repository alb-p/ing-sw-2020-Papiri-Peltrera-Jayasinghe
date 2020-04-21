package it.polimi.ingsw.utils.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitialCardsMessage {

    private ArrayList<String> completeList;
    private ArrayList<String> selectedList;
    private String message;
    private int id;


    public InitialCardsMessage(ArrayList<String> l, int i, int dim){
        this.id=i;
        this.completeList=l;
        this.message="Select "+dim+" God Cards "+ completeList;


    }

    public ArrayList<String> getCompleteList() {
        return completeList;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }


    public void setSelectedList(ArrayList<String> selectedList) {
        this.selectedList = selectedList;
    }


    public ArrayList<String> getSelectedList() {
        return selectedList;
    }

}
