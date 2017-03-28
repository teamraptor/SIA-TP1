package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.Board.CellContent;
import java.util.Optional;
import static ar.edu.itba.sia.Board.CellContent.*;

public class TwoInLineRule implements GPSRule{

    public Optional<GPSState> evalRule(GPSState gpsState) {
        Board board = ((Board)gpsState).copy();
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board.getPiece(i,j) == EMPTY)
                    continue;
                if (board.getPiece(i,j) == board.getPiece(i,j+1)) {
                    CellContent p = board.getPiece(i,j) == BLUE ? RED : BLUE;
                    if(board.getPiece(i,j+2) == EMPTY || board.getPiece(i,j-1) == EMPTY) {
                        board.setPiece(i, j + 2, p);
                        board.setPiece(i, j - 1, p);
                        return Optional.of(board);
                    }
                }
                if (board.getPiece(i,j) == board.getPiece(i+1,j)) {
                    CellContent p = board.getPiece(i,j) == BLUE ? RED : BLUE;
                    if(board.getPiece(i+2,j) == EMPTY || board.getPiece(i-1,j) == EMPTY) {
                        board.setPiece(i+2, j, p);
                        board.setPiece(i-1, j, p);
                        return Optional.of(board);
                    }
                }
            }
        }
        return Optional.empty();
    }

    public Integer getCost() { return 2; }

    public String getName() {
        return "Two in line Rule";
    }

    @Override
    public String toString() {
        return getName();
    }
}
