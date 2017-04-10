package ar.edu.itba.sia.test;

import ar.edu.itba.sia.gps.api.GPSState;

public abstract class Moveable {

	public boolean canMove(MOVE m,SokobanState s){
		if((s.getBoard()[(int)s.getPlayerPos().getX() + m.getX()][(int)s.getPlayerPos().getY() + m.getY()] & TILE.WALL.getValue()) != 0){
			return false;
		}
        return !((s.getBoard()[(int) s.getPlayerPos().getX() + m.getX()][(int) s.getPlayerPos().getY() + m.getY()] & TILE.BOX.getValue()) != 0
                && ((s.getBoard()[(int) s.getPlayerPos().getX() + m.getX() * 2][(int) s.getPlayerPos().getY() + m.getY() * 2] & TILE.WALL.getValue()) != 0
                || (s.getBoard()[(int) s.getPlayerPos().getX() + m.getX() * 2][(int) s.getPlayerPos().getY() + m.getY() * 2] & TILE.BOX.getValue()) != 0));
    }
	
	public boolean nextToBox(MOVE m,SokobanState s){
		return ((s.getBoard()[(int)s.getPlayerPos().getX() + m.getX()][(int)s.getPlayerPos().getY() + m.getY()] & TILE.BOX.getValue()) != 0);
	}
	
	public GPSState movePlayer(MOVE m, SokobanState s){
		SokobanState next = new SokobanState(s);
		next.getBoard()[(int)s.getPlayerPos().getX()+m.getX()][(int)s.getPlayerPos().getY()+m.getY()] = (byte)(next.getBoard()[(int)s.getPlayerPos().getX() + m.getX()][(int)s.getPlayerPos().getY() + m.getY()] | TILE.PLAYER.getValue());
		next.getBoard()[(int)s.getPlayerPos().getX()][(int)s.getPlayerPos().getY()] = (byte)(next.getBoard()[(int)s.getPlayerPos().getX()][(int)s.getPlayerPos().getY()] & (255 - TILE.PLAYER.getValue()));
		next.movePlayer(m);
		return next;
	}
	
	public GPSState movePlayerWithBox(MOVE m, SokobanState s){
		SokobanState next = new SokobanState(s);
		next.getBoard()[(int)s.getPlayerPos().getX() + m.getX()*2 ][(int)s.getPlayerPos().getY() + m.getY()*2] = (byte)(next.getBoard()[(int)s.getPlayerPos().getX() + m.getX()*2][(int)s.getPlayerPos().getY() + m.getY()*2] | TILE.BOX.getValue());
		next.getBoard()[(int)s.getPlayerPos().getX() + m.getX()][(int)s.getPlayerPos().getY() + m.getY()] = (byte)((next.getBoard()[(int)s.getPlayerPos().getX() + m.getX()][(int)s.getPlayerPos().getY() + m.getY()] | TILE.PLAYER.getValue()) & (255 - TILE.BOX.getValue()));
		next.getBoard()[(int)s.getPlayerPos().getX()][(int)s.getPlayerPos().getY()] = (byte)(next.getBoard()[(int)s.getPlayerPos().getX()][(int)s.getPlayerPos().getY()] & (255 - TILE.PLAYER.getValue()));
		next.moveBox(m);
		next.movePlayer(m);
		if((next.getBoard()[(int)s.getPlayerPos().getX() + m.getX()*2 ][(int)s.getPlayerPos().getY() + m.getY()*2] & TILE.TARGET.getValue()) != 0) {
			next.addCompletedBox();
		}
		if(( next.getBoard()[(int)s.getPlayerPos().getX() + m.getX()][(int)s.getPlayerPos().getY() + m.getY()] & TILE.TARGET.getValue()) != 0){
			next.removeCompletedBox();
		}
		return next;
	}
	public boolean moveToDeadlock(MOVE m, SokobanState s){
		int target = s.getBoard()[(int)s.getPlayerPos().getX() + m.getX()*2 ][(int)s.getPlayerPos().getY() + m.getY()*2];
		return ((target & TILE.DEADLOCK.getValue()) != 0);
	}
}
