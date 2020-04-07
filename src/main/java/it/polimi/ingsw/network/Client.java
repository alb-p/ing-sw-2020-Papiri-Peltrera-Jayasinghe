package it.polimi.ingsw.network;

import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{

    private String ip;
    private int port;
    boolean online = false;
    private RemoteView view;
    private ObjectInputStream inputStream;
    private PrintWriter printStream;
    private PropertyChangeSupport socketListeners = new PropertyChangeSupport(this);


    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;



    }


    public void start(){
        try {
            final Socket socket = new Socket(ip, port);
            this.view = new CLI(socket);
            socketListeners.addPropertyChangeListener(this.view);
            this.online = true;
            inputStream = new ObjectInputStream(socket.getInputStream());

            printStream = new PrintWriter(socket.getOutputStream());

            // OPEN READER
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(online){
                            Object inputObject = inputStream.readObject();
                            socketListeners.firePropertyChange("Sock",null , inputObject);
                            // TODO collegarsi alla remoteView (CLI) del client

                        }
                    }catch (Exception e){
                        online = false;
                    }

                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO writer che andrà a mandare gli oggetti al controller del server
        // writer che sarà chiamato dalla update dopo la chiamatea della cli

    }

}
