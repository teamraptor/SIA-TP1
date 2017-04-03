package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.GPSEngine;
import ar.edu.itba.sia.gps.GPSObserver;
import ar.edu.itba.sia.gps.SearchStrategy;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ar.edu.itba.sia.Board.CellContent.*;

public class Main {

    public static void main(String[] args) throws IOException {

        List<GPSRule> rulz = new ArrayList<>();
//        rulz.add(new TwoInLineRule());
//        rulz.add(new BetweenRule());
//       rulz.add(new SimpleRule(Board.CellContent.BLUE));
//        rulz.add(new SimpleRule(Board.CellContent.RED));




        Board initialBoard = BoardParser.readBoard("./boards/board2.txt");

        for (int i = 0; i < initialBoard.getSize(); i++) {
            for (int j = 0; j < initialBoard.getSize(); j++) {
                if (initialBoard.getPiece(i,j) == EMPTY) {
                    rulz.add(new Rule(BLUE, i, j));
                    rulz.add(new Rule(RED, i, j));
                }
            }
        }

        GPSProblem problem = new Game(initialBoard, rulz);

        GPSEngine engine = new GPSEngine(problem, SearchStrategy.GREEDY, 5);
        //GPSObserver observer = new TreePlotter();
        //engine.addObserver(observer);
//        NodeCounter counter = new NodeCounter();
//        engine.addObserver(counter);
        Debugger debugger = new Debugger();
        engine.addObserver(debugger);
        long time = System.currentTimeMillis();
        List<GPSRule> solution = engine.findSolution();
        time = System.currentTimeMillis() - time;
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
        String dateFormatted = formatter.format(date);
        System.out.println();
        GPSState state = initialBoard;
        System.out.println(initialBoard);
        System.out.println();
        for (GPSRule rule :solution) {
            state = rule.evalRule(state).get();
            if (!((Board)state).isValid()) {
                System.out.println("NO");
                return;
            }
            System.out.println(rule);
            System.out.println(state);
            System.out.println();
        }
        System.out.println("TIME: " + dateFormatted);
        //System.out.println("NODES: " + counter.getCounter());
    }
}
