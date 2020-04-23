package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.network.SocketClientConnection;

import java.io.Serializable;

public interface Message{

    String getMessage();
    void setId(int i);
    int getId();
}
