package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class View implements Runnable, PropertyChangeListener {

    private Scanner in;
    private PrintStream out;
    private int i = 0;
    private int numberOfPlayers = 0;
    private VirtualBoard vBoard;


    private PropertyChangeSupport viewListeners = new PropertyChangeSupport(this);

    public void addViewListener(PropertyChangeListener listener) {
        viewListeners.addPropertyChangeListener(listener);
    }


    public View() {

        in = new Scanner(System.in);
        out = new PrintStream(System.out);
        vBoard = new VirtualBoard();
    }


    @Override
    public void run() {

        System.out.println("WeLcOmE!'' on Santorini");
        System.out.println("\n" +
                "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░╔╦═╦╗░░░░░░░░░░░░░╔╗░░░░░░░░░░░░\n" +
                "░░░░░║║║║╠═╦╗╔═╦═╦══╦═╗║╚╦═╗░░░░░░░░░\n" +
                "░░░░░║║║║║╩╣╚╣═╣╬║║║║╩╣║╔╣╬║░░░░░░░░░\n" +
                "░░░░░╚═╩═╩═╩═╩═╩═╩╩╩╩═╝╚═╩═╝░░░░░░░░░\n" +
                "░░░░░╔══╗░░░░░╔╗░░░░╔╗░░╔╗░░░░░░░░░░░\n" +
                "░░░░░║══╬═╗╔═╦╣╚╦═╦╦╬╬═╦╬╣░░░░░░░░░░░\n" +
                "░░░░░╠══║╬╚╣║║║╔╣╬║╔╣║║║║║░░░░░░░░░░░\n" +
                "░░░░░╚══╩══╩╩═╩═╩═╩╝╚╩╩═╩╝░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n");
        setPlayersNumber();
    }


    private void setPlayersNumber() {

        numberOfPlayers = 3;
        System.out.println("2 or 3 plaYers?");
        numberOfPlayers = in.nextInt();
        while (numberOfPlayers != 2 && numberOfPlayers != 3) {
            System.out.println("Invalid number, 2 or 3 plaYers?");
            numberOfPlayers = in.nextInt();
        }
        in.nextLine();
        viewListeners.firePropertyChange("playersNumber", null, numberOfPlayers);
    }


    public String setNick(int i) {

        System.out.println("Insert nickname for player " + (i + 1) + ": ");
        return (in.nextLine());
    }


    public String setColor(ArrayList<String> listaColori, String nick) {

        String color;
        do {
            System.out.println("Ok " + nick + ", which color do you prefer?");
            for (String s : listaColori) {
                System.out.print("\t- " + s);
            }
            System.out.print("\n");
            color = in.nextLine().toUpperCase();
        } while (!listaColori.contains(color));
        return color.toUpperCase();
    }


    public Coordinate setWorkers(int j) {
        System.out.println("Set position for worker no " + j + ":\n\trow :");
        int row = in.nextInt();
        System.out.println("\tcol :");
        int col = in.nextInt();

        in.nextLine();
        return (new Coordinate(row, col));
    }


    public String setCard(int i) {

        System.out.println("Insert card for player " + (i + 1) + ": ");
        String cardName = in.nextLine();

        return cardName.toUpperCase();
    }




    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("turnHandler") ){
            i++;
            if (i > numberOfPlayers - 1) {

                System.out.println(vBoard);
                System.out.println("Stampa turno");
                viewListeners.firePropertyChange("playerAction", null, in.nextLine());
            }else System.out.println(vBoard);


        }else if (evt.getPropertyName().equals("deltaUpdate")) {
            vBoard.add((VirtualSlot)evt.getNewValue());
        }


    }
}
