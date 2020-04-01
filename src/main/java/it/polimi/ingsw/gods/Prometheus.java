package it.polimi.ingsw.gods;

import it.polimi.ingsw.model.*;

import java.util.Scanner;

public class Prometheus extends BasicGodCard {
    String name = "Promethus";
    boolean effect = false;
    Coordinate savedCoord;

    //Your Turn: If your Worker does
    //not move up, it may build both
    //before and after moving
    // MOVE 0,0 IN 0,1 & BUILD IN 1,1
    //MOVE 0,0 IN 0,1 enter BUILD 0,1 IN 1,1 (2 lines)
    // BUILD 0,1 IN 0,2 & MOVE IN 1,1 & BUILD IN 1,2
    //TODO what happens if you get wrong input? (general case) what happens if no-levelUp condition isn't respected?

    @Override
    public boolean turnHandler(Player player, IslandBoard board, String string, boolean halfDone) throws Exception {
        String[] words = string.split(" ");
        if (!halfDone && words[0].toUpperCase().equals("MOVE")) {

            this.move(player.getWorker(stringToCoord(words[1])), stringToCoord(words[3]), board);
            if (words.length > 4) {
                this.build(player.getWorker(stringToCoord(words[3])), stringToCoord(words[7]), board, new Floor());
                return true;
            }
        }
        if (halfDone && words[0].toUpperCase().equals("BUILD")) {
            this.build(player.getWorker(stringToCoord(words[1])), stringToCoord(words[3]), board, new Floor());
            return true;

        }
        //build-move-build I'm even considering the player moves in the newly built slot
        if (!halfDone && words[0].toUpperCase().equals("BUILD") &&
                board.infoSlot(stringToCoord(words[1])).getConstructionLevel() >=
                        board.infoSlot(stringToCoord(words[7])).getConstructionLevel()) {
            this.build(player.getWorker(stringToCoord(words[1])), stringToCoord(words[3]), board, new Floor());
            this.move(player.getWorker(stringToCoord(words[1])), stringToCoord(words[7]), board);
            this.build(player.getWorker(stringToCoord(words[7])), stringToCoord(words[11]), board, new Floor());
        }
        return false;
    }

}