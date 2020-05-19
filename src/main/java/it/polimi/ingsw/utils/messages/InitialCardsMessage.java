package it.polimi.ingsw.utils.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitialCardsMessage implements Message, Serializable {

    private ArrayList<String> completeList;
    private ArrayList<String> selectedList;
    private String message;
    private int id;
    private int dim;


    public InitialCardsMessage(ArrayList<String> list, int id, int dim) {
        this.id = id;
        this.completeList = new ArrayList<>();
        this.selectedList = new ArrayList<>();

        this.completeList.addAll(list);
        this.message = "Select " + dim + " God Cards ";
        this.dim = dim;


    }

    public ArrayList<String> getCompleteList() {
        return completeList;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void addToSelectedList(String selectedGod) {
        this.selectedList.add(selectedGod.toUpperCase());
    }

    public ArrayList<String> getSelectedList() {
        return selectedList;
    }

    public int getDim() {
        return this.dim;
    }

}
