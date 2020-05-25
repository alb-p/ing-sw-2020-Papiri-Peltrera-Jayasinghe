package it.polimi.ingsw.utils.messages;

import java.io.Serializable;

public class NicknameMessage implements Message , Serializable {

    private String message;
    private String nickname;
    private int id;

    public NicknameMessage() {
        this.message = "Choose a nickname:";
        this.nickname = null;

    }

    public NicknameMessage(int id) {
        this.message = "Choose a nickname:";
        this.nickname = null;
        this.id = id;
    }

    public NicknameMessage(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.message = "default";
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
