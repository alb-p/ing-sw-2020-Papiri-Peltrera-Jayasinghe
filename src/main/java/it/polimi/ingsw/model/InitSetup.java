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

    public void setUsername(String user) {
        username.add(user);
    }

    //rimanda messaggio se il giocatore ha impostato un nick non valido
    public void wrongUsername(int id) {
        NicknameMessage message = new NicknameMessage(id);
        message.setMessage("Nickname is already being used, please try again: ");
        initSetupListeners.firePropertyChange("sendNick", false, message);
    }

/**********************************************************************************************************************/
    /***COLORS***/

    //viene inviato un messaggio con i colori rimanenti a
    //chi ha ha finito di mettere il nick o colore sbagliato
    public void askColor(int id) {
        ColorMessage message = new ColorMessage(id, this.colors);
        initSetupListeners.firePropertyChange("sendColor", false, message);

    }

    public boolean isInColor(Color color) {

        return colors.contains(color);
    }

    //un giocatore x ha scelto un colore che deve essere ora cancellato dalla lista
    //dei colori disponibili. Il messaggio viene inviato a tutti tranne a giocatore x
    public void delColor(ColorMessage mess) {

        for (int i = 0; i < this.colors.size(); i++)
            if (this.colors.get(i) == mess.getColor())
                this.colors.remove(i);
        ColorSelectedMessage message = new ColorSelectedMessage(mess.getId(), mess.getColor());
        initSetupListeners.firePropertyChange("delColor", null, message);

    }

/**********************************************************************************************************************/
    /***GODS***/

    //imposta le divinità scelta
    public void setChosenGods(int ID) {
        GodMessage message = new GodMessage(ID, chosenGods);
        initSetupListeners.firePropertyChange("sendGod", null, message);
    }

    //imposta le divinità scelte dal giocatore radom
    public void addChosenGod(String chosenGod) {
        this.chosenGods.add(chosenGod);
        for (int i = 0; i < gods.size(); i++) {
            if (gods.get(i).equals(chosenGod)) {
                this.gods.remove(i);
                return;
            }
        }
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

    //cancella la divinità scelta e manda un messaggio di richiesta divinità al prossimo player
    //se tutte le divinità sono state scelte viene fatta la richiesta del first player al prescelto
    public void delGod(GodMessage mess, int numOfPlayers) {
        int nextPlayer;
        for (int i = 0; i < chosenGods.size(); i++) {
            if (chosenGods.get(i).equals(mess.getGod())) {
                chosenGods.remove(i);
                break;
            }
        }
        if (chosenGods.isEmpty()) {
            askFirstPlayer(mess.getId());

        } else {
            nextPlayer = (mess.getId() + 1) % numOfPlayers;
            System.out.println("NEXTPLA "+nextPlayer+ "MESSid "+mess.getId()+ "PLAYERSPERGAME: "+numOfPlayers);
            GodMessage message = new GodMessage(nextPlayer, this.chosenGods);
            initSetupListeners.firePropertyChange("sendGod", null, message);
        }

    }

    //manda un messaggio al giocatore random per la scelta delle divinità
    public void initialCards(int lastPlayerID, int numberOfGodsLeft) {
        InitialCardsMessage message = new InitialCardsMessage(this.gods, lastPlayerID, numberOfGodsLeft);
        initSetupListeners.firePropertyChange("initialCards", false, message);

    }

    //richiede un god perchè il giocatore ne ha selezionato uno non valido
    public void askGod(int PlayerID) {
        GodMessage message = new GodMessage(PlayerID, chosenGods);
        initSetupListeners.firePropertyChange("sendGod", false, message);
    }


/**********************************************************************************************************************/
    /***ALTRO***/
    public void initialWorkers(int ID, int workerNumber) {
        WorkerMessage message = new WorkerMessage(ID,workerNumber);
        initSetupListeners.firePropertyChange("setWorker",null,message);
    }

    //manda una richiesta per la scelta del primo giocatore
    public void askFirstPlayer(int id) {
        FirstPlayerMessage message=new FirstPlayerMessage(id,this.username);
        initSetupListeners.firePropertyChange("firstPlayer",null,message);
    }


    public void notifyGameReady() {
        initSetupListeners.firePropertyChange("gameReady", null, new StartGameMessage());
    }
}
