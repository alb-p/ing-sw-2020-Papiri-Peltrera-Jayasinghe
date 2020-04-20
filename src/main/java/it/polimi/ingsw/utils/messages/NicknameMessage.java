package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.network.SocketClientConnection;

public class NicknameMessage implements Message{

    private String message;
    private String nick;
    private SocketClientConnection scc;

    public NicknameMessage(){
        this.message = "Inserisci il nickname";
        this.nick=null;
        this.scc=null;
    }

    public NicknameMessage(SocketClientConnection socket){
        this.message = "Inserisci il nickname";
        this.nick=null;
        this.scc=socket;
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


    public SocketClientConnection getScc() {
        return scc;
    }
}
