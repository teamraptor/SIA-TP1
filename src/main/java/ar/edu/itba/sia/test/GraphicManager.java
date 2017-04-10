package ar.edu.itba.sia.test;


import ar.edu.itba.sia.gps.GPSEngine;
import ar.edu.itba.sia.gps.GPSNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicManager {
	
	public static void startAnimation(int height, int width, List<SokobanState> solve){
			GraphicBoard.setBoardSize(width,height);
			GraphicBoard.activate();
			GraphicBoard.getInstance().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			for (int j = 0; j < solve.size(); j++) {
				GraphicBoard.getInstance().setBoard(solve.get(j));
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}

}
