package it.polimi.ingsw.network;

public class ClientMain {

    public static void main(String[] args){

        //String ip = "gc20.ddns.net"; // per giocare online
        String ip = "127.0.0.1";
        int port = 4566;

        if(args.length!=0){
            ip = args[0];
            if(args.length>1){
                port = Integer.parseInt(args[1]);
            }
        }

        try {
            new SocketServerConnection(ip, port, 0).start();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

