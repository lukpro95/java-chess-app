package movement.com;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;

import ai.com.Delay;
import chess.com.Board;
import chess.com.Game;
import chess.com.Referee;
import chess.com.Specials;
import gui.com.GUI;
import gui.com.Sounds;
import gui.com.SquareCreator;
import pieces.com.Piece;
import players.com.Player;

public class MovePiece {
	
	private Game game;
	private GUI gui;
	private Board board;
	
	private ArrayList<JLabel> squaresToSelect = new ArrayList<>();
	
	private int[][] playGround;
	private Piece piece;

	public MovePiece(Game game) {
		this.game = game;
		this.gui = game.getGUI();
		this.board = game.getBoard();
		this.playGround = game.getBoard().playGround;
	}
	
	public MovePiece(Game game, Piece piece){
		this.game = game;
		this.gui = game.getGUI();
		this.board = game.getBoard();
		this.playGround = board.playGround;
		
		this.piece = piece;
		
		createSquares();
	}
	
	private void createSquares() {
		
		SquareCreator sC = new SquareCreator(piece.getActive(), piece.getX(), piece.getY(), piece.getAllowedMoves(), playGround);
		squaresToSelect = sC.getSquaresToSelect();
		
		for(JLabel squareToSelect: squaresToSelect) {
			squareToSelect.addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e) {
				
				moveToSquare(squareToSelect);

				// if the Piece is AI's, apply a random delay for AI up to Delay(value) seconds
				// Illusion for AI thinking
				
				for(Player player: Game.players) {
					if(!player.isReal() && player.doesPlay()) {
						Delay delay = new Delay(game, 4);
						delay.getDelay().start();
					}
				}
				
			}});
		}
		
	}
	
	public void moveToSquare(JLabel selectedSquare) {
		
		int x = (selectedSquare.getX()/92);
		int y = (selectedSquare.getY()/92);
		
		board.movePiece(piece, x, y);
		
		record(piece.getActive(), x, y);
		completeMove();
		
	}
	
	public void completeMove() {

		gui.refreshAll();
		new Specials(game, piece);
		new Sounds("Move");
		new Referee(game, piece);
		
	}
	
	// -------------------------------------- Server Side Requests ---------------------------------------
	
	public void record(int active, int x, int y) {
		
		if(game.cM != null) {
			try {
				game.cM.sendCoords(active, x, y);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(game.sH != null) {
			try {
				game.sH.sendCoords(active, x, y);
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		
	}
	
	public void receiveMovement(int active, int x, int y) {
		
		for(Piece e: Game.piecesW) {
			if(active == e.getActive()) {
				piece = e;
			}
		}
	
		for(Piece e: Game.piecesB) {
			if(active == e.getActive()) {
				piece = e;
			}
		}
		
		piece.getBoard().movePiece(piece, x, y);
		completeMove();
		
	}
	
	public void changePawn(int active, int x, int y, int promote) {
		
		for(Piece e: Game.piecesW) {
			if(active == e.getActive()) {
				piece = e;
			}
		}
		
		for(Piece e: Game.piecesB) {
			if(active == e.getActive()) {
				piece = e;
			}
		}
		
		new Specials(game, piece, promote);
		
	}
	
}