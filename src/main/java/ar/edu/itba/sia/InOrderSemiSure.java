package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public class InOrderSemiSure implements Heuristic{
    private Integer size;
    private SureSemiSureHeuristic sureSemiSureHeuristic;

    public InOrderSemiSure(Board initialBoard) {
        this.size = initialBoard.getSize();
        this.sureSemiSureHeuristic = new SureSemiSureHeuristic(initialBoard);
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
        return accum + sureSemiSureHeuristic.getHValue(gpsState);
    }

    @Override
    public Integer getCost() {
        return 3*size + sureSemiSureHeuristic.getCost();
    }

    @Override
    public String getName() {
        return "In Order" + sureSemiSureHeuristic.getName();
    }
}
