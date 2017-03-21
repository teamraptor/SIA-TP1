package ar.edu.itba.sia;

import gps.api.GPSState;

/**
 * Created by lumarzo on 14/03/17.
 */
public class Board implements GPSState {

    public enum CellContent {
        EMPTY, RED, BLUE
    }
    private CellContent[][] board;

    public Board(CellContent[][] initialState) {
        this.board = initialState;
    }

    public boolean isFull() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] == CellContent.EMPTY)
                    return false;
            }
        }
        return true;
    }

    public Board setPiece(int x, int y, CellContent piece) {
        int n = board.length;
        if (!(x >= n || x < 0 || y >= n || y < 0))
        	board[x][y] = piece;
        return this;
    }
    
    public CellContent getPiece(int x, int y) {
    	int n = board.length;
        if (x >= n || x < 0 || y >= n || y < 0)
            return null;
        return board[x][y];
    }
    public boolean isValid() {
        int n = board.length;
        boolean emptyR, emptyC, eqR, eqC;
        int countRowsR, countRowsB, countColsR, countColsB;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                emptyR = true;
                emptyC = true;
                eqR = true;
                eqC = true;

                for (int k = 0; k < n; k++) {
                    if (board[i][k] != CellContent.EMPTY)
                        emptyC = false;
                        if (board[i][k] != board[j][k]) {
                            eqC = false;
                    }
                    if (board[k][i] != CellContent.EMPTY) {
                        emptyR = false;
                        if (board[k][i] != board[k][j])
                            eqC = false; 
                    }
                }
                if ((!emptyC && eqC) || (!emptyR && eqR))
                    return false;
            }
        }

        for (int i = 0; i < n; i++) {
        	countRowsB = 0;
        	countRowsR = 0;
        	countColsB = 0;
        	countColsR = 0;
            for (int j = 0; j < n; j++) {
                if (board[i][j] == CellContent.RED)
                    countRowsR ++;
                else if (board[i][j] == CellContent.BLUE)
                    countRowsB ++;
            	
                if (board[j][i] == CellContent.RED)
                    countColsR ++;
                else
                    countColsB ++;
                
                if ((i < n-2) && (j < n-2) && (board[i][j] != CellContent.EMPTY)) {
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
    
    public Integer getSize() {
    	return board.length;
    }

}
