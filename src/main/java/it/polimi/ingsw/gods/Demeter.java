package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

import java.util.Scanner;

public class Demeter extends BasicGodCard {
/*
    @Override
    public boolean turnHandler(Player player, IslandBoard board, Ac string, boolean halfDone) throws Exception {
        //Your Build: Your Worker may
        //build one additional time, but not
        //on the same space.
        // MOVE 0,0 IN 3,2 & BUILD IN 2,1
        // MOVE 0,0 IN 0,1 & BUILD IN 0,2 & IN 1,1
        // BUILD 0,1 IN 0,2 & IN 1,1
        String[] words = string.split(" ");
        if (!halfDone && words[0].toUpperCase().equals("MOVE")) {

            this.move(player.getWorker(stringToCoord(words[1])), stringToCoord(words[3]), board);

        }if (!halfDone && words.length>4) {
            this.build(player.getWorker(stringToCoord(words[3])),stringToCoord(words[7]), board);
            if(words.length>9){
                if(!(stringToCoord(words[7])).equals(stringToCoord(words[10]))){
                    this.build(player.getWorker(stringToCoord(words[3])), stringToCoord(words[10]), board);
                }
            }
            return true;
        }
        if(halfDone && words[0].toUpperCase().equals("BUILD")){
            this.build(player.getWorker(stringToCoord(words[1])), stringToCoord(words[3]), board);
            if(!(stringToCoord(words[3])).equals(stringToCoord(words[6]))){
                this.build(player.getWorker(stringToCoord(words[1])), stringToCoord(words[6]), board);
            }
            return true;
        }

        return false;

    }

    /*
    @Override
    public void build(Worker w, Coordinate coord, IslandBoard board, Construction construction) throws Exception {
        super.build(w, coord, board, construction);
        String choice;
        Coordinate secondCoord;
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to build again? YES to continue");
        choice = in.nextLine();

        if (choice.toUpperCase().equals("YES")) {
            do {
                System.out.println("Row: ");
                int secondRow = in.nextInt();
                System.out.println("Col: ");
                int secondCol = in.nextInt();
                secondCoord = new Coordinate(secondRow, secondCol);
                if(secondCoord.equals(coord)){
                    System.out.println("You cannot build here, insert new coordinate for the additional build: ");
                }
            }
            while (secondCoord.equals(coord));
            in.close();
            super.build(w, secondCoord, board, construction);
        }
    }
    */
}
