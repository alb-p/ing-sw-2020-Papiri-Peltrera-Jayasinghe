package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.network.SocketClientConnection;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;


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
        ColorMessage newMessage=new ColorMessage(id,this.colors);                                       //viene inviato un messaggio con i colori rimanenti a
        initSetupListeners.firePropertyChange("sendColor", false, newMessage);      //chi ha ha finito di mettere il nick o colore sbagliato

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
                                                        //un giocatore x ha secondo un colore che deve essere ora cancellato dalla lista
    public void delColor(ColorMessage message){        //dei colori disponibili. Il messaggio viene inviato a tutti tranne a giocatore x
        colors.remove(message.getColor());
       initSetupListeners.firePropertyChange("delColor", null, message);

    }

    public void setUsername(String user){
        username.add(user);
    }

    public void WrongUsername(int id) {
        NicknameMessage message=new NicknameMessage(id);
        initSetupListeners.firePropertyChange("sendNick", false, message);
    }
}
