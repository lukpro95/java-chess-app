package gui.com;

import java.util.ArrayList;

import javax.swing.JLabel;

import movement.com.Coords;

public class SquareCreator {
	
	private JLabel selfSquare;
	private JLabel nextSquare;
	
	private ArrayList<Coords> squares;
	private int pieceActive;
	private int pieceX;
	private int pieceY;
	private int[][] playGround;
	private ArrayList<JLabel> squaresToSelect = new ArrayList<>();

	public SquareCreator(int pieceActive, int pieceX, int pieceY, ArrayList<Coords> squares, int[][] playGround) {
		
		this.pieceActive = pieceActive;
		this.pieceX = pieceX;
		this.pieceY = pieceY;
		this.playGround = playGround;
		this.squares = squares;
		
		createSquares();
		
	}
	
	private void createSquares() {
		
		int pieceSize = getPieceSize();
		
		selfSquare = new Library().getTempSquare(pieceActive, pieceActive);
		selfSquare.setBounds(2+pieceX*pieceSize, 2+pieceY*pieceSize, pieceSize, pieceSize);
		
		for(Coords square: squares) {
			
			int dx = square.getX();
			int dy = square.getY();
			int dActive = playGround[dx][dy];
			
			nextSquare = new Library().getTempSquare(dActive, pieceActive);
			nextSquare.setBounds(3+square.getX()*pieceSize, 2+square.getY()*pieceSize, pieceSize, pieceSize);
			
			squaresToSelect.add(nextSquare);
			
		}
		
	}
	
	public ArrayList<JLabel> getSquaresToSelect(){
		return squaresToSelect;
	}
	
	private int getPieceSize() {
		return 92;
	}

}
