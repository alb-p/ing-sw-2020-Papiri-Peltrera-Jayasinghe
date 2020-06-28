package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.messages.*;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;


/**
 * The type Init setup.
 */
public class InitSetup {

    private ArrayList<Color> colors;
    private ArrayList<String> nicknames;
    private ArrayList<String> gods;
    private ArrayList<String> chosenGods;
    private PropertyChangeSupport initSetupListeners = new PropertyChangeSupport(this);

    /**
     * Instantiates a new Init setup.
     */
    public InitSetup() {
        colors = new ArrayList<>();
        nicknames = new ArrayList<>();
        gods = new ArrayList<>();
        chosenGods = new ArrayList<>();


        Collections.addAll(colors, Color.values());

        gods.add("APOLLO");
        gods.add("ARTEMIS");
        gods.add("ATHENA");
        gods.add("ATLAS");
        gods.add("CHRONUS");
        gods.add("DEMETER");
        gods.add("HEPHAESTUS");
        gods.add("HESTIA");
        gods.add("HYPNUS");
        gods.add("MINOTAUR");
        gods.add("PAN");
        gods.add("POSEIDON");
        gods.add("PROMETHEUS");
        gods.add("ZEUS");
    }

    /**
     * Add init setup listener.
     *
     * @param listener the listener
     */
    public void addInitSetupListener(PropertyChangeListener listener) {
        initSetupListeners.addPropertyChangeListener(listener);
    }

    /**
     * Validate a nickname sent by the players.
     *
     * @param name the name
     * @return the boolean
     */
    public boolean isInUser(String name) {

        for (String s : nicknames) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets nicknames.
     *
     * @param message the message
     */
    public void setNicknames(NicknameMessage message) {
        nicknames.add(message.getNickname());
        initSetupListeners.firePropertyChange("nicknameConfirm", null, message);
    }


    /**
     * Check if the selected color
     * is still available.
     *
     * @param color the color
     * @return the boolean
     */
    public boolean isInColor(Color color) {

        return colors.contains(color);
    }


    /**
     *  Removes the color from the available ones.
     *
     * @param mess the mess
     */
    public void delColor(ColorMessage mess) {

        for (int i = 0; i < this.colors.size(); i++)
            if (this.colors.get(i) == mess.getColor())
                this.colors.remove(i);

        initSetupListeners.firePropertyChange("colorConfirm", null, mess);

    }

    /**
     * Notifies the view that the godly
     * player has been selected (randomly).
     *
     * @param godlyID the godly id
     */
//avvisa chi è il godly
    public void notifyGodly(int godlyID) {
        GodlyMessage message = new GodlyMessage(godlyID);
        initSetupListeners.firePropertyChange("godlySelected", false, message);

    }


    /**
     * Add selected god to the list of the selected gods.
     *
     * @param chosenGod the chosen god
     * @param mess      the mess
     */
    public void addChosenGod(String chosenGod, InitialCardsMessage mess) {
        this.chosenGods.add(chosenGod);
        System.out.println("GODS SIZE:: " + gods.size() + "HO AGGIUNTO:: " + chosenGod);
        for (int i = 0; i < gods.size(); i++) {
            if (gods.get(i).equals(chosenGod)) {
                this.gods.remove(i);
                if (chosenGods.size() == nicknames.size()) {
                    break;
                }
                return;
            }
        }
        initSetupListeners.firePropertyChange("god1ofNConfirmed", false, mess);

    }

    /**
     * Chosen gods size int.
     *
     * @return the int
     */
    public int chosenGodsSize() {
        return this.chosenGods.size();
    }


    /**
     * Is in list god boolean.
     *
     * @param god the god
     * @return the boolean
     */
    public boolean isInListGod(String god) {

        for (String s : gods) {
            if (s.equalsIgnoreCase(god)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is in god boolean.
     *
     * @param god the god
     * @return the boolean
     */
    public boolean isInGod(String god) {

        for (String s : chosenGods) {
            if (s.equals(god)) {
                return true;
            }
        }
        return false;
    }


    /**
     * A player chose his god.
     * The god needs to be removed from the list.
     *
     * @param mess the mess
     */
    public void delGod(GodMessage mess) {
        for (int i = 0; i < chosenGods.size(); i++) {
            if (chosenGods.get(i).equals(mess.getGod())) {
                chosenGods.remove(i);
                break;
            }
        }
        initSetupListeners.firePropertyChange("godConfirm", null, mess);
    }


    /**
     * Notify the view that a
     * worker has been placed
     *
     * @param mess the mess
     */
    public void workerPlaced(WorkerMessage mess) {
        initSetupListeners.firePropertyChange("workerConfirm", null, mess);
    }


    /**
     * First player choosen by godly.
     *
     * @param mess the mess
     */
    public void FirstPlayer(NicknameMessage mess) {
        initSetupListeners.firePropertyChange("firstPlayerConfirmed", null, mess);
    }


}
