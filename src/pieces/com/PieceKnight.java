package pieces.com;

import chess.com.Board;
import chess.com.Game;
import movement.com.Coords;
import movement.com.Tolerance;

public class PieceKnight extends Piece {
	
	private int range = 2;
	
	private int[][] moves = {
			{1, 2},
			{1, -2},
			{-1, 2},
			{-1, -2},
			{2, 1},
			{2, -1},
			{-2, 1},
			{-2, -1}
	};
	
	public PieceKnight(Game game, Board board, int active, int x, int y) {
		super(game, board, active, x, y);
		super.moves = this.moves;
		super.range = this.range;
	}
	

	protected void checkMovement() {
		
		super.clear();
		
		for(int[] e: moves) {
			for(int z = 1 ; z < 2 ; z++) {
		    		
	    	    int x1 = x + e[0]*z;
	    	    int y1 = y + e[1]*z;
	    	    
	    		if((x1 < 8 && y1 < 8) && (x1 >= 0 && y1 >= 0)) {

	    			int nextSquare = super.playGround[x1][y1];

					if(enemies.contains(nextSquare)) {
						allowedMoves.add(new Coords(x1, y1));
						break;
					}
					
					else if(nextSquare == 0) {
						allowedMoves.add(new Coords(x1, y1));
					}
	    				
					else if(allies.contains(nextSquare)) {
						break;
					}
	    				
	    		}
			}
		}
		
		new Tolerance(game, piece);
		
	}
	
}