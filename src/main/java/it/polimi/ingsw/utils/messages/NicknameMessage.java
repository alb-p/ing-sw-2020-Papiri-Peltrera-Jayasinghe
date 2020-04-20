package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.network.SocketClientConnection;

public class NicknameMessage implements Message{

    String message = "Inserisci il nickname";
    String nick;
    private SocketClientConnection scc;

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public String getMessage() {
        return this.message;
    }


}
