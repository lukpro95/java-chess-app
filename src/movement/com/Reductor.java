package movement.com;

import java.util.ArrayList;

import chess.com.Board;
import pieces.com.Piece;

public class Reductor extends Board {
	
	private ArrayList<Coords> allowedMoves = new ArrayList<>();
	private ArrayList<Coords> reducedMoves = new ArrayList<>();

	public Reductor(Piece piece) {
		
		allowedMoves.clear();
		reducedMoves.clear();
		
		allowedMoves = piece.getAllowedMoves();
		DangerousSquares dangerousSquares = new DangerousSquares(piece.getActive());
		reducedMoves = dangerousSquares.getArray();
		
		reduceKingMovements();
		
	}
		
	private void reduceKingMovements() {
		
		ArrayList<Coords> remover = new ArrayList<>();
		remover.clear();
		
		for(Coords e: allowedMoves) {
		for(Coords f: reducedMoves) {
			if(e.getX() == f.getX() && e.getY() == f.getY()) {
				remover.add(e);
			}
		}}
		
		allowedMoves.removeAll(remover);
		
	}
	
	public ArrayList<Coords> getReducedArray(){
		return this.allowedMoves;
	}
	
}