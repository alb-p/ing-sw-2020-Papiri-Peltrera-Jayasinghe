package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
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
    private int currentPlayerID = -1;
    private boolean atLeastOneGod = false;
    private boolean workerPlaced = false;

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
                data.setNicknames(message);
                playerCreationQueue(message);
            }

        } else if (evt.getPropertyName().equals("notifyColor")) {
            ColorMessage message = (ColorMessage) evt.getNewValue();
            Color color = message.getColor();
            if (data.isInColor(color)) {
                data.delColor(message);
                playerCreationQueue(message);

            }

        } else if (evt.getPropertyName().equals("notify1ofNGod")) {
            InitialCardsMessage message = (InitialCardsMessage) evt.getNewValue();
            if (message.getId() == currentPlayerID) {
                for (String s : message.getSelectedList()) if (data.isInListGod(s)) data.addChosenGod(s, message);
                if (data.chosenGodsSize() == playersPerGame) currentPlayerID = (currentPlayerID + 1) % playersPerGame;
            }

        } else if (evt.getPropertyName().equals("notifyGod") && data.chosenGodsSize() == playersPerGame) {
            GodMessage message = (GodMessage) evt.getNewValue();
            String god = message.getGod();
            if (message.getId() == currentPlayerID && data.isInGod(god)) {
                model.setCard(message.getId(), god);
                data.delGod(message);
                currentPlayerID = (currentPlayerID + 1) % playersPerGame;
                atLeastOneGod = true;

            }

        } else if (evt.getPropertyName().equals("notifyFirstPlayer") && atLeastOneGod && data.chosenGodsSize() == 0) {
            FirstPlayerMessage message = (FirstPlayerMessage) evt.getNewValue();
            String name = message.getChosenName();
            if (message.getId() == currentPlayerID && data.isInUser(name)) {
                for (int i = 0; i < playersPerGame; i++) {
                    System.out.println(playersMap);
                    if (playersMap.get(i).equals(name)) {
                        firstPlayerChosenID = i;
                        currentPlayerID = firstPlayerChosenID;
                        System.out.println("FIRSTPLAYERID " + firstPlayerChosenID);
                        turnHandler.setTotalTurnCounter(i);
                        data.FirstPlayer(message);
                        break;
                    }
                }
            }

        } else if (evt.getPropertyName().equals("notifyWorker") && firstPlayerChosenID != -1) {
            WorkerMessage message = (WorkerMessage) evt.getNewValue();
            System.out.println("ID PLAYER DA SETWORKER:: " + message.getId());
            Coordinate coordinate = message.getCoordinate();
            if (currentPlayerID == message.getId()) {
                if (!workerPlaced && model.addWorker(currentPlayerID, coordinate, message.getWorkerNumber())) {
                    workerPlaced = true;
                    data.workerPlaced(message);
                } else if (workerPlaced && model.addWorker(currentPlayerID, coordinate, message.getWorkerNumber())) {
                    workerPlaced = false;
                    currentPlayerID++;
                    data.workerPlaced(message);
                    if (currentPlayerID == firstPlayerChosenID) data.notifyGameReady();
                }


            }
        }
    }



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
