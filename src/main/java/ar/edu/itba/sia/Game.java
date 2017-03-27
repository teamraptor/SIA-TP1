package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.*;
import java.util.List;

public class Game implements GPSProblem {

    private Board initialState;
    private List<GPSRule> rules;

    public Game(Board initialState, List<GPSRule> rules) {
        this.initialState = initialState;
        this.rules = rules;
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

    public Integer getHValue(GPSState gpsState) { return ((Board) gpsState).getFilledSize(); }
}
