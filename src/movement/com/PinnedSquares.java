package movement.com;

import java.util.ArrayList;

import chess.com.Game;
import pieces.com.Piece;
import questions.com.Pin;

public class PinnedSquares {
	
	private Piece 				mainPiece		= null;
	private Piece 				piece 			= null;
	private ArrayList<Coords> 	allowedMoves 	= null;
	
	private int defender = 0;
	
	public PinnedSquares(Piece mainPiece) {
		this.mainPiece = mainPiece;
		
		allowedMoves = new ArrayList<>();
	
		for(Piece piece: Game.pieces) {
			if(piece.getActive() > 0 && mainPiece.getActive() < 0 || piece.getActive() < 0 && mainPiece.getActive() > 0) {
				this.piece = piece;
				checkPiecesThatCanPin();
			}
		}
		
	}
	
	private void checkPiecesThatCanPin() {
		
		Pin checkPin = new Pin(piece);
		
		if(checkPin.isPinningPiece()) {
			checkPaths();
		}
		
	}
	
	private void checkPaths() {
		
		for(int[] e: piece.getMoves()) {
		for(int z = 1 ; z < 8 ; z++) {
	    		
	    	    int x1 = piece.getX() + e[0]*z;
	    	    int y1 = piece.getY() + e[1]*z;
	    	    
	    		if((x1 < 8 && y1 < 8) && (x1 >= 0 && y1 >= 0)) {

	    			int nextSquare = piece.getBoard().playGround[x1][y1];

					if(piece.getEnemies().contains(nextSquare)) {
						
						for(int goFurther = 1; goFurther <= 8; goFurther++) {
							int x2 = x1 + e[0]*goFurther;
							int y2 = y1 + e[1]*goFurther;
							
							if((x2 < 8 && y2 < 8) && (x2 >= 0 && y2 >= 0)) {
								int dangerSquare = piece.getBoard().playGround[x2][y2];
								
								if(piece.getEnemies().contains(dangerSquare) && (Math.abs(dangerSquare) == 16)) {
									defender = nextSquare;
									
									for(int goBack = goFurther+z; goBack > 0; goBack--) {
										int x3 = x2 - e[0]*goBack;
										int y3 = y2 - e[1]*goBack;
										
										allowedMoves.add(new Coords(x3, y3));
									}
									
								}
								
								else if(piece.getEnemies().contains(dangerSquare) && !(Math.abs(dangerSquare) == 16)){
									z=8;
									break;
								}
							}
							
						}
						
					}
					
					else if(piece.getAllies().contains(nextSquare)) {
						break;
					}
					
	    		}
	    }}
		
		reduceMovementOfPinnedPiece();
		
	}
	
	private void reduceMovementOfPinnedPiece() {
		
		ArrayList<Coords> tempArray = new ArrayList<>();
		tempArray.clear();
		
		for(Coords e: mainPiece.getAllowedMoves()) {
		for(Coords f: allowedMoves) {
			if(e.getX() == f.getX() && e.getY() == f.getY()) {
				tempArray.add(e);
				break;
			}
		}}
		
		if(defender == mainPiece.getActive()) {
			mainPiece.setAllowedMoves(tempArray);
		}
		
	}

	public int getDefender() {
		return defender;
	}

}