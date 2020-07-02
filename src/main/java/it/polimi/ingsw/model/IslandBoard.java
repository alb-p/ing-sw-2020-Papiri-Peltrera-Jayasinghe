package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.Coordinate;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Island board represent the
 * board of the game.
 */
public class IslandBoard{

    private final Logger logger = Logger.getLogger("model.board");
    private final Slot[][] board = new Slot[5][5];

    /**
     * Instantiates a new Island board.
     */
    public IslandBoard(){
        for(int i=0; i<5; i++){
            for(int j=0 ; j<5; j++){
                board[i][j]= new Slot();
            }
        }
    }

    /**
     * Returns the slot in the parameter position.
     *
     * @param coordinate the coordinate
     * @return the slot
     */
    public Slot infoSlot(Coordinate coordinate) {
        if(coordinate.getCol()>=0 && coordinate.getCol()<5 &&
                coordinate.getRow()>=0 && coordinate.getRow()<5){
            return board[coordinate.getRow()][coordinate.getCol()];
        }
        else{
            logger.log(Level.WARNING, "Invalid Coordinate");
            return null;
        }
    }

    public Slot[][] getBoard() {
        return board;
    }

    public int getLength(){
        return board.length;
    }





}
