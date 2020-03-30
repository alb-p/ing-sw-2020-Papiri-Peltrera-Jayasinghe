package it.polimi.ingsw.model;

public class Dome implements Construction{

    @Override
    public void build(Slot slot) throws Exception {
        slot.construct(this);
    }
}