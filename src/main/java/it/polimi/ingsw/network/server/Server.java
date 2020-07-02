package it.polimi.ingsw.network.server;

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

    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private Room room = new Room(executor);
    private final Logger logger = Logger.getLogger("server");

    /**
     * Instantiates a new Server.
     *
     * @throws IOException the io exception
     */
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }


    /**
     * Handles all the connection requests from the clients.
     * If the new room is empty asks the following client
     * the number of players for the creating game.
     *
     * When the room will be full, it will start the game,
     * and the server will instantiate a new room and new players.
     */
    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket);

                synchronized (room) {
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
                        logger.log(Level.INFO,"All players are connected, setup starts");
                        room= new Room(executor);
                        int numOfPlayers  = socketConnection.askNumOfPlayers();
                        room.setNumOfPlayers(numOfPlayers);
                        room.addPlayer(socketConnection);
                        socketConnection.setId(room.currentPlayerId()-1);
                    }
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }

    }


}
