package it.polimi.ingsw.network;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Server main is the launcher for the server.
 */
public class ServerMain {

    private static Logger logger = Logger.getLogger("serverMain");


    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args){

        Server server;
        try {
            server = new Server();
            server.run();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }







}
