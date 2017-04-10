package ar.edu.itba.sia.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DeadLockFinder {

	SokobanState s;
	byte[][] original;
	byte[][] matrix = null;
	int fil = 0;
	int col = 0;
	
	Queue<Point> goals;

	static final int FREE = 0;
	static final int WALL = 1;
	static final int BOX = 2;
	static final int GOAL = 4;
	static final int BOX_ON_OBJECTIVE = 6;
	static final int PLAYER = 8;
	static final int PLAYER_ON_OBJECTIVE = 12;
	static final int DEADLOCK = 16;
	static final int SAFEZONE = 9;

	public DeadLockFinder(SokobanState s) {
		this.s = s;
		this.original = s.getBoard();
		fil = original.length;
		col = original[0].length;
		this.matrix = new byte[fil][col];
		goals = new LinkedList<Point>();
		copyOnlyObjectivesAndWalls(original,matrix,fil,col);
	}
	
	private void copyOnlyObjectivesAndWalls(byte[][] original, byte[][] target, int fil, int col){
		for(int i=0; i<fil; i++){
			for(int j=0; j<col; j++){
				int x = original[i][j];
				if(x == BOX || x == PLAYER)
					x = FREE;
				else if(x == BOX_ON_OBJECTIVE || x == PLAYER_ON_OBJECTIVE || x == GOAL){
					x = GOAL;
					goals.add(new Point(i,j));
				}
				target[i][j] = (byte)x;
			}
		}
	}
	
	/**
	 * Calculates deadlocks for the matrix given to the constructor
	 * @return The state with the matrix with the deadlocks marked or null if puzzle can't be solved
	 */
	public SokobanState getStateWithDeadLocks(){
		
		while(!goals.isEmpty()){
			Point p = goals.poll();
			List<Point> canMoveFrom = getNeighboursThatCanMoveToMe(p);
			for(Point p2: canMoveFrom){
				if(get(p2)!=GOAL && get(p2)!=SAFEZONE){
					set(p2,SAFEZONE);
					goals.offer(p2);
				}
			}
		}
		
		
		byte[][] res = new byte[fil][col];
		
		for(int i=0; i<fil; i++){
			for(int j=0; j<col; j++){
				int v = matrix[i][j];
				if(v == WALL || v == GOAL || v == SAFEZONE)
					res[i][j] = original[i][j];
				else if(v == FREE){
					res[i][j] = (byte)(original[i][j]+DEADLOCK);
				} else
					return null;
			}
		}
		
		return new SokobanState(res, s.getPlayerPos(), s.getBoxes(), s.getGoals(), s.getWidth(), s.getHeight(), s.getCompletedBoxes());
	}
	
	public List<Point> getNeighboursThatCanMoveToMe(Point p){
		List<Point> list = new ArrayList<Point>();
		if(p.x-2>=0 && !isWall(p.x-2,p.y) && !isWall(p.x-1,p.y))
			list.add(new Point(p.x-1,p.y));
		if(p.x+2<fil && !isWall(p.x+2,p.y) && !isWall(p.x+1,p.y))
			list.add(new Point(p.x+1,p.y));
		if(p.y-2>=0 && !isWall(p.x,p.y-2) && !isWall(p.x,p.y-1))
			list.add(new Point(p.x,p.y-1));
		if(p.y+2<col && !isWall(p.x,p.y+2) && !isWall(p.x,p.y+1))
			list.add(new Point(p.x,p.y+1));
		return list;
	}
	
	private boolean isWall(int x, int y){
		return matrix[x][y] == WALL;
	}
	
	private int get(Point p){
		return matrix[p.x][p.y];
	}
	
	private void set(Point p, int val){
		matrix[p.x][p.y] = (byte)val;
	}
	
}
