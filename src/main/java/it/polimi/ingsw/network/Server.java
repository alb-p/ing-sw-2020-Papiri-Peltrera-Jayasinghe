package it.polimi.ingsw.network;

import it.polimi.ingsw.Room;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Server.
 */
public class Server {

    private static final int PORT = 4566;
    private ServerSocket serverSocket = new ServerSocket(PORT);
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Room room = new Room(executor);
    private Logger logger = Logger.getLogger("server");

    /**
     * Instantiates a new Server.
     *
     * @throws IOException the io exception
     */
    public Server() throws IOException {
    }


    /**
     * Run.
     */
    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("trewc");
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket);
                /*
                inserico i giocatori nella waiting list
                if waiting list. length è 1 allora a quello chiedo il numero di giocatori
                creo una stanza con quel parametro e aggiungo alla stanza i primi
                n giocatori della lista
                verifica che la stanza non sia vuota, se
                è vuota chiede al socket il numero di giocatori

                 */
                synchronized (room) {
                    logger.log(Level.INFO,"Server synchronized on Room");
                    if (room.isUninitialized()) {
                        int numOfPlayers  = socketConnection.askNumOfPlayers();
                        room.setNumOfPlayers(numOfPlayers);
                        room.addPlayer(socketConnection);
                        socketConnection.setId(room.currentPlayerId()-1);
                    }else if(!room.isReady()){
                        room.addPlayer(socketConnection);
                        socketConnection.setId(room.currentPlayerId()-1);
                        if(room.isReady())room.start();
                    }else{
                        socketConnection.notifyGamePlaying();
                        room= new Room(executor);
                        int numOfPlayers  = socketConnection.askNumOfPlayers();
                        room.setNumOfPlayers(numOfPlayers);
                        room.addPlayer(socketConnection);
                        socketConnection.setId(room.currentPlayerId()-1);
                    }
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }

    }


}
