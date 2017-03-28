package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.GPSEngine;
import ar.edu.itba.sia.gps.SearchStrategy;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<GPSRule> rulz = new ArrayList<>();
        rulz.add(new SimpleRule(Board.CellContent.BLUE));
        rulz.add(new SimpleRule(Board.CellContent.RED));
        rulz.add(new TwoInLineRule());
        rulz.add(new BetweenRule());

        Board initialBoard = Board.emptyBoard(4);
        initialBoard.setPiece(0,0, Board.CellContent.RED)
                    .setPiece(2,1, Board.CellContent.BLUE)
                    .setPiece(3,1, Board.CellContent.BLUE)
                    .setPiece(3,2, Board.CellContent.BLUE);


        try {
            initialBoard = BoardParser.readBoard("/home/lumarzo/board.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }


        GPSProblem problem = new Game(initialBoard, rulz);

        GPSEngine engine = new GPSEngine(problem, SearchStrategy.ASTAR);
        List<GPSRule> solution = engine.findSolution();
        GPSState state = initialBoard;
        System.out.println(initialBoard);
        System.out.println();
        for (GPSRule rule :solution) {
            state = rule.evalRule(state).get();
            System.out.println(rule);
            System.out.println(state);
            System.out.println();
        }

    }
}
