package it.polimi.ingsw.network.server;

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
        int port = 4566;
        if(args.length >0){
            String arg = args[0].replaceAll("[^0-9]", "");
            if (!arg.equals("")){
                port = Integer.parseInt(arg);
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
