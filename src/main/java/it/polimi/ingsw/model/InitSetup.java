package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.network.SocketClientConnection;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.GodMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;
import it.polimi.ingsw.utils.messages.Select3GodsMessage;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


public class InitSetup {

    ArrayList<String> colors;
    ArrayList<String> username;
    ArrayList<String> gods;



    ArrayList<String> chosenGods;



    private PropertyChangeSupport initSetupListeners = new PropertyChangeSupport(this);

    public InitSetup() {

        colors = new ArrayList<>();
        username = new ArrayList<>();
        gods = new ArrayList<>();
        chosenGods =new ArrayList<>();


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

    public void askColor(int id){
        ColorMessage message=new ColorMessage(id,this.colors);                                       //viene inviato un messaggio con i colori rimanenti a
        initSetupListeners.firePropertyChange("sendColor", false, message);      //chi ha ha finito di mettere il nick o colore sbagliato

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

    public ArrayList<String> getChosenGods(){
        return chosenGods;
    }

    public void setChosenGods(ArrayList<String> chosenGods,int firstplayerID) {
        this.chosenGods = chosenGods;
        GodMessage message=new GodMessage(firstplayerID,chosenGods);
        initSetupListeners.firePropertyChange("sendGod", null, message);
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

    public boolean isInListGod(String god) {

        for (String s : gods) {
            if (s.equals(god)) {
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

    public void delGod(GodMessage mess){
        gods.remove(mess.getGod());
        GodMessage message=new GodMessage(mess.getId(),this.chosenGods);
        initSetupListeners.firePropertyChange("delGod", null, message);

    }
                                                        //un giocatore x ha secondo un colore che deve essere ora cancellato dalla lista
    public void delColor(ColorMessage mess){        //dei colori disponibili. Il messaggio viene inviato a tutti tranne a giocatore x
        colors.remove(mess.getColor());
        ColorMessage message=new ColorMessage(mess.getId(),this.colors);
       initSetupListeners.firePropertyChange("delColor", null, message);

    }

    public void setUsername(String user){
        username.add(user);
    }

    public void WrongUsername(int id) {
        NicknameMessage message=new NicknameMessage(id);
        initSetupListeners.firePropertyChange("sendNick", false, message);
    }

    public void choose3cards(int firstplayerID) {
        Select3GodsMessage message=new Select3GodsMessage(this.gods,firstplayerID);
        initSetupListeners.firePropertyChange("3cards", false, message);

    }
}
