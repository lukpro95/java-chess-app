package chess.com;

import javax.swing.JOptionPane;

import gui.com.OnlineGUI;
import gui.com.OnlineGUIButtons;
import gui.com.Sounds;
import menu.com.Menu;
import movement.com.DirectAttacks;
import pieces.com.Piece;
import players.com.Player;

public class CheckConditions {
	
	private Game 	game 	= null;
	private Board 	board 	= null;
	
	private int draw 				= 0;
	private int threeFoldTrigger 	= 0;
	private int stalemate 			= 0;
	private int mate 				= 0;
	
	private String reason = null;
	private String winner = null;

	public CheckConditions(Game game){
		this.game = game;
		this.board = game.getBoard();
		
		checkDraw();
		checkMateOrStalemate();

	}
	
	private void checkDraw() {
		
		//50-Move rule
		if(Referee.getTotalCounter() == 50) {
			System.out.println("Draw by 50-moves rule!");
			draw = 1;
			reason  = "50-Move Rule!";
		}
		
		//Insufficient Material
		if(board.living.size() == 2) {
			
			String living = board.living.toString();

			if(living.contains("16") && living.contains("-16")) {
				System.out.println("Draw by Insufficient Materials!");
				draw = 1;
				reason = "Insufficient Material!";
			}
		}
		
		//Insufficient Material
		if(board.living.size() == 3) {
			
			String living = board.living.toString();

			if(	//Bishops
				living.contains("16") && living.contains("-16") && (living.contains("11") || living.contains("12"))
				||
				living.contains("16") && living.contains("-16") && (living.contains("-11") || living.contains("-12"))
				||
				//Knights
				living.contains("16") && living.contains("-16") && (living.contains("9") || living.contains("10"))
				||
				living.contains("16") && living.contains("-16") && (living.contains("-9") || living.contains("-10"))
			) 
			{
				System.out.println("Draw by Insufficient Materials!");
				draw = 1;
				reason = "Insufficient Material!";
			}
		}
		
		//Insufficient Material
		if(board.living.size() == 4) {
			
			String living = board.living.toString();

			if(
					living.contains("16") && living.contains("-16") && (living.contains("9") && living.contains("10"))
					||
					living.contains("16") && living.contains("-16") && (living.contains("-9") && living.contains("-10"))
			) 
			{
				System.out.println("Draw by Insufficient Materials!");
				draw = 1;
				reason = "Insufficient Material!";
			}
		}
		
		//checkRepetition();
		
		if(threeFoldTrigger  == 1) {
			System.out.println("Draw by 3Fold Repetition!");
			draw = 1;
			reason = "Three Fold Repetition!";
		}
		
	}
	
//	private void checkRepetition() {
//		
//		for (int index1 = 0; index1 < recordList.size(); index1++) {
//			
//			for (int index2 = 0; index2 < recordList.size(); index2++) {
//				
//				if(
//						(recordList.get(index1).getActive() == recordList.get(index2).getActive() && (index1 != index2))
//						&&
//						(recordList.get(index1).getX() == recordList.get(index2).getX() && (index1 != index2))
//						&&
//						(recordList.get(index1).getY() == recordList.get(index2).getY() && (index1 != index2))
//				) {
//				for (int index3 = 0; index3 < recordList.size(); index3++) {
//					
//					if(
//							(recordList.get(index2).getActive() == recordList.get(index3).getActive() && (index1 != index2 && index2 != index3 && index1 != index3))
//							&&
//							(recordList.get(index2).getX() == recordList.get(index3).getX() && (index1 != index2 && index2 != index3 && index1 != index3))
//							&&
//							(recordList.get(index2).getY() == recordList.get(index3).getY() && (index1 != index2 && index2 != index3 && index1 != index3))
//					) {
//						
//						threeFoldTrigger = 1;
//						break;
//						
//					}
//					
//				}
//				}
//				
//			}
//				
//		}
//	}
	
	private void checkMateOrStalemate() {
		
		for(Player player: Game.players) {
			if(player.getTeam().equals("White")) {
				
				int count = 0;
				
				for(Piece piece: Game.piecesW) {
					if(piece.getType().equals("King")) {
						DirectAttacks.resetCount();
						new DirectAttacks(piece);
					}
				}
				
				for(Piece piece: Game.piecesW) {
					if(piece.fetchMyMoves() == 0) {
						count++;
					}
				}
				
				if(DirectAttacks.getCount() == 1) {
					new Sounds("Check");
				}
				
				if(DirectAttacks.getCount() == 2 && DirectAttacks.getAttacker().equals("Black")) {
					reason = "Black Check-Mates White!";
					mate = 1;
					player.setCondition("Won");
				}
				
				if(count == Game.piecesW.size() && DirectAttacks.getCount() == 1) {
					reason = "Black Check-Mates White!";
					mate = 1;
					player.setCondition("Won");
				}
				
				else if(count == Game.piecesW.size() && DirectAttacks.getCount() == 0) {
					reason = "Stalemate!";
					stalemate = 1;
				}
			}
			else if(player.getTeam().equals("Black")) {
				
				int count = 0;
				
				for(Piece piece: Game.piecesB) {
					if(piece.getType().equals("King")) {
						DirectAttacks.resetCount();
						new DirectAttacks(piece);
					}
				}
				
				for(Piece piece: Game.piecesB) {
					if(piece.fetchMyMoves() == 0) {
						count++;
					}
				}
				
				if(DirectAttacks.getCount() == 1) {
					new Sounds("Check");
				}
				
				if(DirectAttacks.getCount() == 2 && DirectAttacks.getAttacker().equals("White")) {
					reason = "White Check-Mates Black!";
					mate = 1;
					player.setCondition("Won");
				}
				
				if(count == Game.piecesB.size() && DirectAttacks.getCount() == 1) {
					reason = "White Check-Mates Black!";
					mate = 1;
					player.setCondition("Won");
				}
				
				else if(count == Game.piecesB.size() && DirectAttacks.getCount() == 0) {
					reason = "Stalemate!";
					stalemate = 1;
				}
			}
		}
		
		for(Player player: Game.players) {
			if(player.getCondition().equals("Won")) {
				winner = player.getName();
				break;
			}
		}
		
		endGame();
		
	}
	
	private void endGame() {
		
		if(draw == 1 && stalemate == 0 && mate == 0) {
			
			new Sounds("Draw");
			
			String drawMessage = "Game ends by a Draw!\nReason: " + reason;
			JOptionPane.showMessageDialog(null, drawMessage);
			
			endAll(winner);
			
		}
		
		if(stalemate == 1 && draw == 0 && mate == 0) {
			
			new Sounds("Draw");
			
			String drawMessage = "Game ends by a Draw!\nReason: " + reason;
			JOptionPane.showMessageDialog(null, drawMessage);
			
			endAll(winner);
			
		}
		
		if(mate == 1 && stalemate == 0 && draw == 0) {
			
			new Sounds("Mate");
			
			String drawMessage = "The game has finished!\nResult: "+ reason;
			JOptionPane.showMessageDialog(null, drawMessage);
			
			endAll(winner);
			
		}
		
	}
	
	public void endAll(String winner) {
		
		int result = -1;
		
		if(game.sH != null) {
			game.sH.sendWinner(winner);
		}
		
		if(game.cM == null && game.sH == null) {
			System.exit(10);
		}
		else {
			for(Player player: Game.players) {
				if(player.getName().equals(Menu.settings.getName())) {
					if(player.getCondition().equals("Won")) {
						result = 0;
					}
					else 
					if(player.getCondition().equals("Lost")) {
						result = 1;
					}
				}
				else
				if(player.getCondition().equals("Both")) {
					result = 2;
				}
			}
		}
		
		if(result == 0) {
			String drawMessage = "Game ends! You have won!";
			JOptionPane.showMessageDialog(null, drawMessage);
		}
		else if(result == 1) {
			String drawMessage = "Game ends! You have lost!";
			JOptionPane.showMessageDialog(null, drawMessage);
		}
		else if(result == 2) {
			String drawMessage = "Game ends! " + winner + " has won the game!";
			JOptionPane.showMessageDialog(null, drawMessage);
		}
		
		result = 0;
		
		reset();
		
	}
	
	private void reset() {
		
		draw = 0;
		threeFoldTrigger = 0;
		stalemate = 0;
		mate = 0;
		reason = null;
		winner = null;
		
		if(game.sH == null) {
			OnlineGUIButtons.changeButtons(11);
		}
		else if(game.cM == null) {
			OnlineGUIButtons.changeButtons(1);
			game.sH.sendNeuter();
			game.sH.wishList.clear();
			game.sH.playingList.clear();
			game.sH.winner = "";
			game.sH.defeated = "";
		}
		
		OnlineGUI.blackP.setText("Awaiting...");
		OnlineGUI.whiteP.setText("Awaiting...");
		
		game.resetAll();
		
	}

}