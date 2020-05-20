package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.SocketClientConnection;
import it.polimi.ingsw.utils.messages.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Random;

public class GameHandler implements PropertyChangeListener {

    private InitSetup data;
    private HashMap<Integer, String> playersMap = new HashMap<>();      //associazione    ID -> nome
    private Model model;
    private TurnHandler turnHandler;
    private int playersPerGame;
    private int firstPlayerChosenID = 0;

    public GameHandler(InitSetup initSetup, Model m, int playersPerGame) {
        data = initSetup;
        model = m;
        this.playersPerGame = playersPerGame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        //arriva un nick da virtualview. Se non è gia stato inserito
        //da un altro player viene inserito in una lista in
        //initSetup, creato un buffer di creazione del player e viene
        //fatta la richiesta per il colore.
        if (evt.getPropertyName().equals("notifyNickname")) {
            NicknameMessage message = (NicknameMessage) evt.getNewValue();
            String name = message.getNick();
            if (!data.isInUser(name)) {
                data.setUsername(message);
                playerCreationQueue(message);
            }
        }

        //arriva un colore da virtualview. se non è gia stato inserito
        //da un altro player viene inserito in una lista in initSetup
        //e completa la creazione del player nel buffer
        else if (evt.getPropertyName().equals("colorMessageResponse")) {
            ColorMessage message = (ColorMessage) evt.getNewValue();
            Color color = message.getColor();
            if (data.isInColor(color)) {
                playerCreationQueue(message);
                data.delColor(message);
            } else {
                data.askColor(message.getId());
            }
        }

        //arriva una lista di god da virtualview scelti dal godly
        //i god validi vengono inseriti in una lista in initSetup.
        //quelli non validi vengono richiesti. Se tutti sono stati validati
        //viene fatto scegliere un God al giocatore successivo al godly
        else if (evt.getPropertyName().equals("initialCardsResponse")) {
            InitialCardsMessage message = (InitialCardsMessage) evt.getNewValue();
            for (String s : message.getSelectedList()) if (data.isInListGod(s)) data.addChosenGod(s);
            if (data.chosenGodsSize() == playersPerGame) {
                int nextID =( message.getId() + 1) % playersPerGame;
                data.setChosenGods(nextID);
            } else {
                data.initialCards(message.getId(), playersPerGame - data.chosenGodsSize());
            }
        }

        //arriva un God da virtualview. Se non è gia stato inserito da
        //un altro player viene cancellato dalla lista dei god disponibili
        //e assegna la divinità al player. dopo la cancellazione viene fatta
        //la richiesta di scelta god al player successivo in initSetup. Se
        //tutti i god sono stati scelti viene fatta la richiesta al godly
        //per la scelta del primo player
        else if (evt.getPropertyName().equals("godMessageResponse")) {
            GodMessage message = (GodMessage) evt.getNewValue();
            String god = message.getGod();
            if (data.isInGod(god)) {
                data.delGod(message, playersPerGame);
                model.setCard(message.getId(), god);
            } else {
                data.askGod(message.getId());
            }
        }

        //se il firstplayer scelto esiste viene avvisato turnHandler
        else if (evt.getPropertyName().equals("firstPlayerResponse")) {
            FirstPlayerMessage message = (FirstPlayerMessage) evt.getNewValue();
            String name = message.getChosenName();
            if(data.isInUser(name)){
               for(int i=0;i<playersPerGame;i++){
                   System.out.println(playersMap);
                   if(playersMap.get(i).equals(name)){
                       firstPlayerChosenID = i;
                       System.out.println("FIRSTPLAYERID "+ firstPlayerChosenID);
                       turnHandler.setTotalTurnCounter(i);
                       data.initialWorkers(firstPlayerChosenID,0);
                       break;
                   }
               }
            }else{
                data.askFirstPlayer(message.getId());
            }
        }

        else if (evt.getPropertyName().equals("setWorkerResponse")) {
            WorkerMessage message = (WorkerMessage) evt.getNewValue();
            System.out.println("ID PLAYER DA SETWORKER:: " + message.getId());
            Coordinate coordinate = message.getCoordinate();
            int index = message.getId();
                if(model.addWorker(index,coordinate,message.getWorkerNumber())) {
                    if (message.getWorkerNumber() == 0)
                        data.initialWorkers(message.getId(), 1);
                    else {
                        int nextID = (message.getId() + 1) % playersPerGame;
                        if (nextID == firstPlayerChosenID) {
                            data.notifyGameReady();
                        } else {
                            data.initialWorkers(nextID, 0);
                        }
                    }
                }else data.initialWorkers(message.getId(),message.getWorkerNumber());
        } else if (evt.getPropertyName().equalsIgnoreCase("notifyNick")){
        }
    }


    //buffer di creazione del player. Una volta ricevuto il nick e il colore
    //si procede alla creazione effettiva del player. Ad ogni inserimento del
    //nick si crea un hashmap del tipo ID -> nome. Se tutti i player sono stati
    //creati si procede alla scelta random del godly a cui verrà inviato un
    //messaggio per decidere le divinità iniziali
    private void playerCreationQueue(Object value) {
        if (value instanceof NicknameMessage) {
            playersMap.put(((NicknameMessage) value).getId(), ((NicknameMessage) value).getNick());
        } else if (value instanceof ColorMessage) {
            ColorMessage message = (ColorMessage) value;
            System.out.println("GET : " + message.getColor());
            model.addPlayer(new Player(message.getId(), playersMap.get(message.getId()), message.getColor()));
            if (model.getNumOfPlayers() == this.playersPerGame) {
                Random random = new Random();
                int godlyID = random.nextInt(model.getNumOfPlayers());
                System.out.println("Last player is: " + godlyID);
                data.initialCards(godlyID, model.getNumOfPlayers());

            }
        }

    }


    public void setTurnHandler(TurnHandler turnHandler) {
        this.turnHandler = turnHandler;
    }
}
