package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.GPSNode;
import ar.edu.itba.sia.gps.GPSObserver;

public class NodeCounter implements GPSObserver {
    private long counter;
    @Override
    public void start(GPSNode gpsNode) {
        counter = 1;
    }

    @Override
    public void observe(GPSNode gpsNode) {
        counter++;
        System.out.println(counter);
    }

    @Override
    public void finalize() {

    }

    public long getCounter() {
        return counter;
    }
}
