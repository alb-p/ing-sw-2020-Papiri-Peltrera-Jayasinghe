package it.polimi.ingsw.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * The type Client main.
 */
public class ClientMain {
    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        //String ip = "127.0.0.1";
        //String ip = "87.18.127.100";
        String ip = "localhost";
        int port = 4566;
        int chosenUI = 1;

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
            System.out.println("CHOSEN UI " + chosenUI);
        }


        try {
            new SocketServerConnection(ip, port, chosenUI);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

