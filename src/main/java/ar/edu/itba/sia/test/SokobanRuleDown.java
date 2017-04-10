package ar.edu.itba.sia.test;

import java.util.Optional;

import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

public class SokobanRuleDown extends Moveable implements GPSRule {

	@Override
	public Integer getCost() {
		return 1;
	}

	@Override
	public String getName() {
		return "Down";
	}

	@Override
	public Optional<GPSState> evalRule(GPSState state) {
		SokobanState s = (SokobanState) state;
		GPSState next;
		if(!canMove(MOVE.DOWN,s)){
			return Optional.empty();
		}
		if(nextToBox(MOVE.DOWN,s)){
			if(moveToDeadlock(MOVE.DOWN, s)){
				return Optional.empty();
			}
			next = movePlayerWithBox(MOVE.DOWN,s);
		}else{
			next = movePlayer(MOVE.DOWN,s);
		}
		return Optional.of(next);
	}

}
