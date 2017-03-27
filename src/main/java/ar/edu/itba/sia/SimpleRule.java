package ar.edu.itba.sia;

import ar.edu.itba.sia.Board.CellContent;
import ar.edu.itba.sia.gps.api.*;


import java.util.Optional;

public class SimpleRule implements GPSRule {

	private final CellContent ruleType;
	
	public SimpleRule(CellContent ruleType) {
		this.ruleType = ruleType;
	}

	public Optional<GPSState> evalRule(GPSState arg0) {
		// TODO Auto-generated method stub
		Board board = ((Board)arg0).copy();
		for(int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				if (board.getPiece(i, j) == CellContent.EMPTY) {
					board.setPiece(i, j, this.ruleType);
					if (board.isValid()) {
						return Optional.of(board);
					} else {
						return Optional.empty();
					}
				}
			}
		}

		return null;
	}

	public Integer getCost() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE - Integer.MIN_VALUE;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.ruleType.toString().substring(0, 1) + this.ruleType.toString().toLowerCase().substring(1) + " Rule";
	}

}
