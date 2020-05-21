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
    private int firstPlayerChosenID = -1;
    private int currentPlayerID=-1;

    public GameHandler(InitSetup initSetup, Model m, int playersPerGame) {
        data = initSetup;
        model = m;
        this.playersPerGame = playersPerGame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {


        if (evt.getPropertyName().equals("notifyNickname")) {
            NicknameMessage message = (NicknameMessage) evt.getNewValue();
            String name = message.getNick();
            if (!data.isInUser(name)) {
                data.setUsername(message);
                playerCreationQueue(message);
            }
        }


        else if (evt.getPropertyName().equals("notifyColor")) {
            ColorMessage message = (ColorMessage) evt.getNewValue();
            Color color = message.getColor();
            if (data.isInColor(color)) {
                data.delColor(message);
                playerCreationQueue(message);

            }
        }


        else if (evt.getPropertyName().equals("notify1ofNGod")) {
            InitialCardsMessage message = (InitialCardsMessage) evt.getNewValue();
            if(message.getId()==currentPlayerID){
                for (String s : message.getSelectedList()) if (data.isInListGod(s)) data.addChosenGod(s,message);
                if (data.chosenGodsSize() == playersPerGame) currentPlayerID = (currentPlayerID + 1) % playersPerGame;
            }
        }


        else if (evt.getPropertyName().equals("notifyGod") && data.chosenGodsSize() == playersPerGame) {
            GodMessage message = (GodMessage) evt.getNewValue();
            String god = message.getGod();
            if (message.getId()==currentPlayerID&&data.isInGod(god)) {
                model.setCard(message.getId(), god);
                data.delGod(message);
                currentPlayerID = (currentPlayerID + 1) % playersPerGame;

            }
        }

        else if (evt.getPropertyName().equals("notifyFirstPlayer") && data.chosenGodsSize() == playersPerGame) {
            FirstPlayerMessage message = (FirstPlayerMessage) evt.getNewValue();
            String name = message.getChosenName();
            if(message.getId()==currentPlayerID&&data.isInUser(name)){
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
                currentPlayerID = random.nextInt(model.getNumOfPlayers());
                System.out.println("Last player is: " + currentPlayerID);
                data.notifyGodly(currentPlayerID);
            }
        }

    }


    public void setTurnHandler(TurnHandler turnHandler) {
        this.turnHandler = turnHandler;
    }
}
