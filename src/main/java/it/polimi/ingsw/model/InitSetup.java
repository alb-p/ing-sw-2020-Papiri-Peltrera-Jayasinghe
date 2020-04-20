package it.polimi.ingsw.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class InitSetup {

    ArrayList<String> colors;
    ArrayList<String> username;
    ArrayList<String> gods;

    private PropertyChangeSupport initSetupListeners = new PropertyChangeSupport(this);

    public InitSetup() {

        colors = new ArrayList<>();
        username = new ArrayList<>();
        gods = new ArrayList<>();

        for (Color c : Color.values().clone()) {
            colors.add(c.getName());
        }

        gods.add("Apollo");
        gods.add("Artemis");
        gods.add("Athena");
        gods.add("Atlas");
        gods.add("Demeter");
        gods.add("Hephaestus");
        gods.add("Minotaur");
        gods.add("Pan");
        gods.add("Prometheus");



    }

    public void addInitSetupListener(PropertyChangeListener listener) {
        initSetupListeners.addPropertyChangeListener(listener);
    }

    public ArrayList<String> getUsername() {
        return username;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public ArrayList<String> getGods() {
        return gods;
    }

    public boolean isInUser(String name) {

        for (String s : username) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInColor(String color) {

        for (String s : colors) {
            if (s.equals(color)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInGod(String god) {

        for (String s : gods) {
            if (s.equals(god)) {
                return true;
            }
        }
        return false;
    }

    public void delGod(String god){
        gods.remove(god);
        initSetupListeners.firePropertyChange("delGod", null, gods.clone());

    }

    public void delColor(String color){
        colors.remove(color);
       initSetupListeners.firePropertyChange("delColor", null, colors.clone());

    }

    public void setUsername(String user){
        username.add(user);
    }

}
