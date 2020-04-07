package it.polimi.ingsw.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private int numOfPlayers;
    private ExecutorService executor = Executors.newFixedThreadPool(128);


    public void run(){
        while(true){
            try{
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection();
                executor.submit(socketConnection);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }



}
