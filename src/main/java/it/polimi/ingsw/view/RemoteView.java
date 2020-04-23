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

    public abstract GodMessage askGod(GodMessage inputObject);

    public abstract Object askNumOfPlayers(SetupMessage inputObject);

    public abstract void showColor(ColorSelectedMessage inputObject);
}
