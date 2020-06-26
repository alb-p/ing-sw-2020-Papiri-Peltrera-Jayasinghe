package it.polimi.ingsw.utils.messages;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Initial cards message is a message
 *  * that vehicles the list of the gods
 *  selected by the godly player.
 */
public class InitialCardsMessage implements Message, Serializable {

    private static final long serialVersionUID = -5566840779833893228L;
    private final ArrayList<String> selectedList;
    private int id;


    /**
     * Instantiates a new Initial cards message.
     */
    public InitialCardsMessage() {
        this.selectedList = new ArrayList<>();

    }

    /**
     * @return null
     */
    public String getMessage() {
        return null;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }


    /**
     * Add to selected list.
     *
     * @param selectedGod the selected god
     */
    public void addToSelectedList(String selectedGod) {
        this.selectedList.add(selectedGod.toUpperCase());
    }

    /**
     * Gets selected list.
     *
     * @return the selected list
     */
    public ArrayList<String> getSelectedList() {
        return selectedList;
    }


}
