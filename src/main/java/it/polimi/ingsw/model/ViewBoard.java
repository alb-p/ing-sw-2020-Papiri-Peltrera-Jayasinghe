package it.polimi.ingsw.model;

public class ViewBoard {


    ViewSlot[][] board = new ViewSlot[5][5];

    public ViewBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new ViewSlot();
            }
        }
    }

    public ViewBoard(IslandBoard realBoard){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new ViewSlot(realBoard.getSlot(i,j),new Coordinate(i,j));

            }
        }

    }



    public String toString (){
        StringBuilder total = new StringBuilder(5*5*2);

        total.append("        \u2554\u2550\u2550\u2566\u2550\u2550\u2566\u2550\u2550\u2566\u2550\u2550\u2566\u2550\u2550\u2557").append('\n');
        total.append("        \u2551"+board[4][0]+"\u2551"+board[4][1]+"\u2551"+board[4][2]+"\u2551"+board[4][3]+"\u2551"+board[4][4]+"\u2551").append('\n');
        total.append("        \u2560\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u2563").append('\n');
        total.append("        \u2551"+board[3][0]+"\u2551"+board[3][1]+"\u2551"+board[3][2]+"\u2551"+board[3][3]+"\u2551"+board[3][4]+"\u2551").append('\n');
        total.append("        \u2560\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u2563").append('\n');
        total.append("        \u2551"+board[2][0]+"\u2551"+board[2][1]+"\u2551"+board[2][2]+"\u2551"+board[2][3]+"\u2551"+board[2][4]+"\u2551").append('\n');
        total.append("        \u2560\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u2563").append('\n');
        total.append("        \u2551"+board[1][0]+"\u2551"+board[1][1]+"\u2551"+board[1][2]+"\u2551"+board[1][3]+"\u2551"+board[1][4]+"\u2551").append('\n');
        total.append("        \u2560\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u256C\u2550\u2550\u2563").append('\n');
        total.append("        \u2551"+board[0][0]+"\u2551"+board[0][1]+"\u2551"+board[0][2]+"\u2551"+board[0][3]+"\u2551"+board[0][4]+"\u2551").append('\n');
        total.append("        \u255A\u2550\u2550\u2569\u2550\u2550\u2569\u2550\u2550\u2569\u2550\u2550\u2569\u2550\u2550\u255D").append('\n');
        total.append("         \u24FF  \u24F5  \u24F6  \u24F7  \u24F8 ").append('\n');


        return (total.toString());
    }

    public ViewSlot getSlot(int i,int j){
        return board[i][j];

    }

    public void add(ViewSlot vSlot) {

        this.board[vSlot.getCoord().getRow()][vSlot.getCoord().getCol()]=vSlot;

    }
}
