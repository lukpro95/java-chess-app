package questions.com;

import pieces.com.Piece;

public class Pin {
	
	private boolean pinningPiece = false;
	private String[] pinningPieces = {"Queen", "Rook", "Bishop"};
	
	public Pin(Piece piece) {

		for(String e: pinningPieces) {
			if(e.contentEquals(piece.getType())) {
				pinningPiece = true;
			}
		}
		
	}
	
	public boolean isPinningPiece() {
		return pinningPiece;
	}

}
