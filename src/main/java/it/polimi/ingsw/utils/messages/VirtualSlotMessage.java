package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.VirtualSlot;

import java.io.Serializable;

public class VirtualSlotMessage implements Message, Serializable {
    private VirtualSlot virtualSlot;

    public VirtualSlotMessage(VirtualSlot virtualSlot) {
        this.virtualSlot = virtualSlot;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return 0;
    }

    public VirtualSlot getVirtualSlot() {
        return virtualSlot;
    }
}
