package pieces.com;

import chess.com.Board;
import chess.com.Game;
import movement.com.Coords;
import movement.com.Reductor;

public class PieceKing extends Piece {
	
	private int range = 2;
	
	private int[][] moves = {
			{1, 1},
			{1, -1},
			{-1, 1},
			{-1, -1},
			{0, 1},
			{0, -1},
			{1, 0},
			{-1, 0}
	};
	
	public PieceKing(Game game, Board board, int active, int x, int y) {
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

	    			int nextSquare = playGround[x1][y1];

					if(enemies.contains(nextSquare)) {
						allowedMoves.add(new Coords(x1, y1));
						break;
					}
					
					else if(nextSquare == 0) {
						allowedMoves.add(new Coords(x1, y1));
						checkCastling(x1);
					}
	    				
					else if(allies.contains(nextSquare)) {
						break;
					}
					
	    		}
	    }}

		Reductor reductor = new Reductor(piece);
		allowedMoves = reductor.getReducedArray();
		
	}
	
	private void checkCastling(int x1) {
		if(this.getMoveCount() == 0) {
			for(Piece piece: Game.pieces) {
				if(piece.getType().equals("Rook") && allies.contains(piece.getActive()) && piece.getMoveCount() == 0) {
					if(piece.getX() == 0 && piece.getY() == y) {
						if(playGround[x-1][y] == 0 && playGround[x-2][y] == 0 && playGround[x-3][y] == 0) {
							allowedMoves.add(new Coords(x1-1, y));
						}
					}
					
					if(piece.getX() == 7 && piece.getY() == y) {
						if(playGround[x+1][y] == 0 && playGround[x+2][y] == 0) {
							allowedMoves.add(new Coords(x1+1, y));
						}
					}
				}
			}
		}	
	}
	
	//------------------------------------ Uniques for King ------------------------------------

}