package ar.edu.itba.sia;

import java.util.List;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

public class Game implements GPSProblem {

    private static final int SURE_WEIGHT = 5;
    private static final int ASSUMP_WEIGHT = 1;
    private static final int FULL_WEIGHT = 2;

    private Board initialState;
    private List<GPSRule> rules;
    private Integer idealValue;
    private final int heuristic;

    public Game(final Board initialState, final List<GPSRule> rules, final int heuristic) {
        this.initialState = initialState;
        this.rules = rules;
        this.idealValue = getIdealValue(initialState.getSize());
        this.heuristic = heuristic;
    }

    public GPSState getInitState() {
        return initialState;
    }

    public boolean isGoal(final GPSState gpsState) {
        Board board = (Board) gpsState;
        return board.isFull() && board.isValid();
    }

    public List<GPSRule> getRules() {
        return this.rules;
    }

    public Integer getHValue(final GPSState gpsState) {
        Board board = (Board) gpsState;
        // empty + sure + assumptions = n * n <= empty
        // empty <= n*n - sure - assumptions / assumptions
        /*h* = board.getEmptyCells() >= emptyCells/ emptyCells + assumptions + sure*/
        return heuristic==0? firstHeuristic(board) : bestHeuristic(board);
    }

    private Integer getIdealValue(final int n) {
        return n * n * SURE_WEIGHT + 2 * n * FULL_WEIGHT;
    }
    
    private Integer firstHeuristic(final Board board) {
    	return board.getEmptyCells();
    }
    
    private Integer bestHeuristic(final Board board) {
    	return idealValue - (board.getSure() * SURE_WEIGHT + board.getAssumptions() * ASSUMP_WEIGHT  + (board.getFullCols() + board.getFullRows()) * FULL_WEIGHT);
    }
}
