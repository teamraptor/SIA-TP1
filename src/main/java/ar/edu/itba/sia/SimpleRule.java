package ar.edu.itba.sia;

import ar.edu.itba.sia.Board.CellContent;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

public class SimpleRule implements GPSRule {

	private final CellContent ruleType;
	
	public SimpleRule(CellContent ruleType) {
		this.ruleType = ruleType;
	}
	
	@Override
	public GPSState evalRule(GPSState arg0) throws NotAppliableException {
		// TODO Auto-generated method stub
		Board board = (Board)arg0;
		for(int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				if (board.getPiece(i, j) == CellContent.EMPTY) {
					board.setPiece(i, j, this.ruleType);
					if (board.isValid()) {
						return board;
					} else {
						board.setPiece(i, j, CellContent.EMPTY);
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public Integer getCost() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE - Integer.MIN_VALUE;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.ruleType.toString().substring(0, 1) + this.ruleType.toString().toLowerCase().substring(1) + " Rule";
	}

}
