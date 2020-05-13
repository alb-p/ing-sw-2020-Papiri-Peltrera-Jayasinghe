package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.utils.messages.*;

import java.io.Console;

public abstract class RemoteView extends View {
    //client invoca funzioni di questa classe per richiedere input
    // all'utente a seguito di richieste specifiche


    public abstract NicknameMessage askNickPlayer(NicknameMessage message);

    public abstract void welcomeMessage();

    public abstract ActionMessage askAction(ActionMessage message);

    public abstract ColorMessage askColor (ColorMessage message);

    public abstract InitialCardsMessage askGodList (InitialCardsMessage message);

    public abstract void waitingMess(WaitingMessage inputObject);

    public abstract GodMessage askGod(GodMessage inputObject);

    public abstract Object askNumOfPlayers(SetupMessage inputObject);

    public abstract void showColor(ColorSelectedMessage inputObject);

    public abstract FirstPlayerMessage firstPlayer(FirstPlayerMessage message);

    public abstract WorkerMessage setWorker(WorkerMessage message);

    public abstract void gameIsReady(StartGameMessage inputObject);

    public abstract void updateVBoard(VirtualSlotMessage inputObject);

    public abstract void winnerMess(WinnerMessage inputObject);

    public abstract ChoiceMessage askChoice(ChoiceMessage inputObject);

    public abstract void genericMess(GenericMessage inputObject);
}
