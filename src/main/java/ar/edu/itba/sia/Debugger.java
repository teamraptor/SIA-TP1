package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.GPSNode;
import ar.edu.itba.sia.gps.GPSObserver;

public class Debugger implements GPSObserver{

    GPSNode last;

    @Override
    public void start(GPSNode gpsNode) {
    }

    @Override
    public void observeFrontier(GPSNode gpsNode) {

    }

    @Override
    public void observeVisited(GPSNode gpsNode) {
        observe(gpsNode);
        this.last = gpsNode;
    }


    public void observe(GPSNode gpsNode) {
        Board board = ((Board)gpsNode.getState());
        System.out.println("ASUMPTIONS: " + board.getAssumptions());
        System.out.println("EMPTY CELLS: " + board.getEmptyCells());
        System.out.println("SURE: " + board.getSure());
        System.out.println(gpsNode.getGenerationRule());
        System.out.println(gpsNode);

    }

    @Override
    public void finalize() {
    }
}
