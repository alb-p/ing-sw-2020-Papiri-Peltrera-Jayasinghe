package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.network.SocketClientConnection;

public class NicknameMessage implements Message {

    private String message;
    private String nick;
    private int id;

    public NicknameMessage() {
        this.message = "Inserisci il nickname";
        this.nick = null;

    }

    public NicknameMessage(int i) {
        this.message = "Inserisci il nickname";
        this.nick = null;
        id = i;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void setId(int i) {
        this.id = i;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
