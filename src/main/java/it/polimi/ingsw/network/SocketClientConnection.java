package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;


public class SocketClientConnection implements Runnable, PropertyChangeListener {

    private Socket socket;
    private ObjectOutputStream outSocket;
    private ObjectInputStream inSocket;
    private PropertyChangeSupport sccListeners = new PropertyChangeSupport(this);


    private VirtualView view;


    private int id;
    private Server server;

    private boolean active = true;

    public SocketClientConnection(Socket newSocket) {
        try {
            socket = newSocket;
            outSocket = new ObjectOutputStream(newSocket.getOutputStream());
            inSocket = new ObjectInputStream(newSocket.getInputStream());
            System.out.println("CREATA CONNECTION");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized boolean isActive() {
        return active;
    }


    public synchronized void send(Object message) {
        try {
            if (socket.isClosed()) return;
            outSocket.reset();
            outSocket.writeObject(message);
            outSocket.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int askNumOfPlayers() {
        int read = 0;
        try {
            //outSocket = new ObjectOutputStream(socket.getOutputStream());
            send(new SetupMessage());
            read = ((SetupMessage) inSocket.readObject()).getField();
            while (!(read == 2 || read == 3)) {
                send(new SetupMessage());
                read = ((SetupMessage) inSocket.readObject()).getField();
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        }
        return read;
    }

    public void notifyGamePlaying() {
        send("The Lobby is full!! Please Wait");
    }

    public void setId(int i) {

        this.id = i;
    }

    public int getId() {
        return id;
    }

    public void setView(VirtualView view) {
        this.view = view;
    }


    @Override
    public void run() {
        try {
            sendEvent(new PropertyChangeEvent(this,"gameReady", null,true));
            while (true) {
                Object inputObject = inSocket.readObject();

                if (inputObject instanceof PropertyChangeEvent) {
                    PropertyChangeEvent event = (PropertyChangeEvent) inputObject;
                    event = new PropertyChangeEvent(id, event.getPropertyName(), event.getOldValue(),event.getNewValue());
                    sccListeners.firePropertyChange(event);
                }
            }
        } catch (Exception e) {

            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        sendEvent(evt);
    }

    public void sendEvent(PropertyChangeEvent evt) {
        try {
            synchronized (outSocket) {
                if (socket.isClosed()) return;
                outSocket.reset();
                outSocket.writeObject(evt);
                outSocket.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addSccListener(PropertyChangeListener listener) {
        sccListeners.addPropertyChangeListener(listener);
    }
}