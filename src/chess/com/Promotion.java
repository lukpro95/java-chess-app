package chess.com;

import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

import menu.com.Menu;
import pieces.com.Piece;
import pieces.com.PieceBishop;
import pieces.com.PieceKnight;
import pieces.com.PieceQueen;
import pieces.com.PieceRook;
import players.com.Player;

public class Promotion {
	
	private Game game;
	private Board board;
	
	private Piece piece;
	private int promote;
	
	private String[] options = {"Knight", "Bishop", "Rook", "Queen"};

	private boolean chosen;
	
	// ------------------------------------------------- Server Requests ----------------------------------------------------------
	
	public Promotion(Game game, Piece piece, int promote){
		this.game = game;
		this.board = game.getBoard();
		this.piece = piece;
		this.promote = promote;
		
		change(promote);
	}

	private void sendServerRequest() {
		if(game.cM != null) {
			try { game.cM.sendChange(piece.getActive(), piece.getX(), piece.getY(), promote); } catch (Exception e) {
				System.out.println(e);
				}
			}
	
		if(game.sH != null) {
			try { game.sH.sendChange(piece.getActive(), piece.getX(), piece.getY(), promote); } catch (IOException e) {
				System.out.println(e);
			}
		}
	}
	
	// ------------------------------------------------- Player/AI ----------------------------------------------------------
	
	public Promotion(Game game, Piece piece) {
		this.game = game;
		this.board = game.getBoard();
		this.piece = piece;
		
		for(Player player: Game.players) {
			if(player.isReal() && player.doesPlay() && player.getName().equals(Menu.settings.getName())) {
				manualChange();
			}
			else if (!player.isReal() && player.doesPlay()){
				autoChange();
			}
		}
		
	}
	
	private void manualChange() {
		while(chosen == false) {
			int promote = JOptionPane.showOptionDialog(null, "Promote your Pawn!", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			change(promote);
		}
			
		sendServerRequest();
	}
	
	private void autoChange() {
		Random r = new Random();
		int RandIndex = r.nextInt(options.length-0)+0;
		int chooseOption = RandIndex;
		
		change(chooseOption);
	}
	
	private void change(int chooseOption) {
		
		int newActive = piece.getActive()+20+10*chooseOption;
		
		this.promote = chooseOption;
		
		switch(chooseOption) {
			case 0: 
				new PieceKnight(game, board, newActive, piece.getX(), piece.getY());
				piece.destroy();
				break;
			case 1: 
				new PieceBishop(game, board, newActive, piece.getX(), piece.getY());
				piece.destroy();
				break;
			case 2: 
				new PieceRook(game, board, newActive, piece.getX(), piece.getY());
				piece.destroy();
				break;
			case 3: 
				new PieceQueen(game, board, newActive, piece.getX(), piece.getY());
				piece.destroy();
				break;
		}
		
		chosen = true;
		
	}
	
}