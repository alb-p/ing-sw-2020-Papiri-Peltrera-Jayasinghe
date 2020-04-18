package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.SocketClientConnection;

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


    public void addModelListener(PropertyChangeListener listener) {
        virtualViewListeners.addPropertyChangeListener(listener);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //io sono in ascolto sia del model che del socketCC.
        if(evt.getPropertyName().equals("playersSetup")){


        }
    }

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
