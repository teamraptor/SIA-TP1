package ar.edu.itba.sia.test;

import java.util.Optional;

import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

public class SokobanRuleRight extends Moveable implements GPSRule{

	@Override
	public Integer getCost() {
		return 1;
	}

	@Override
	public String getName() {
		return "Right";
	}

	@Override
	public Optional<GPSState> evalRule(GPSState state) {
		SokobanState s = (SokobanState) state;
		GPSState next;
		if(!canMove(MOVE.RIGHT,s)){
			return Optional.empty();
		}
		if(nextToBox(MOVE.RIGHT,s)){
			if(moveToDeadlock(MOVE.RIGHT, s)){
				return Optional.empty();
			}
			next = movePlayerWithBox(MOVE.RIGHT,s);
		}else{
			next = movePlayer(MOVE.RIGHT,s);
		}
		return Optional.of(next);
	}

}
