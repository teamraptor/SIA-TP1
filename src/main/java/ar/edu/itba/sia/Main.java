package ar.edu.itba.sia;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import ar.edu.itba.sia.gps.GPSEngine;
import ar.edu.itba.sia.gps.SearchStrategy;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

public class Main {

    public static void main(String[] args) throws IOException {

        Map<String, Object> arguments = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            String item = args[i];

            if (item.charAt(0) != '-') {
                throw new IOException("Input error");
            }

            if ((i+1) < args.length) { // not the last item
                String next = args[i+1];
                if (next.charAt(0) == '-') {
                    arguments.put(item, true);
                } else {
                    arguments.put(item, next);
                    i += 1;
                }
            }
        }

        System.out.println(arguments);

        Board initialBoard = null;
        SearchStrategy strategy = null;
        Integer cut = null;
        Integer hCode = null;

        if (arguments.get("-b") == null) {
            throw new IOException("Missing Board");
        } else {
            String boardPath = (String) arguments.get("-b");
            if (!boardPath.endsWith(".txt")) {
                throw new IOException("Board parameter is invalid");
            }
            initialBoard = BoardParser.readBoard(boardPath);
        }
    	
    	if (arguments.get("-s") == null) {
            throw new IOException("Missing Search Strategy");
        } else {
            String searchStrategy = (String) arguments.get("-s");
            if (!SearchStrategy.contains(searchStrategy)) {
                throw new IOException("Invalid strategy.");
            }
            strategy = SearchStrategy.valueOf(searchStrategy);
        }

        if (arguments.get("-c") == null) {
            throw new IOException("Missing cut condition");
        } else {
            cut = new Integer((String) arguments.get("-c"));
        }

        if (arguments.get("-h") == null) {
            throw new IOException("Missing heuristic");
        } else {
            hCode = new Integer((String) arguments.get("-h"));
        }

        Heuristic heuristic;

        switch (hCode) {
            case 0:
                heuristic = new TrivialHeuristic();
                break;
            case 1:
                heuristic = new LeastAssumptions(initialBoard);
                break;
            case 2:
                heuristic = new ComposedHeuristic(new LeastAssumptions(initialBoard), new InOrderHeuristic(initialBoard));
                break;
            case 3:
                heuristic = new ComposedHeuristic(new NeighboursHeuristic(), new LeastAssumptions(initialBoard));
                break;
            case 4:
                //heuristic = new SureSemiSureHeuristic(initialBoard);
                heuristic = new SureSemiSureAdmissibleHeuristic(initialBoard);
                break;
            case 5:
//              heuristic = new ComposedHeuristic(new SureSemiSureHeuristic(initialBoard), new InOrderHeuristic(initialBoard));
                heuristic = new ComposedHeuristic(new SureSemiSureAdmissibleHeuristic(initialBoard), new InOrderAdmissibleHeuristic(initialBoard));
                break;
            case 6:
                heuristic = new BestHeuristic(initialBoard);
                break;
            default:
                throw new IOException("Invalid heuristic value.");
        }
        GPSProblem problem = MyGameFactory.getNewProblem(initialBoard, heuristic);
        GPSEngine engine = new GPSEngine(problem, strategy, cut);
        //GPSObserver observer = new TreePlotter();
        //engine.addObserver(observer);
        NodeCounter counter = new NodeCounter();
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
        System.out.println("ASSUMPTIONS: " + ((Board)state).getAssumptions());
        System.out.println("SURE: " + ((Board)state).getSure());
        System.out.println("SEMI SURE: " + ((Board)state).getSemiSure());
        System.out.println("TIME: " + dateFormatted);
        //System.out.println("NODES: " + counter.getCounter());
    }
}
