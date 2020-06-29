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
        //System.setProperty("file.encoding", "UTF-8");
        //String ip = "127.0.0.1";
        //String ip = "87.18.127.100";
        String ip = "gc20.ddns.net";
        int port = 4566;
        int chosenUI = 1;
        boolean useUnicode = !System.getProperty("os.name").startsWith("Windows");

/*
        if (args.length != 0) {
            int arg = 0;
            if (args[arg].equalsIgnoreCase("CLI")) {
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

*/

        if (args.length != 0) {
            ip = args[0];
            if (args.length > 1) {
                port = Integer.parseInt(args[1]);
                if (args.length > 2) {
                    chosenUI = Integer.parseInt(args[2]);
                }
            }
        }
        if (args.length < 3) {
            do {
                System.out.println("CHOSE UI:\n\t0- CLI\t1- GUI\n");
                System.out.print(">>>");
                String input = scanner.nextLine();
                String nums = input.replaceAll("[^0-9]", "");
                if (!nums.equalsIgnoreCase("")) {
                    chosenUI = Integer.parseInt(nums);
                } else chosenUI = -1;
            } while (chosenUI != 0 && chosenUI != 1);
        }


        try {
            new SocketServerConnection(ip, port, chosenUI, useUnicode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}

