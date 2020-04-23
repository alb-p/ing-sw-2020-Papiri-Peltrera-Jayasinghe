package it.polimi.ingsw.network;

import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.View;

public class ClientMain {

    public static void main(String[] args){

        String ip = "127.0.0.1";
        int port = 4566;

        if(args.length!=0){
            ip = args[0];
            if(args.length>1){
                port = Integer.parseInt(args[1]);
            }
        }

        try {
            new Client(ip, port, 0).start();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

