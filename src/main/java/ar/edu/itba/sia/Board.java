package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

import java.util.Arrays;

import static ar.edu.itba.sia.Board.CellContent.*;

public class Board implements GPSState {

    private CellContent[][] board;
    private int hash;
    private int asumptions;
    private int sure;
    private int emptyCells;

    public Board(CellContent[][] initialState) {
        this.board = initialState;
        this.asumptions = 0;
        this.sure = 0;
        this.emptyCells = 0;
        for (int i = 0; i < initialState.length; i++) {
            for (int j = 0; j < initialState.length; j++) {
                if (initialState[i][j] == EMPTY)
                    this.emptyCells++;
            }
        }
        updateHashCode();
    }

    public Board(CellContent[][] initialState, int asumptions, int emptyCells, int sure) {
        this.board = initialState;
        this.sure = sure;
        this.asumptions = asumptions;
        this.emptyCells = emptyCells;
        updateHashCode();
    }

    public boolean isFull() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] == EMPTY)
                    return false;
            }
        }
        return true;
    }

    public boolean isValid() { //TODO improve perfomance
        int n = board.length;
        boolean fullR, fullC, eqR, eqC;
        int countRowsR, countRowsB, countColsR, countColsB;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                fullR = true;
                fullC = true;
                eqR = true;
                eqC = true;

                for (int k = 0; k < n; k++) {
                    if (board[i][k] != EMPTY) {
                        if (board[i][k] != board[j][k])
                            eqR = false;
                    }else
                        fullR = false;

                    if (board[k][i] != EMPTY) {
                        if (board[k][i] != board[k][j])
                            eqC = false;
                    }else
                        fullC = false;
                }
                if ((fullC && eqC) || (fullR && eqR))
                    return false;
            }
        }

        for (int i = 0; i < n; i++) {
        	countRowsB = 0;
        	countRowsR = 0;
        	countColsB = 0;
        	countColsR = 0;
            for (int j = 0; j < n; j++) {
                if (board[i][j] == RED)
                    countRowsR ++;
                else if (board[i][j] == BLUE)
                    countRowsB ++;
            	
                if (board[j][i] == RED)
                    countColsR ++;
                else if (board[j][i] == BLUE)
                    countColsB ++;

                if (board[i][j] != EMPTY) {
                    if ((j < n-2) && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2])
                        return false;
                    if ((i < n-2) && board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j])
                        return false;
                }
            }
            if (countColsB > n/2 || countRowsB > n/2 || countColsR > n/2 || countRowsR > n/2)
                return false;
        }
        return true;
    }

    public static Board emptyBoard(int n) {
        CellContent[][] board = new CellContent[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                board[i][j] = EMPTY;
        }
        return new Board(board);
    }

    public Board copy() {
        int n = this.getSize();
        CellContent[][] board = new CellContent[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                board[i][j] = this.board[i][j];
        }
        return new Board(board,this.asumptions,this.emptyCells,this.sure);
    }

    public enum CellContent {
        EMPTY, RED, BLUE
    }

    /*
    *
    *   Getters & Setters.
    *
    * */

    public Integer getSize() {
        return board.length;
    }

    public Board setPiece(int row, int col, CellContent piece) {
        int n = board.length;
        if (!(row >= n || row < 0 || col >= n || col < 0)) {
            board[row][col] = piece;
            updateHashCode();
            this.emptyCells--;
        }
        return this;
    }

    public CellContent getPiece(int row, int col) {
        int n = board.length;
        if (row >= n || row < 0 || col >= n || col < 0)
            return null;
        return board[row][col];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++)
                builder.append(board[i][j].toString().charAt(0));
            builder.append('\n');
        }
        return builder.toString();
    }



    public boolean validateMove(final int row, final int col) {
        boolean ans = false;
        int size = this.getSize();
        CellContent piece = this.getPiece(row,col);
        //for (int i=0 ; !ans & )

        //Check 2 consecutive values

        //Check full row

        //Check full col

        //Check between

        //Check equal sequence

        return ans;
    }

    public void increaseAsumptions() {
        this.asumptions++;
    }

    public void increaseSure() {
        this.sure++;
    }
    public int getAsumptions() {
        return asumptions;
    }
    public int getSure() {
        return sure;
    }

    public int getEmptyCells() {
        return emptyCells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board1 = (Board) o;

        if (getAsumptions() != board1.getAsumptions()) return false;
        if (emptyCells != board1.emptyCells) return false;
        return Arrays.deepEquals(board, board1.board);
    }

    private void updateHashCode() {
        int result = Arrays.deepHashCode(board);
        result = 31 * result + getAsumptions();
        result = 31 * result + emptyCells;
        this.hash = result;
    }
    @Override
    public int hashCode() {
        return hash;
    }
}
