package ar.edu.itba.sia.test;

import java.util.Optional;

import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

public class SokobanRuleUp extends Moveable implements GPSRule {

	@Override
	public Integer getCost() {
		return 1;
	}

	@Override
	public String getName() {
		return "Up";
	}

	@Override
	public Optional<GPSState> evalRule(GPSState state) {
		SokobanState s = (SokobanState) state;
		GPSState next;
		if(!canMove(MOVE.UP,s)){
			return Optional.empty();
		}
		if(nextToBox(MOVE.UP,s)){
			if(moveToDeadlock(MOVE.UP, s)){
				return Optional.empty();
			}
			next = movePlayerWithBox(MOVE.UP,s);
		}else{
			next = movePlayer(MOVE.UP,s);
		}
		return Optional.of(next);
	}
}
