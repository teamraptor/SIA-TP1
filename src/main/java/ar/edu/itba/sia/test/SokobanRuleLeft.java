package ar.edu.itba.sia.test;

import java.util.Optional;

import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

public class SokobanRuleLeft extends Moveable implements GPSRule {

	@Override
	public Integer getCost() {
		return 1;
	}

	@Override
	public String getName() {
		return "Left";
	}

	@Override
	public Optional<GPSState> evalRule(GPSState state) {
		SokobanState s = (SokobanState) state;
		GPSState next;
		if(!canMove(MOVE.LEFT,s)){
			return Optional.empty();
		}
		if(nextToBox(MOVE.LEFT,s)){
			if(moveToDeadlock(MOVE.LEFT, s)){
				return Optional.empty();
			}
			next = movePlayerWithBox(MOVE.LEFT,s);
		}else{
			next = movePlayer(MOVE.LEFT,s);
		}
		return Optional.of(next);
	}
	
	
}
