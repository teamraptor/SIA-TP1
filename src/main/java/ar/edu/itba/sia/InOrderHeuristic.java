package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public class InOrderHeuristic implements Heuristic{
    private Integer size;
    private LeastAssumptions leastAssumptions;

    public InOrderHeuristic(Board initialBoard) {
        this.size = initialBoard.getSize();
        this.leastAssumptions = new LeastAssumptions(initialBoard);
    }

    @Override
    public Integer getHValue(GPSState gpsState) {
        Board board = (Board) gpsState;
        Integer n = board.getSize();
        Integer accum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Board.CellContent piece = board.getPiece(i,j);
                accum += piece == Board.CellContent.EMPTY ? 3 * size : i+j;
            }
        }
        return accum + leastAssumptions.getHValue(gpsState);
    }

    @Override
    public Integer getCost() {
        return 3*size + leastAssumptions.getCost();
    }

    @Override
    public String getName() {
        return "In Order" + leastAssumptions.getName();
    }
}
