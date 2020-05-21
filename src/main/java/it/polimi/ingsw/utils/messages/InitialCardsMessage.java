package it.polimi.ingsw.utils.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitialCardsMessage implements Message, Serializable {

    private ArrayList<String> selectedList;
    private String message;
    private int id;


    public InitialCardsMessage() {
        this.selectedList = new ArrayList<>();

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


}
