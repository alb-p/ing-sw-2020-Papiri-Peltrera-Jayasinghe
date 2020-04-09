package it.polimi.ingsw.view;

import it.polimi.ingsw.model.IslandBoard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CLI extends RemoteView implements Runnable, PropertyChangeListener {

    private Scanner scanner;
    private PrintWriter socketOut;
    private PrintStream printer;

    private IslandBoard board;
    private PropertyChangeSupport viewListeners = new PropertyChangeSupport(this);



    public CLI(Socket socket) throws IOException {
        this.scanner = new Scanner(System.in);
        this.socketOut = new PrintWriter(socket.getOutputStream());
        this.printer= System.out;

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //At the beginning of the game, Model sends a board copy
        if (evt.getPropertyName().equals("initialBoard")) {
            board = (IslandBoard) evt.getNewValue();
            System.out.println(board);
            
        }
    }


}