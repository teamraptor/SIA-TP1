package ar.edu.itba.sia.test;

public enum TILE {
	EMPTY(0),
	WALL(1),
	BOX(2),
	TARGET(4),
	PLAYER(8),
	DEADLOCK(16);
	
    private final int value;
    
    TILE(final int newValue) {
    	value = newValue;
    }
    
    public int getValue() { return value; }
}
