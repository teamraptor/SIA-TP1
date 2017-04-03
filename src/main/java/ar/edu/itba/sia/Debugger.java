package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.GPSNode;
import ar.edu.itba.sia.gps.GPSObserver;

public class Debugger implements GPSObserver{
    @Override
    public void start(GPSNode gpsNode) {
        System.out.println(gpsNode);
        Board board = ((Board)gpsNode.getState());
        System.out.println("ASUMPTIONS: " + board.getAsumptions());
        System.out.println("EMPTY CELLS: " + board.getEmptyCells());
    }

    @Override
    public void observe(GPSNode gpsNode) {
        System.out.println(gpsNode.getGenerationRule());
        System.out.println(gpsNode);
        Board board = ((Board)gpsNode.getState());
        System.out.println("ASUMPTIONS: " + board.getAsumptions());
        System.out.println("EMPTY CELLS: " + board.getEmptyCells());
    }

    @Override
    public void finalize() {
        System.out.println("NO TERMINA");
    }
}
