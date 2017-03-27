package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.GPSEngine;
import ar.edu.itba.sia.gps.SearchStrategy;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lumarzo on 27/03/17.
 */
public class Main {

    public static void main(String[] args) {

        List<GPSRule> rulz = new ArrayList<>();
        rulz.add(new SimpleRule(Board.CellContent.BLUE));
        rulz.add(new SimpleRule(Board.CellContent.RED));

        Board initialBoard = Board.emptyBoard(4);
        initialBoard.setPiece(0,0, Board.CellContent.RED)
                    .setPiece(2,1, Board.CellContent.BLUE)
                    .setPiece(3,1, Board.CellContent.BLUE)
                    .setPiece(3,2, Board.CellContent.BLUE);

        GPSProblem problem = new Game(initialBoard, rulz);
        GPSEngine engine = new GPSEngine(problem, SearchStrategy.ASTAR);
        engine.findSolution();

    }
}
