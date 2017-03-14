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


}
