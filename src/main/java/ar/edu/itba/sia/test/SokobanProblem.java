package ar.edu.itba.sia.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

public class SokobanProblem implements GPSProblem {

	public static int heuristic = 2;

	List<GPSRule> rules = new ArrayList<>();
	GPSState st;
	GPSState fin;

	public SokobanProblem(GPSState s) {
		st = s;
		setRules();
	}

	private void setRules() {
		rules.add(new SokobanRuleDown());
		rules.add(new SokobanRuleLeft());
		rules.add(new SokobanRuleRight());
		rules.add(new SokobanRuleUp());
	}

	@Override
	public GPSState getInitState() {
		return st;
	}

	@Override
	public boolean isGoal(GPSState state) {
		SokobanState s = (SokobanState) state;
		return s.hasWon();
	}

	@Override
	public List<GPSRule> getRules() {
		return rules;
	}

	@Override
	public Integer getHValue(GPSState state) {
		switch (heuristic) {
		case 1:
			return getHValue1(state);
		case 2:
			return getHValue2(state);
		case 3:
			return getHValue3(state);
		case 4:
			return getHValue4(state);
		default:
			return getHValue2(state);
		}
	}

	private Integer getHValue1(GPSState state) {
		SokobanState s = (SokobanState) state;
		int totalDistance = 0;
		for (Point box : s.getBoxes()) {
			int min = s.getWidth() + s.getHeight();
			for (Point goal : s.getGoals()) {
				int dist = Math.abs(box.x - goal.x) + Math.abs(box.y - goal.y);
				if (dist < min)
					min = dist;
			}
			totalDistance += min;
		}
		return totalDistance;
	}

	public Integer getHValue2(GPSState state) {
		SokobanState s = (SokobanState) state;
		if (s.getHValue() == -1) {
			int totalPlayerToBoxDistance = Integer.MAX_VALUE;
			int totalBoxToTargetDistance = 0;
			for (Point b : s.boxes) {
				
				int aux = Math.abs((s.playerPos.x - b.x)) + Math.abs((s.playerPos.y - b.y))-1;
				if (aux < totalPlayerToBoxDistance) {
					totalPlayerToBoxDistance = aux;
				}
				
				int shortest = Integer.MAX_VALUE;
				
				for (Point g : s.goals) {
					int dist = Math.abs((g.x - b.x)) + Math.abs((g.y - b.y));
					if (dist < shortest) {
						shortest = dist;
					}
				}
				totalBoxToTargetDistance += shortest;

			}
			s.setHValue(totalBoxToTargetDistance+ totalPlayerToBoxDistance);
			return totalBoxToTargetDistance + totalPlayerToBoxDistance;
		} else {
			return s.getHValue();
		}
	}
	
	public Integer getHValue3(GPSState state) {
		SokobanState s = (SokobanState) state;
		if (s.getHValue() == -1) {
			int totalPlayerToBoxDistance = Integer.MAX_VALUE;
			int totalBoxToTargetDistance = 0;
			for (Point b : s.boxes) {
				
				int aux = Math.abs((s.playerPos.x - b.x)) + Math.abs((s.playerPos.y - b.y))-1;
				if (aux < totalPlayerToBoxDistance) {
					totalPlayerToBoxDistance = aux;
				}
				
				int shortest = Integer.MAX_VALUE;
				
				for (Point g : s.goals) {
					int dist = Math.abs((g.x - b.x)) + Math.abs((g.y - b.y));
					if (dist < shortest && ((s.getBoard()[g.x][g.y] & TILE.BOX.getValue()) == 0)) {
						shortest = dist;
					}
				}
				totalBoxToTargetDistance += shortest;

			}
			s.setHValue(totalBoxToTargetDistance + totalPlayerToBoxDistance  - s.completedBoxes*10);
			return totalBoxToTargetDistance + totalPlayerToBoxDistance - - s.completedBoxes*10;
		} else {
			return s.getHValue();
		}
	}
	public Integer getHValue4(GPSState state) {
		SokobanState s = (SokobanState) state;
		if (s.getHValue() == -1) {
			int totalPlayerToBoxDistance = Integer.MAX_VALUE;
			for (Point b : s.boxes) {
				int aux = Math.abs((s.playerPos.x - b.x)) + Math.abs((s.playerPos.y - b.y))-1;
				if (aux < totalPlayerToBoxDistance && ((s.getBoard()[b.x][b.y] & TILE.TARGET.getValue()) == 0)) {
					totalPlayerToBoxDistance = aux;
				}
			}
			s.setHValue(totalPlayerToBoxDistance  - s.completedBoxes*10);
			return totalPlayerToBoxDistance  - s.completedBoxes*10;
		} else {
			return s.getHValue();
		}
	}
}
