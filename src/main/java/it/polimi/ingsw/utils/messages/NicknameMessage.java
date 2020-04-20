package it.polimi.ingsw.utils.messages;

public class NicknameMessage {

    String message = "Inserisci il nickname";
    String nick;

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
