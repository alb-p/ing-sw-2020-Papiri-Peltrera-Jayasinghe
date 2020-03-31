package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class View extends Observable implements Runnable, Observer {

    private Scanner in;
    private PrintStream out;
    private int i=0;
    private int numberOfPlayers = 0 ;


    public View() {

        in = new Scanner(System.in);
        out = new PrintStream(System.out);
    }

    @Override
    public void run() {

        System.out.println("WeLcOmE!'' on Santorini");
        setPlayersNumber();
    }

    private void setPlayersNumber(){

        numberOfPlayers= 3;
        System.out.println("2 or 3 plaYers?");
        numberOfPlayers= in.nextInt();
        while (numberOfPlayers!= 2 && numberOfPlayers!= 3) {
            System.out.println("Invalid number, 2 or 3 plaYers?");
            numberOfPlayers= in.nextInt();

        }
        in.nextLine();
        setChanged();
        notifyObservers((Integer) numberOfPlayers);
    }


    @Override
    public void update(Observable o, Object arg) {

        System.out.println((IslandBoard)arg);
        i++;
        System.out.println("Valore i in update di view "+i);
        if (i > numberOfPlayers -1) {
            System.out.println("Stampa turno");
            setChanged();
            notifyObservers(in.nextLine());
        }
    }



    public String setNick(int i) {

        System.out.println("Insert nickname for player " + (i + 1) + ": ");
        return (in.nextLine());
    }



    public String setColor(ArrayList<String> listaColori,String nick) {

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
        return(new Coordinate(row,col));
    }



    public String setCard(int i) {

        System.out.println("Insert card for player " + (i + 1) + ": ");
        String cardName = in.nextLine();

        return cardName.toUpperCase();
    }

    public String requestTask() {

        return(in.nextLine());
    }
}
