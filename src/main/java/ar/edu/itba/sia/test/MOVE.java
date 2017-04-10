package ar.edu.itba.sia.test;

public enum MOVE {
	LEFT(0,-1),
	RIGHT(0,1),
	UP(-1,0),
	DOWN(1,0);
	
private final int x;
private final int y;
    
    MOVE(final int x,final int y) {
    	this.x = x;
    	this.y = y;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
}
