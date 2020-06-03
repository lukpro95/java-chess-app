package ai.com;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;

import chess.com.Game;
import movement.com.Coords;
import movement.com.MovePiece;
import pieces.com.Piece;
import players.com.Player;

public class AutoBot {
	
	private Game game;
	private Piece generatedPiece;
	private Coords chosenSquareToMove;
	
	private ArrayList<Piece> myTeam = new ArrayList<>();
	
	private String side;
	
	public AutoBot(Game game) {
		
		this.game = game;
		this.myTeam.clear();
		
		for(Player player: Game.players) {
			if(player.getTeam().equals("White") && !player.isReal()) {
				side = "White";
				System.out.println(player.doesPlay());
				doTasks();
			}
			else if(player.getTeam().equals("Black") && !player.isReal()) {
				side = "Black";
				doTasks();
			}
		}
		
	}
	
	private void doTasks(){
		
		generatePiece();

		if(generatedPiece.getAllowedMoves().size() == 0) {
			doTasks();
		}
		else {
			randomizeChoice();
		}

	}
	
	private void generatePiece() {
		
		for(Piece piece: Game.pieces) {
			if(piece.getTeam().equals(side)) {
				myTeam.add(piece);
				piece.fetchMyMoves();
			}
		}
		
		Random r = new Random();
		int RandIndex = r.nextInt(myTeam.size()-0)+0;
		generatedPiece = myTeam.get(RandIndex);
		
	}
	
	private void randomizeChoice() {
		Random r = new Random();
		int RandIndex = r.nextInt(generatedPiece.getAllowedMoves().size()-0)+0;
		chosenSquareToMove = generatedPiece.getAllowedMoves().get(RandIndex);
		
		JLabel chosenSquare = new JLabel();
		chosenSquare.setBounds(3+chosenSquareToMove.getX()*92, 3+chosenSquareToMove.getY()*92, 90, 90);
		
		MovePiece move = new MovePiece(game, generatedPiece);
		move.moveToSquare(chosenSquare);
		
	}
	
}