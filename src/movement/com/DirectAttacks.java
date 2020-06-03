package movement.com;

import java.util.ArrayList;

import chess.com.Game;
import pieces.com.Piece;

public class DirectAttacks {
	
	private Piece mainPiece;
	private Piece piece;
	private ArrayList<Coords> allowedMoves = new ArrayList<>();
	private int[][] moves;
	
	private boolean directAttack = false;
	private static ArrayList<Integer> attackers = new ArrayList<>();
	
	public DirectAttacks(Piece mainPiece) {
		
		this.mainPiece = mainPiece;
		allowedMoves.clear();
		
		if(mainPiece.getActive() > 0) {
			for(int piece = 0; piece < Game.piecesB.size(); piece++) {
				this.piece = Game.piecesB.get(piece);
				start(Game.piecesB.get(piece));
			}
		}
		else {
			for(int piece = 0; piece < Game.piecesW.size(); piece++) {
				this.piece = Game.piecesW.get(piece);
				start(Game.piecesW.get(piece));
			}
		}
	}
	
	private void start(Piece piece) {
		
		if(piece.getType().equals("Queen")) {
			loadMoves();
			checkPaths(8, 8);
		}
		else if(piece.getType().equals("Rook")) {
			loadMoves();
			checkPaths(4, 8);
		}
		else if(piece.getType().equals("Bishop")) {
			loadMoves();
			checkPaths(4, 8);
		}
		else if(piece.getType().equals("Pawn")) {
			loadMoves();
			checkPaths(2, 2);
		}
		else if(piece.getType().equals("Knight")){
			loadMoves();
			checkPaths(8, 2);
		}
	}
	
	private void loadMoves() {
		moves = piece.getMoves();
	}
	
	private void checkPaths(int maxI, int maxZ) {
		
		int x = piece.getX();
		int y = piece.getY();
		
		ArrayList<Integer> allies = piece.getAllies();
		ArrayList<Integer> enemies = piece.getEnemies();
		
		int[][] playGround = piece.getBoard().playGround;
		
		for(int i = 0 ; i < maxI ; i++) {
		for(int z = 1 ; z < maxZ ; z++) {
	    		
	    	    int x1 = x + moves[i][0]*z;
	    	    int y1 = y + moves[i][1]*z;
	    	    
	    		if((x1 < 8 && y1 < 8) && (x1 >= 0 && y1 >= 0)) {

	    			int nextSquare = playGround[x1][y1];
	    			
	    			if(enemies.contains(nextSquare) && (nextSquare == 16 || nextSquare == -16)){
	    				directAttack = true;
	    				
	    				if(!attackers.contains(piece.getActive())) {
	    					attackers.add(piece.getActive());
	    				}
	    				
						for(int goBack = z; goBack > 0; goBack--) {
							int x2 = x1 - moves[i][0]*goBack;
							int y2 = y1 - moves[i][1]*goBack;
							
							allowedMoves.add(new Coords(x2, y2));
						}
	    			}

					if(enemies.contains(nextSquare)) {
	    				break;
					}

					if(allies.contains(nextSquare)) {
						break;
					}
					
	    		}
	    }}
		
		sort();
		
	}
	
	private void sort() {
		ArrayList<Coords> tempArray = new ArrayList<>();
		tempArray.clear();
		
		for(Coords element: mainPiece.getAllowedMoves()) {
			for(Coords subject: allowedMoves) {
				if((element.getX() == subject.getX()) && (element.getY() == subject.getY())) {
					tempArray.add(new Coords(element.getX(), element.getY()));
				}
			}
		}
		
		if(directAttack == true) {
			mainPiece.setAllowedMoves(tempArray);
		}
		
	}

	public static int getCount() {
		return attackers.size();
	}
	
	public static String getAttacker() {
		if(attackers.get(0) < 59) {
			return "White";
		}
		else {
			return "Black";
		}
	}
	
	public static void resetCount() {
		attackers.clear();
	}
	
}