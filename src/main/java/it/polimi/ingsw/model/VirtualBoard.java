package it.polimi.ingsw.model;

/**
 * The type Virtual board represent the board for the view.
 */
public class VirtualBoard {


    private VirtualSlot[][] board = new VirtualSlot[5][5];

    /**
     * Instantiates a new Virtual board.
     */
    public VirtualBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new VirtualSlot(new Coordinate(i, j));
            }
        }
    }

    /**
     * To string string.
     *
     * @return the string
     */
    public String toString() {
        StringBuilder total = new StringBuilder(5 * 5 * 2);

        total.append("       ╔══╦══╦══╦══╦══╗").append('\n');
        total.append("   5   ║" + board[4][0] + "║" + board[4][1] + "║" + board[4][2] + "║" + board[4][3] + "║" + board[4][4] + "║").append('\n');
        total.append("       ╠══╬══╬══╬══╬══╣").append('\n');
        total.append("   4   ║" + board[3][0] + "║" + board[3][1] + "║" + board[3][2] + "║" + board[3][3] + "║" + board[3][4] + "║").append('\n');
        total.append("       ╠══╬══╬══╬══╬══╣").append('\n');
        total.append("   3   ║" + board[2][0] + "║" + board[2][1] + "║" + board[2][2] + "║" + board[2][3] + "║" + board[2][4] + "║").append('\n');
        total.append("       ╠══╬══╬══╬══╬══╣").append('\n');
        total.append("   2   ║" + board[1][0] + "║" + board[1][1] + "║" + board[1][2] + "║" + board[1][3] + "║" + board[1][4] + "║").append('\n');
        total.append("       ╠══╬══╬══╬══╬══╣").append('\n');
        total.append("   1   ║" + board[0][0] + "║" + board[0][1] + "║" + board[0][2] + "║" + board[0][3] + "║" + board[0][4] + "║").append('\n');
        total.append("       ╚══╩══╩══╩══╩══╝").append('\n');
        total.append("        1 |2 |3 |4 |5").append('\n');

        return (total.toString());
    }

    /**
     * Gets slot.
     *
     * @param c the c
     * @return the slot
     */
    public VirtualSlot getSlot(Coordinate c) {
        return board[c.getRow()][c.getCol()];
    }

    /**
     * Sets slot.
     *
     * @param vSlot the v slot
     */
    public void setSlot(VirtualSlot vSlot) {
        this.board[vSlot.getCoordinate().getRow()][vSlot.getCoordinate().getCol()] = vSlot;
    }

}
