package it.polimi.ingsw.network.server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Server main is the launcher for the server.
 */
public class ServerMain {

    private static final Logger logger = Logger.getLogger("serverMain");


    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args){

        Server server;
        int port = 4566;
        if(args.length >0){
            try{
                port = Integer.parseInt(args[0]);
            } catch(NumberFormatException e){
                logger.log(Level.SEVERE, "Invalid port argument");
            }
        }
        try {
            server = new Server(port);
            logger.log(Level.INFO, "Server started");
            server.run();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }







}
