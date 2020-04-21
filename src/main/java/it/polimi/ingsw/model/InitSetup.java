package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.messages.*;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


public class InitSetup {

    private ArrayList<String> colors;
    private ArrayList<String> username;
    private ArrayList<String> gods;
    private ArrayList<String> chosenGods;
    private PropertyChangeSupport initSetupListeners = new PropertyChangeSupport(this);

    public InitSetup() {
        colors = new ArrayList<>();
        username = new ArrayList<>();
        gods = new ArrayList<>();
        chosenGods = new ArrayList<>();


        for (Color c : Color.values().clone()) {
            colors.add(c.getName());
        }
        gods.add("APOLLO");
        gods.add("ARTEMIS");
        gods.add("ATHENA");
        gods.add("ATLAS");
        gods.add("DEMETER");
        gods.add("HEPHAESTUS");
        gods.add("MINOTAUR");
        gods.add("PAN");
        gods.add("PREMETHEUS");
    }

    public void addInitSetupListener(PropertyChangeListener listener) {
        initSetupListeners.addPropertyChangeListener(listener);
    }


    /***NICKNAME***/
    public boolean isInUser(String name) {

        for (String s : username) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void setUsername(String user) {
        username.add(user);
    }

    public void wrongUsername(int id) {
        NicknameMessage message = new NicknameMessage(id);                                            //rimanda messaggio se il giocatore ha impostato un nick non valido
        initSetupListeners.firePropertyChange("sendNick", false, message);
    }

/**********************************************************************************************************************************************************************************************/
    /***COLORS***/

    public void askColor(int id) {
        ColorMessage message = new ColorMessage(id, this.colors);                                       //viene inviato un messaggio con i colori rimanenti a
        initSetupListeners.firePropertyChange("sendColor", false, message);      //chi ha ha finito di mettere il nick o colore sbagliato

    }

    public boolean isInColor(String color) {

        for (String s : colors) {
            if (s.equals(color)) {
                return true;
            }
        }
        return false;
    }

    //un giocatore x ha secondo un colore che deve essere ora cancellato dalla lista
    public void delColor(ColorMessage mess) {                        //dei colori disponibili. Il messaggio viene inviato a tutti tranne a giocatore x
        colors.remove(mess.getColor());
        ColorMessage message = new ColorMessage(mess.getId(), this.colors);
        initSetupListeners.firePropertyChange("delColor", null, message);

    }

/**********************************************************************************************************************************************************************************************/
    /***GODS***/

    public void setChosenGods(ArrayList<String> chosenGods, int firstplayerID) {                 //imposta le divinità scelte dal giocatore random
        this.chosenGods = chosenGods;                                                           //e manda al primo player un messaggio di scelta divinità
        GodMessage message = new GodMessage(firstplayerID, chosenGods);
        initSetupListeners.firePropertyChange("sendGod", null, message);
    }


    public boolean isInListGod(String god) {

        for (String s : gods) {
            if (s.equals(god.toUpperCase())) {
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

    public void delGod(GodMessage mess, int numOfPlayers) {               //cancella la divinità scelta e manda un messaggio di
        int nextplayer;                                                 //richiesta divinità al prossimo player
        chosenGods.remove(mess.getGod());                               //se tutti hanno scelto la divinità stampa "fine"
        if (chosenGods.size() == 0) {
            System.out.println("fine");
            initSetupListeners.firePropertyChange("gameReady", null, new StartGameMessage());

        }
        else {
            nextplayer = mess.getId() + 1 % numOfPlayers;
            GodMessage message = new GodMessage(nextplayer, this.chosenGods);
            initSetupListeners.firePropertyChange("sendGod", null, message);
        }

    }


    public void initialCards(int lastPlayerID, int numberOfPlayers) {                                //manda un messaggio al giocatore random per la scelta delle divinità
        InitialCardsMessage message = new InitialCardsMessage(this.gods, lastPlayerID, numberOfPlayers);
        initSetupListeners.firePropertyChange("initialCards", false, message);

    }

    public void askGod(int PlayerID) {                                                              //richiede un god perchè il giocatore ne ha selezionato uno non valido
        GodMessage message = new GodMessage(PlayerID, chosenGods);
        initSetupListeners.firePropertyChange("sendGod", false, message);
    }
}
