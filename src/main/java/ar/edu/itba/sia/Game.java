package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.*;
import java.util.List;

public class Game implements GPSProblem {

    private final int SURE_WEIGHT = 5;
    private final int ASSUMP_WEIGHT = 1;
    private final int FULL_WEIGHT = 2;

    private Board initialState;
    private List<GPSRule> rules;
    private Integer idealValue;

    public Game(Board initialState, List<GPSRule> rules) {
        this.initialState = initialState;
        this.rules = rules;
        this.idealValue = getIdealValue(initialState.getSize());
    }

    public GPSState getInitState() {
        return initialState;
    }

    public boolean isGoal(GPSState gpsState) {
        Board board = (Board) gpsState;
        return board.isFull() && board.isValid();
    }

    public List<GPSRule> getRules() {
        return this.rules;
    }

    public Integer getHValue(GPSState gpsState) {
        Board board = (Board) gpsState;
        // empty + sure + assumptions = n * n <= empty
        // empty <= n*n - sure - assumptions / assumptions
        /*h* = board.getEmptyCells() >= emptyCells/ emptyCells + assumptions + sure*/
        return idealValue - (board.getSure() * SURE_WEIGHT + board.getAssumptions() * ASSUMP_WEIGHT  + (board.getFullCols() + board.getFullRows()) * FULL_WEIGHT);
    }

        /** LA heuristica:
         *
         * costo real de un movimiento es SURE_WEIGHT
         * costo de la heuristica es idealValue - (sure * SURE_WEIGHT + assump * ASSUMP_WEIGHT)
         * SI O SI ASSUMP_WEIGHT < SURE_WEIGHT para que sea admisible.
         */

        private Integer getIdealValue(final int n) {
        return n * n * SURE_WEIGHT + 2 * n * FULL_WEIGHT;
    }
}
