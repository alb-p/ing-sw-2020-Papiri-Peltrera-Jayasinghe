package it.polimi.ingsw.network;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {

    private static Logger logger = Logger.getLogger("serverMain");


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
