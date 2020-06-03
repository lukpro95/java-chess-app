package movement.com;

import java.util.ArrayList;

import chess.com.Game;
import pieces.com.Piece;

public class DangerousSquares {

	private Piece 				piece 			= null;
	private ArrayList<Coords> 	allowedMoves 	= null;
	
	public DangerousSquares(int active) {
		
		allowedMoves = new ArrayList<>();
		
		for(Piece piece: Game.pieces) {
			if(piece.getActive() > 0 && active < 0 || piece.getActive() < 0 && active > 0) {
				this.piece = piece;
				checkPaths();
			}
		}
		
	}
	
	private void checkPaths() {
		
		int[][] playGround = piece.getBoard().playGround;
		
		for(int[] e: piece.getMoves()) {
		for(int z = 1 ; z < piece.getRange() ; z++) {
	    		
	    	    int x1 = piece.getX() + e[0]*z;
	    	    int y1 = piece.getY() + e[1]*z;
	    	    
	    		if((x1 < 8 && y1 < 8) && (x1 >= 0 && y1 >= 0)) {

	    			int nextSquare = playGround[x1][y1];

					if(piece.getEnemies().contains(nextSquare) && !(Math.abs(nextSquare) == 16)) {
						break;
					}
					
					else if(piece.getEnemies().contains(nextSquare) && (Math.abs(nextSquare) == 16) && z == 1) {
							allowedMoves.add(new Coords(x1, y1));
					}
					
					else if((nextSquare == 0 && !piece.getType().equals("Pawn")) || (nextSquare == 0 && piece.getType().equals("Pawn") && x1 != piece.getX())) {
						allowedMoves.add(new Coords(x1, y1));
					}
	    				
					else if((piece.getAllies().contains(nextSquare) && !piece.getType().equals("Pawn")) || (piece.getAllies().contains(nextSquare) && x1 != piece.getX())) {
						allowedMoves.add(new Coords(x1, y1));
						break;
					}
	    				
	    		}
	    }}
		
	}
	
	public ArrayList<Coords> getArray() {
		return this.allowedMoves;
	}

}