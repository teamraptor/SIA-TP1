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

    public Game(Board initialState) {
        this.initialState = initialState;
    }

    public GPSState getInitState() {
        return initialState;
    }

    public boolean isGoal(GPSState gpsState) {
        Board board = (Board) gpsState;
        return board.isFull();
    }

    public List<GPSRule> getRules() {
        return null;
    }

    public Integer getHValue(GPSState gpsState) {
        return null;
    }
}
