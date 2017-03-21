package ar.edu.itba.sia;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;

import java.util.List;

/**
 * Created by lumarzo on 14/03/17.
 */
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

    public Integer getHValue(GPSState gpsState) {
        return Integer.MAX_VALUE - Integer.MIN_VALUE;
    }
}
