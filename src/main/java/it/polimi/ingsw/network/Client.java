package it.polimi.ingsw.network;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;

public class Client{

    private String ip;
    private int port;
    boolean online = false;
    private RemoteView view;
    private ObjectInputStream inputStream;
    private ObjectOutputStream printStream;
    //private PropertyChangeSupport socketListeners = new PropertyChangeSupport(this);
    //oppure trovare soluzione più definitiva per associare id e nome player
    //è strettamente necessario avere il nickname?
    String nickname;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    public void start(){
        try {
            System.out.println("TRY 34");
            Socket socket = new Socket(ip, port);
            //this.view = new CLI(socket);
            //socketListeners.addPropertyChangeListener(this.view);
            this.online = true;
            inputStream = new ObjectInputStream(socket.getInputStream());
            printStream = new ObjectOutputStream(socket.getOutputStream());

            // OPEN READER
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(online){
                            Object inputObject = inputStream.readObject();
                            if(inputObject instanceof WelcomeMessage){
                                view.welcomeMessage();
                            }else if(inputObject instanceof NicknameMessage){
                                nickname = view.askNickPlayer((NicknameMessage) inputObject).getNick();
                                send(view.askNickPlayer((NicknameMessage) inputObject));
                            }else if(inputObject instanceof ColorMessage){
                                //meglio tenere anche info sul colore nel client?
                                send(view.askColor((ColorMessage) inputObject, nickname).getColor());
                            }else if(inputObject instanceof ActionMessage){
                                //TODO va bene passare il nick in questo modo?
                                view.askAction((ActionMessage) inputObject, nickname);
                            }else if(inputObject instanceof GodMessage){
                                //meglio tenere anche info sul colore nel client?
                                send(view.askGod((GodMessage) inputObject, nickname));
                            }

                           // socketListeners.firePropertyChange("Sock",null , inputObject);
                            // TODO collegarsi alla remoteView (CLI o GUI) del client

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

    private synchronized void send(Object message) {
        try {
            printStream.reset();
            printStream.writeObject(message);
            printStream.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }



}
