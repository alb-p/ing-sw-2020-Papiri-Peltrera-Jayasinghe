package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.SocketClientConnection;
import it.polimi.ingsw.utils.messages.ColorMessage;
import it.polimi.ingsw.utils.messages.NicknameMessage;
import org.w3c.dom.ls.LSOutput;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;

public class VirtualView implements Runnable, PropertyChangeListener {

    private ArrayList<SocketClientConnection> connections;
    private HashMap<String, SocketClientConnection> playersMap;
    private ArrayList<Color> colorSet = new ArrayList<Color>();
    private PropertyChangeSupport virtualViewListeners = new PropertyChangeSupport(this);


    public VirtualView (ArrayList<SocketClientConnection> connections ){
        this.connections = connections;
        colorSet.addAll(Arrays.asList(Color.values()));
        }


/**********************************************************************************************/
    public void addVirtualViewListener(PropertyChangeListener listener) {
        virtualViewListeners.addPropertyChangeListener(listener);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //io sono in ascolto sia del model che del socketCC.
        if(evt.getPropertyName().equals("sendNick")){
            //manda a client un nicknameMessage
            //se scc è null manda a tutti, altrimenti a quello specifico
        }else if(evt.getPropertyName().equals("receiveNick")){
            //manda a controller il nick
            virtualViewListeners.firePropertyChange("nickMessageResponse", null, evt.getNewValue());

        }else if(evt.getPropertyName().equals("setNick")){
            //imposta nick in view
            NicknameMessage message = (NicknameMessage) evt.getNewValue();
            playersMap.put(message.getNick(),message.getScc());
        }else if(evt.getPropertyName().equals("sendColor")){
            //manda a client specifico la scelta del colore
        }else if(evt.getPropertyName().equals("receiveColor")){
            //manda a controller il colore
            virtualViewListeners.firePropertyChange("colorMessageResponse", null, evt.getNewValue());
        }else if(evt.getPropertyName().equals("delColor")){
            ColorMessage message=(ColorMessage) evt.getNewValue();
            for(SocketClientConnection c: connections){
                if(c!=message.getScc()){
                    //invia nuovi colori
                }
            }

        }

    }

/************************************************************************************************/



//////////// QUANDO  ROOM  AVVIA LA VIEW
////////////
//////////// System.out.println("Welcome to Santorini");
//////////// NicknameMessage message=new NicknameMessage();
//////////// manda a tutti message
////////////
////////////
////////////
////////////











    @Override
    public void run() {

    }

    public void playersSetup(){

    }

    private void serializeAsk( int i){
        new Thread(){
            public void run(){
                try{
                    fun(2);

                }catch (Exception e ){}
            }
        }.start();
    }

    public void fun( int i) throws IOException, ClassNotFoundException {
        String val = "VAL";

        while (!val.equals("VAL")){
            val = connections.get(i).askNick();
        }

    }

}
