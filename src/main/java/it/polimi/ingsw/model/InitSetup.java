package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.messages.*;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;


public class InitSetup {

    private ArrayList<Color> colors;
    private ArrayList<String> nicknames;
    private ArrayList<String> gods;
    private ArrayList<String> chosenGods;
    private PropertyChangeSupport initSetupListeners = new PropertyChangeSupport(this);

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
        gods.add("MINOTAUR");
        gods.add("PAN");
        gods.add("PROMETHEUS");
        gods.add("ZEUS");
    }

    public void addInitSetupListener(PropertyChangeListener listener) {
        initSetupListeners.addPropertyChangeListener(listener);
    }

/**********************************************************************************************************************/
    /***NICKNAME***/

    public boolean isInUser(String name) {

        for (String s : nicknames) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void setNicknames(NicknameMessage message) {
        nicknames.add(message.getNickname());
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
                /*if(chosenGods.size()==nicknames.size()){
                    initSetupListeners.firePropertyChange("god1ofNConfirmed",false,mess);
                }else return;*/
            }
        }
        System.out.println("\nMESS e FIRE:: " + mess.getSelectedList());
        initSetupListeners.firePropertyChange("god1ofNConfirmed", false, mess);

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
        System.out.println("GOD CONFIRM SERVER");
        initSetupListeners.firePropertyChange("godConfirm", null, mess);
    }


/**********************************************************************************************************************/
    /***ALTRO***/
    public void workerPlaced(WorkerMessage mess) {
        initSetupListeners.firePropertyChange("workerConfirm", null, mess);
    }


    public void FirstPlayer(NicknameMessage mess) {
        initSetupListeners.firePropertyChange("firstPlayerConfirmed", null, mess);
    }


}
