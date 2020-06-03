package chess.com;

import gui.com.GUI;
import pieces.com.Piece;
import players.com.Player;

public class Referee {
	
	private Piece piece;
	private GUI gui;
	private Game game;
	
	private static int whiteCounter = 0;
	private static int blackCounter = 0;
	private static int move = 0;
	
	public Referee(Game game, Piece piece) {
		this.game = game;
		this.gui = game.getGUI();
		this.piece = piece;
		
		pieceMovementCounter();
		changePlayer();
		changeMove();
		checkConditions();
		
	}
	
	private void pieceMovementCounter() {
		piece.setCount();
		game.getGUI().refreshAll();
	}
	
	private void changePlayer() {

		for(Player player: Game.players) {
			if(player.getTeam().equals("White")) {

				if(player.doesPlay()) {
					player.setPlays(false);
					whiteCounter += 1;
				}
				else {
					player.setPlays(true);
					gui.getReferee().setText("Player turn: White!");
				}
			}
			
			else if(player.getTeam().equals("Black")) {

				if(player.doesPlay()) {
					player.setPlays(false);
					blackCounter += 1;
				}
				else {
					player.setPlays(true);
					gui.getReferee().setText("Player turn: Black!");
				}
			}
		}
		
	}
	
	private void changeMove() {
		if(piece.getType().equals("Pawn")) {
			whiteCounter = 0;
			blackCounter = 0;
		}
	}
	
	private void checkConditions() {
		// Checking Stale-mates/Check-Mates
		new CheckConditions(game);
	}
	
	//------------------------- Getters ---------------------------------------------
	
	public static int getTotalCounter() {
		move = (whiteCounter + blackCounter)/2;
		return move;
	}
	
}
