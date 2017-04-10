package ar.edu.itba.sia.test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GraphicBoard extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static JLabel[][] labels;
	private ImageIcon player;
	private ImageIcon wall;
	private ImageIcon target;
	private ImageIcon empty;
	private ImageIcon box;
	private ImageIcon boxOnTarget;
	private ImageIcon dead;
	private static int w = 10;
	private static int h = 10;
	private static GraphicBoard instance;
	private static boolean activated  = false;
	 
	private GraphicBoard() throws IOException{
		super("Tablero");
			player = new ImageIcon("img/player.png");
			empty = new ImageIcon("img/empty.png");
			box = new ImageIcon("img/boxtarget.png");
			boxOnTarget = new ImageIcon("img/box.png");
			wall = new ImageIcon("img/WALL.png");
			target = new ImageIcon("img/target.png");
			dead = new ImageIcon("img/deadlock.png");
			setLayout(new GridLayout(h,w,0,0));
			for(int j=0; j<h;j++){
				for(int i = 0; i<w;i++){
				labels[j][i] = new JLabel();
				add(labels[j][i]);
			}
		}
		setSize(w*32 +5,(h+1)*32);
		setVisible(true);
		
	}
	public static void activate(){
		activated = true;
	}
	public static GraphicBoard getInstance(){
		if(activated){
			if(instance == null){
				try {
					instance = new GraphicBoard();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return instance;
		}
		return null;
	}
	public static boolean isGraphicsActive(){
		return activated;
	}
	public static void setBoardSize(int w,int h){
		GraphicBoard.w = w;
		GraphicBoard.h = h;
		labels = new JLabel[h][w];
	}
	public void setBoard(SokobanState b){
		for(int j=0; j<h;j++){
			for(int i = 0; i<w;i++){
				switch(b.getBoard()[j][i]){
				case 0:
					labels[j][i].setIcon(empty);
					break;
				case 1:
					labels[j][i].setIcon(wall);
					break;
				case 2:
					labels[j][i].setIcon(box);
					break;
				case 4:
					labels[j][i].setIcon(target);
					break;
				case 6:
					labels[j][i].setIcon(boxOnTarget);
					break;
				case 8:
				case 12:
				case 24:
					labels[j][i].setIcon(player);
					break;
				case 16:
					labels[j][i].setIcon(dead);
					break;
				default:
					labels[j][i].setIcon(empty);
				
				}
			}
		}
	}
	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
	}
}
