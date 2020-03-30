package it.polimi.ingsw.model;

public class Floor implements Construction{

    @Override
    public void build(Slot slot) throws Exception {
        slot.construct(this);
    }
}