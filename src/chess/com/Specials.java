package chess.com;

import pieces.com.Piece;

public class Specials {
	
	private Game game;
	private Board board;
	private Piece piece;
	
	private int x;
	private int y;
	private int promote;

	public Specials(Game game, Piece piece) {
		
		this.game = game;
		this.board = game.getBoard();
		this.x = piece.getX();
		this.y = piece.getY();
		this.piece = piece;
		
		if(piece.getType().equals("Pawn")){
			checkPawnPromotion();
		}
		
		if(piece.getType().equals("King")){
			checkKingCastling();
		}
		
	}
	
	private void checkPawnPromotion() {
		
		if(piece.getY() == 0 && piece.getTeam().equals("White")) {
			new Promotion(game, piece);
		}
		
		if(piece.getY() == 7 && piece.getTeam().equals("Black")) {
			new Promotion(game, piece);
		}
		
	}
	
	private void checkKingCastling() {
		
		if(x == 6 && piece.getMoveCount() == 0) {
			if(piece.getTeam().equals("White") && board.living.contains(14)) {
				Piece.LivingPiecesW.get(13).castlingX(5);
			}
			
			if(piece.getTeam().equals("Black") && board.living.contains(-14)) {
				Piece.LivingPiecesB.get(13).castlingX(5);
			}
		}
		
		if(x == 2 && piece.getMoveCount() == 0) {
			if(piece.getTeam().equals("White") && board.living.contains(13)) {
				Piece.LivingPiecesW.get(12).castlingX(3);
			}
			
			if(piece.getTeam().equals("Black") && board.living.contains(-13)) {
				Piece.LivingPiecesB.get(12).castlingX(3);
			}
		}

	}
	
	// --------------------------------------------  SERVER SIDE ----------------------------------------
	
	public Specials(Game game, Piece piece, int promote) {
		
		this.piece = piece;
		this.game = game;
		this.x = piece.getX();
		this.y = piece.getY();
		this.promote = promote;
		
		changePawn();
		
	}
	
	private void changePawn() {
		
		if(y == 0 && piece.getTeam().equals("White")) {
			new Promotion(game, piece, promote);
		}
		
		if(y == 7 && piece.getTeam().equals("Black")) {
			new Promotion(game, piece, promote);
		}
		
	}
	
}