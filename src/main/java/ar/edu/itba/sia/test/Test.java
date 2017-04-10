package ar.edu.itba.sia.test;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ar.edu.itba.sia.gps.GPSEngine;
import ar.edu.itba.sia.gps.SearchStrategy;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.tpe1.NodeCounter;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {

		SearchStrategy ss = SearchStrategy.BFS;
		System.out.println("Starting...");
		GPSState s = null;

		if (args.length <= 0) {
			System.err.println("No board file selected");
			return;
		} else {
			s = GameReader.open(args[0]);
		}

		if (args.length <= 1) {
			System.out.println("No Search Strategy Selected, default is BFS");
		} else {
			if (args[1].equals(SearchStrategy.BFS.toString())) {
				ss = SearchStrategy.BFS;
			} else if (args[1].equals(SearchStrategy.DFS.toString())) {
				ss = SearchStrategy.DFS;
			} else if (args[1].equals(SearchStrategy.ASTAR.toString())) {
				ss = SearchStrategy.ASTAR;
			} else if (args[1].equals(SearchStrategy.IDDFS.toString())) {
				ss = SearchStrategy.IDDFS;
			} else if (args[1].equals(SearchStrategy.GREEDY.toString())) {
				ss = SearchStrategy.GREEDY;
			} else {
				System.out.println("Invalid Search Strategy, default is BFS");
			}
		}

		DeadLockFinder dlf = new DeadLockFinder((SokobanState) s);
		GPSState processed_state = dlf.getStateWithDeadLocks();
		System.out.println("Tablero Inicial: ");
		GameReader.printState((SokobanState) processed_state);

		if (processed_state == null) {
			System.out.println("El tablero inicial no puede ser resuelto");
			return;
		}

		GPSProblem problem = new SokobanProblem(processed_state);
		GPSEngine engine = new GPSEngine(problem, ss, 5);
		
        NodeCounter counter = new NodeCounter();
        engine.addObserver(counter);

        long time = System.currentTimeMillis();
        List<GPSRule> solution = engine.findSolution();
        time = System.currentTimeMillis() - time;

        /*NO SOLUTION*/
        if (solution.isEmpty()) {
            System.out.println("NO SOLUTION FOUND");
            return;
        }
        System.out.println("SOLUTION FOUND");
        System.out.println("DEPTH: " + solution.size());
        int cost = 0;
        for (GPSRule rule: solution) {
            cost += rule.getCost();
        }
        System.out.println("SOLUTION COST: " + cost);
        System.out.println("FRONTIER SIZE: " + counter.getFrontierCounter());
        System.out.println("EXPLORED SIZE: " + counter.getVisitedCounter());

        System.out.println("INITIAL BOARD");
        System.out.println(processed_state);
        System.out.println();
        
        for (GPSRule rule :solution) {
        	processed_state = rule.evalRule(processed_state).get();
            System.out.println(rule);
            System.out.println(processed_state);
            System.out.println();
        }

        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
        String dateFormatted = formatter.format(date);
        System.out.println("TIME: " + dateFormatted);

	}
}
