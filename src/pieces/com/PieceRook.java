package pieces.com;

import chess.com.Board;
import chess.com.Game;
import movement.com.Coords;
import movement.com.Tolerance;

public class PieceRook extends Piece {
	
	private int range = 8;
	
	private int[][] moves = {
			{1, 0},
			{-1, 0},
			{0, 1},
			{0, -1}
	};
	
	public PieceRook(Game game, Board board, int active, int x, int y) {
		super(game, board, active, x, y);
		super.moves = this.moves;
		super.range = this.range;
	}
	
	protected void checkMovement() {
		
		super.clear();
		
		for(int[] e: moves) {
			for(int z = 1 ; z < 8 ; z++) {
		    		
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
	
	//------------------------------------ Uniques for Rook ------------------------------------
	
	public void castlingX(int newX) {
		int temp = playGround[x][y];
		playGround[x][y] = 0;
		this.x = newX;
		playGround[newX][y] = temp;
		
		super.label.setBounds(3+92*newX, 3+y*92, 90, 90);
	}
	
}