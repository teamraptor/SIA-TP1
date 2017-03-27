package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;
import static ar.edu.itba.sia.Board.CellContent.*;

public class Board implements GPSState {

    private CellContent[][] board;
    private int filledSize;

    public Board(CellContent[][] initialState) {
        this.board = initialState;
        filledSize = 0;
        for (int i = 0; i < initialState.length; i++) {
            for (int j = 0; j < initialState.length; j++)
                filledSize += initialState[i][j]== EMPTY? 0:1;
        }
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

    public boolean isValid() {
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
                
                if ((i < n-2) && (j < n-2) && (board[i][j] != EMPTY)) {
                    if (board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2])
                        return false;
	                if (board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j])
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
        return new Board(board);
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

    public int getFilledSize() { return this.filledSize; }

    public Board setPiece(int row, int col, CellContent piece) {
        int n = board.length;
        if (!(row >= n || row < 0 || col >= n || col < 0)) {
            if (board[row][col] == EMPTY && piece != EMPTY)
                filledSize++;
            if (board[row][col] != EMPTY && piece == EMPTY)
                filledSize--;
            board[row][col] = piece;
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
}
