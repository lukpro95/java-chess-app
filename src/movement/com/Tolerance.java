package movement.com;

import chess.com.Game;
import pieces.com.Piece;

public class Tolerance {
	
	private Piece piece;
	
	public Tolerance(Game game, Piece piece){
		this.piece = piece;
		check();
	}
	
	private void check() {
		this.checkPins();
		this.checkDirectAttacks();
	}
	
	private void checkPins() {
		
		// Checking if the Pieces (or Squares occupied by these Pieces) are being pinned against its King
		// if yes - clearing every movement that can put King at check
		
		new PinnedSquares(piece);
		
	}
	
	private void checkDirectAttacks() {
		
		// Reducing every movement down to line of fire of enemy's check against King
		
		DirectAttacks.resetCount();
		new DirectAttacks(piece);
		
	}
	
}