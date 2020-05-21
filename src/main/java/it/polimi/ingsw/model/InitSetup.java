package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.messages.*;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;


public class InitSetup {

    private ArrayList<Color> colors;
    private ArrayList<String> username;
    private ArrayList<String> gods;
    private ArrayList<String> chosenGods;
    private PropertyChangeSupport initSetupListeners = new PropertyChangeSupport(this);

    public InitSetup() {
        colors = new ArrayList<>();
        username = new ArrayList<>();
        gods = new ArrayList<>();
        chosenGods = new ArrayList<>();


        Collections.addAll(colors, Color.values());

        gods.add("APOLLO");
        gods.add("ARTEMIS");
        gods.add("ATHENA");
        gods.add("ATLAS");
        gods.add("DEMETER");
        gods.add("HEPHAESTUS");
        gods.add("MINOTAUR");
        gods.add("PAN");
        gods.add("PROMETHEUS");
    }

    public void addInitSetupListener(PropertyChangeListener listener) {
        initSetupListeners.addPropertyChangeListener(listener);
    }

/**********************************************************************************************************************/
    /***NICKNAME***/

    public boolean isInUser(String name) {

        for (String s : username) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void setUsername(NicknameMessage message) {
        username.add(message.getNick());
        System.out.println("SONO NICKNAMECONF DA SERVER");
        initSetupListeners.firePropertyChange("nicknameConfirm", null, message);
    }


/**********************************************************************************************************************/
    /***COLORS***/


    public boolean isInColor(Color color) {

        return colors.contains(color);
    }


    public void delColor(ColorMessage mess) {

        for (int i = 0; i < this.colors.size(); i++)
            if (this.colors.get(i) == mess.getColor())
                this.colors.remove(i);

        initSetupListeners.firePropertyChange("colorConfirm", null, mess);

    }

/**********************************************************************************************************************/
    /***GODS***/

    //avvisa chi è il godly
    public void notifyGodly(int godlyID) {
        GodlyMessage message = new GodlyMessage(godlyID);
        initSetupListeners.firePropertyChange("godlySelected", false, message);

    }


    public void addChosenGod(String chosenGod,InitialCardsMessage mess) {
        this.chosenGods.add(chosenGod);
        for (int i = 0; i < gods.size(); i++) {
            if (gods.get(i).equals(chosenGod)) {
                this.gods.remove(i);
                return;
            }
        }
        initSetupListeners.firePropertyChange("god1ofNConfirmed",false,mess);

    }

    public int chosenGodsSize() {
        return this.chosenGods.size();
    }


    public boolean isInListGod(String god) {

        for (String s : gods) {
            if (s.equalsIgnoreCase(god)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInGod(String god) {

        for (String s : chosenGods) {
            if (s.equals(god)) {
                return true;
            }
        }
        return false;
    }


    public void delGod(GodMessage mess) {

        for (int i = 0; i < chosenGods.size(); i++) {
            if (chosenGods.get(i).equals(mess.getGod())) {
                chosenGods.remove(i);
                break;
            }
        }
        initSetupListeners.firePropertyChange("GodConfirm", null, mess);
    }


/**********************************************************************************************************************/
    /***ALTRO***/
    public void workerPlaced(WorkerMessage mess) {
        initSetupListeners.firePropertyChange("workerConfirm", null, mess);
    }


    public void FirstPlayer(FirstPlayerMessage mess) {
        initSetupListeners.firePropertyChange("firstPlayerConfirmed", null, mess);
    }


    public void notifyGameReady() {
        initSetupListeners.firePropertyChange("gameReady", null, new StartGameMessage());
    }
}
