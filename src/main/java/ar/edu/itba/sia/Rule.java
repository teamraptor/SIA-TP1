package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.Board.CellContent;

import java.util.Optional;

public class Rule implements GPSRule{
    
	private CellContent piece;
    private CellContent opposite;
    private int x;
    private int y;

    public Rule(CellContent piece, int x, int y) {
        this.piece = piece;
        if (piece == CellContent.BLUE)
            this.opposite = CellContent.RED;
        else
            this.opposite = CellContent.BLUE;
        this.x = x;
        this.y = y;
    }

    @Override
    public Integer getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return piece.toString() + " (" + x + ", " + y + ")";
    }

    @Override
    public Optional<GPSState> evalRule(GPSState gpsState) {
        Board board = ((Board) gpsState).copy();
        boolean assumption = true;
        if (board.getPiece(x,y) != CellContent.EMPTY)
            return Optional.empty();

        board.setPiece(x,y,piece);

        if (!board.validateMove(x,y)) {
            return Optional.empty();
        }
        if ((board.getAssumptions() == 0)
            && ((board.getPiece(x-1,y) == opposite && board.getPiece(x+1,y) == opposite)
            || (board.getPiece(x-1,y) == opposite && board.getPiece(x-2,y) == opposite)
            || (board.getPiece(x+1,y) == opposite && board.getPiece(x+2,y) == opposite)
            || (board.getPiece(x,y-1) == opposite && board.getPiece(x,y+1) == opposite)
            || (board.getPiece(x,y-1) == opposite && board.getPiece(x,y-2) == opposite)
            || (board.getPiece(x,y+1) == opposite && board.getPiece(x,y+2) == opposite)
            || (board.countCellContentInRow(opposite,x) == board.getSize()/2)
            || (board.countCellContentInCol(opposite,y) == board.getSize()/2))) {
                board.increaseSure();
                assumption = false;
        }
        if (board.isFullCol(y)) {
            board.incrementFullCols();
            assumption = false;
        }
        if (board.isFullRow(x)) {
            board.incrementFullRows();
            assumption = false;
        }
        if (assumption)
            board.increaseAsumptions();
        return Optional.of(board);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
