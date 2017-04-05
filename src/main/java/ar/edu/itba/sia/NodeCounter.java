package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.GPSNode;
import ar.edu.itba.sia.gps.GPSObserver;

public class NodeCounter implements GPSObserver {
    private long frontierCounter;
    private long visitedCounter;
    @Override
    public void start(GPSNode gpsNode) {
        frontierCounter = 1;
        visitedCounter = 1;
    }

    @Override
    public void observeFrontier(GPSNode gpsNode) {
        frontierCounter++;
    }

    @Override
    public void observeVisited(GPSNode gpsNode) {
        visitedCounter++;
    }


    @Override
    public void finalize() {
    }

    public long getFrontierCounter() {
        return frontierCounter;
    }

    public long getVisitedCounter() {
        return visitedCounter;
    }
}
