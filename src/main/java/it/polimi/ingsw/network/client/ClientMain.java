package it.polimi.ingsw.network.client;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * The type Client main is the launcher for the client.
 */
public class ClientMain {
    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Handles the parameters order and sets up
     * the cli or the gui based on this.
     * Also searches for ip and port to
     * set up the connection
     *
     * @param args the args
     */
    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 4566;
        int chosenUI = 1;
        boolean useUnicode = !System.getProperty("os.name").startsWith("Windows");


        if (args.length != 0) {
            int arg = 0;
            if (args[arg].equalsIgnoreCase("-CLI")) {
                chosenUI = 0;
                arg++;
            }
            if(args.length > arg) {
                ip = args[arg];
                arg++;
                if (args.length > arg) {
                    port = Integer.parseInt(args[arg]);
                }
            }
        }

        try {
            new SocketServerConnection(ip, port, chosenUI, useUnicode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}

