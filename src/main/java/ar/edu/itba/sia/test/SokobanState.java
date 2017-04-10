package ar.edu.itba.sia.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.sia.gps.api.GPSState;

public class SokobanState implements GPSState {
	
	byte[][] board;
	Point playerPos;
	List<Point> boxes;
	List<Point> goals;
	int completedBoxes;
	int height;
	int width;
	int hValue = -1;
	
	public SokobanState(byte[][] board, Point playerPos,List<Point> boxes,List<Point> goals, int width, int height, int completedBoxes) {
		this.board = board;
		this.height = height;
		this.width = width;
		this.boxes = boxes;
		this.completedBoxes = completedBoxes;
		this.playerPos = playerPos;
		this.goals = goals;
		if(height <=0 || width <= 0)
			throw new IllegalArgumentException("Illegal map size parameters");
	}
	public SokobanState(SokobanState original) {
		this(copyBoard(original.board),new Point(original.playerPos), copyBoxes(original.boxes),original.goals, original.width,original.height,original.completedBoxes);
	}
	
	public boolean hasWon(){
		return completedBoxes == boxes.size();
	}
	
	public void movePlayer(MOVE m){
		playerPos.setLocation((playerPos.getX() + m.getX()), playerPos.getY() + m.getY());
	}
	public void moveBox(MOVE m){
		for(Point b : boxes){
			if(b.x == playerPos.x + m.getX() && b.y == playerPos.y + m.getY()){
				b.setLocation(b.getX() + m.getX(),b.getY() + m.getY());
			}
		}
	}

	@Override
	public int hashCode() {
		int result = (int)(Math.pow(2,playerPos.x)* Math.pow(3,playerPos.y));
		for(Point p : boxes){
			result+= p.x*31 + p.y*103;
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SokobanState other = (SokobanState) obj;
		if(!playerPos.equals(other.playerPos))
			return false;
		if(!boxes.containsAll(other.boxes))
			return false;
		return true;
	}
	
	public static byte[][] copyBoard(byte[][] original){
		byte[][] copy = new byte[original.length][original[0].length];
		for(int i = 0; i<original.length;i++){
			for(int j = 0; j<original[0].length;j++){
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}
	
	public static List<Point> copyBoxes(List<Point> original){
		List<Point> copy = new ArrayList<Point>(original.size());
		for(Point p: original){
			copy.add(new Point(p.x,p.y));
		}
		return copy;
	}
	
	public byte[][] getBoard(){
		return board;
	}
	
	public Point getPlayerPos(){
		return playerPos;
	}
	
	public List<Point> getBoxes(){
		return boxes;
	}
	
	public int getCompletedBoxes() {
		return completedBoxes;
	}
	public void addCompletedBox(){
		completedBoxes++;
	}
	public void removeCompletedBox(){
		completedBoxes--;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	
	public List<Point> getGoals(){
		return this.goals;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("**************************************\n");
		for(int i = 0 ; i < getHeight() ; i++){
			for(int j = 0 ; j < getWidth() ; j++){
				sb.append(board[i][j]).append("\t");
			}
			sb.append("\n");
		}
		sb.append("**************************************\n");
		return sb.toString();
	}
	
	public int getHValue(){
		return hValue;
	}
	
	public void setHValue(int v){
		hValue = v;
	}
}
