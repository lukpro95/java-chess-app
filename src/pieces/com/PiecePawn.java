package pieces.com;

import chess.com.Board;
import chess.com.Game;
import movement.com.Coords;
import movement.com.Tolerance;

public class PiecePawn extends Piece {
	
	private int range = 2;
	
	private int[][] moves;
	
	public PiecePawn(Game game, Board board, int active, int x, int y) {
		super(game, board, active, x, y);
		super.range = this.range;
		setUp();
	}
	
	private void setUp() {
		if(getTeam().equals("White")) {
			
			int[][] moves = {
					{-1, -1},
					{1, -1},
					{0, -1}
			};
			
			this.moves = moves;
		}
		else if(getTeam().equals("Black")) {
			
			int[][] moves = {
					{-1, 1},
					{1, 1},
					{0, 1}
			};
			
			this.moves = moves;
		}
		
		super.moves = this.moves;
		
	}
	
	protected void checkMovement() {
		
		super.clear();
		
		for(int[] e: moves) {

			int x1 = x+e[0];
			int y1 = y+e[1];
			
			if(x1 < 8 && y1 < 8 && x1 >= 0 && y1 >= 0) {
				int nextSquare = super.playGround[x1][y1];
				
				if(nextSquare == 0 && x1 == x){
					
					// Pawn Special
					if(getMoveCount() == 0) {
						if(super.playGround[x1][y1+e[1]] == 0) { 
							super.allowedMoves.add(new Coords(x1, y1+e[1]));
						}
					}
					
					super.allowedMoves.add(new Coords(x1, y1));
				}
				
				if(enemies.contains(nextSquare) && x1 != x){
					super.allowedMoves.add(new Coords(x1, y1));
				}
	    	}
			
		}
		
		new Tolerance(game, piece);
		
	}
	
}