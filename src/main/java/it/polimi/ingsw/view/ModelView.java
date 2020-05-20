package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.VirtualBoard;

import java.util.ArrayList;

public class ModelView {

    private VirtualBoard board;
    private ArrayList<Color> colors = new ArrayList<>();
    private ArrayList<String[]> gods = new ArrayList<>();



    public VirtualBoard getBoard(){return board;}



}
