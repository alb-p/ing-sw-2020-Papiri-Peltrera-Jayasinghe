package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.Room;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 4566;
    private ServerSocket serverSocket = new ServerSocket(PORT);
    private int numOfPlayers;
    private Room room = new Room();

    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private ArrayList<SocketClientConnection> waitingList = new ArrayList<SocketClientConnection>();

    public Server() throws IOException {
    }


    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("trewc");
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket);
                /*TODO CREAZIONE STANZA
                inserico i giocatori nella waiting list
                if waiting list. length è 1 allora a quello chiedo il numero di giocatori
                creo una stanza con quel parametro e aggiungo alla stanza i primi
                n giocatori della lista
                verifica che la stanza non sia vuota, se
                è vuota chiede al socket il numero di giocatori

                 */
                synchronized (room) {
                    System.out.println("sincronizzato su room");
                    if (room.isUninitialized()) {
                        int numOfPlayers  = socketConnection.askNumOfPlayers();
                        System.out.println("numero di gioc : "+numOfPlayers);
                        room.setNumOfPlayers(numOfPlayers);
                        room.addPlayer(socketConnection);
                        socketConnection.setId(room.currentPlayerId()-1);
                    }else if(!room.isReady()){
                        room.addPlayer(socketConnection);
                        socketConnection.setId(room.currentPlayerId()-1);
                        System.out.println("ROOM TO BE STARTED");
                        if(room.isReady())room.start();
                    }else{
                        socketConnection.notifyGamePlaying();
                        //waitingList.add(socketConnection);
                        room= new Room();
                        int numOfPlayers  = socketConnection.askNumOfPlayers();
                        System.out.println("numero di gioc : "+numOfPlayers);
                        room.setNumOfPlayers(numOfPlayers);
                        room.addPlayer(socketConnection);
                        socketConnection.setId(room.currentPlayerId()-1);
                    }
                    executor.submit(socketConnection);



                }

            } catch (Exception e) {
               // e.printStackTrace();
            }
        }

    }


}
