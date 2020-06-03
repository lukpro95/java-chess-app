package chess.com;
import java.util.ArrayList;

import pieces.com.Piece;

public class Board {
	
	public ArrayList<Integer> living = new ArrayList<>();
	public ArrayList<Integer> removed = new ArrayList<>();
	public int[][] playGround = new int[8][8];
	
	public Board() {
		setBoard(playGround);
	}
	
	public void resetBoard() {
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				playGround[i][j] = 0;
			}
		}
		
	}
	
	public void piecesStatus() {
		for(int i = 0; i < 8; i++) {
			System.out.println();
		for(int j = 0; j < 8; j++) {
			System.out.print(playGround[j][i] + "\t");
		}
		}
		
		System.out.println("\n Active pieces: " + living);
		System.out.println("\n Removed pieces: " + removed);
	}
	
	// ------------------------------ Setters --------------------------------
	
	public void setBoard(int[][] pG) {
		playGround = pG;
	}
	
	public void movePiece(Piece piece, int newX, int newY) {
		
		int active = piece.getActive();
		int oldX = piece.getX();
		int oldY = piece.getY();
		
		playGround[oldX][oldY] = 0;

		piece.setX(newX);
		piece.setY(newY);
		
		int newSquare = playGround[newX][newY];
		
		System.out.println("["+piece.getActive()+"]" + " " + piece.getTeam() + " " + piece.getType() + " moves to: " + (newX+1) + " " + (newY+1));
		
		if(active < 59) {
			
			//Securing 0-Square
			if(newSquare == 0) {
				playGround[newX][newY] = active;
			}
			
			//Capturing Enemy-Square
			if(piece.getEnemies().contains(newSquare)) {
				int tempDefeated = newSquare;
				playGround[newX][newY] = active;
				
				removed.add(tempDefeated);
				removePiece(tempDefeated);
			}
			
		}
		
		if(active > 60) {
			
			//Securing 0-Square
			if(newSquare == 0) {
				playGround[newX][newY] = active;
			}
			
			//Capturing Enemy-Square
			if(piece.getEnemies().contains(newSquare)) {
				int tempDefeated = newSquare;
				playGround[newX][newY] = active;
				
				removed.add(tempDefeated);
				removePiece(tempDefeated);
			}
			
		}
		
		piece.getLabel().setBounds(2+newX*92, 2+newY*92, 90, 90);
	}
	
	private void removePiece(int defeated) {
		
		for(Piece piece: Piece.LivingPiecesW) {
			if(piece.getActive() == defeated) {
				piece.destroy();
			}
		}
		
		for(Piece piece: Piece.LivingPiecesB) {
			if(piece.getActive() == defeated) {
				piece.destroy();
			}
		}
		
		System.out.println("Defeated: " + removed);
		
		for (int i = 0; i < living.size(); i++) {
			if(living.get(i) == defeated) {
				living.remove(i);
			}
		}
		
	}
	
}