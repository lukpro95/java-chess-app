package chess.com;

import java.util.ArrayList;

import ai.com.AutoBot;
import gui.com.GUI;
import menu.com.Menu;
import movement.com.MovePiece;
import pieces.com.Piece;
import pieces.com.PieceBishop;
import pieces.com.PieceKing;
import pieces.com.PieceKnight;
import pieces.com.PiecePawn;
import pieces.com.PieceQueen;
import pieces.com.PieceRook;
import players.com.Player;
import server.com.ClientMouth;
import server.com.ServerHost;

public class Game {
	
	private GUI 			gui 		= null;
	private Board 			board 		= null;
	private MovePiece 		movePiece 	= null;
	
	private String 			botValue 	= "";
	
	public ServerHost 		sH 			= null;
	public ClientMouth 		cM 			= null;
	
	public static ArrayList<Player> 	players 		= new ArrayList<>();
	public static ArrayList<Piece> 		pieces 			= new ArrayList<>();
	public static ArrayList<Piece> 		piecesW 		= new ArrayList<>();
	public static ArrayList<Piece> 		piecesB 		= new ArrayList<>();
	
	public Game(ServerHost sH, ClientMouth cM, String botValue){
		this.sH = sH;
		this.cM = cM;
		this.gui = new GUI(this, sH, cM);
		this.board 	= new Board();
		this.botValue = botValue;
		
		if(sH == null && cM == null) {
			setSinglePlayers();
		}

		setPieces();
		
	}
	
	private void setSinglePlayers() {
		
		if(botValue.equals("White")) {
			Player player0 = new Player();
			player0.setTeam("White");
			player0.setPlays(true);
			players.add(player0);
			
			Player player1 = new Player(Menu.settings.getName());
			player1.setTeam("Black");
			players.add(player1);
		}
		
		else if(botValue.equals("Black")) {
			Player player0 = new Player();
			player0.setTeam("Black");
			players.add(player0);
			
			Player player1 = new Player(Menu.settings.getName());
			player1.setTeam("White");
			player1.setPlays(true);
			players.add(player1);
		}
		
		else {
			Player player1 = new Player(Menu.settings.getName());
			player1.setTeam("White");
			player1.setPlays(true);
			players.add(player1);
			
			Player player2 = new Player(Menu.settings.getName());
			player2.setTeam("Black");
			players.add(player2);
		}
		
	}
	
	public void setOnlinePlayers(String sideW, String sideB) {

		Player playerWhite = new Player(sideW);
		playerWhite.setTeam("White");
		playerWhite.setPlays(true);
		
		Player playerBlack = new Player(sideB);
		playerBlack.setTeam("Black");
		playerBlack.setPlays(false);
		
		players.add(playerWhite);
		players.add(playerBlack);
		
		for(Player player: players) {
			System.out.println(player.getName() + " plays" + player.getTeam());
			System.out.println(player.doesPlay());
		}
	
	}
	
	private void setPieces() {
		sweepBoard();
		createPieces();
		gui.setChessBoard();
		moveIfBotWhite();
	}
	
	private void moveIfBotWhite() {
		
		if(cM == null && sH == null) {
			if(botValue.equals("White")) {
				new AutoBot(this);
			}
		}

	}

	private void sweepBoard() {
		gui.getPieceFrame().removeAll();
		gui.refreshAll();
		
		board.resetBoard();
		board.living.clear();
		board.removed.clear();
		
		Piece.LivingPiecesW.clear();
		Piece.LivingPiecesB.clear();
	}
		
	private void createPieces() {
		//------------------------------------------------------------ White ------------------------------------------------------------
		// VALUES ON BOARD
		// Pawns: value of 1-8;
		// Knights: value of 9-10; 
		// Bishops: value of 11-12; 
		// Rooks: value of 13-14; 
		// Queen: value of 15; 
		// King: value of 16;
		
		new PiecePawn(this, board, 1, 0, 6);
		new PiecePawn(this, board, 2, 1, 6);
		new PiecePawn(this, board, 3, 2, 6);
		new PiecePawn(this, board, 4, 3, 6);
		new PiecePawn(this, board, 5, 4, 6);
		new PiecePawn(this, board, 6, 5, 6);
		new PiecePawn(this, board, 7, 6, 6);
		new PiecePawn(this, board, 8, 7, 6);
		new PieceKnight(this, board, 9, 1, 7);
		new PieceKnight(this, board, 10, 6, 7);
		new PieceBishop(this, board, 11, 2, 7);
		new PieceBishop(this, board, 12, 5, 7);
		new PieceRook(this, board, 13, 0, 7);
		new PieceRook(this, board, 14, 7, 7);
		new PieceQueen(this, board, 15, 3, 7);
		new PieceKing(this, board, 16, 4, 7);
		
		//------------------------------------------------------------ Black ------------------------------------------------------------
		// VALUES ON BOARD
		// Pawns: negative value of 1-8;
		// Knights: negative value of 9-10; 
		// Bishops: negative value of 11-12; 
		// Rooks: negative value of 13-14; 
		// Queen: negative value of 15; 
		// King: negative value of 16;
		
		new PiecePawn(this, board, -1, 0, 1);
		new PiecePawn(this, board, -2, 1, 1);
		new PiecePawn(this, board, -3, 2, 1);
		new PiecePawn(this, board, -4, 3, 1);
		new PiecePawn(this, board, -5, 4, 1);
		new PiecePawn(this, board, -6, 5, 1);
		new PiecePawn(this, board, -7, 6, 1);
		new PiecePawn(this, board, -8, 7, 1);
		new PieceKnight(this, board, -9, 1, 0);
		new PieceKnight(this, board, -10, 6, 0);
		new PieceBishop(this, board, -11, 2, 0);
		new PieceBishop(this, board, -12, 5, 0);
		new PieceRook(this, board, -13, 0, 0);
		new PieceRook(this, board, -14, 7, 0);
		new PieceQueen(this, board, -15, 3, 0);
		new PieceKing(this, board, -16, 4, 0);
		
	}
	
	public void resetAll() {
		players.clear();
		setPieces();
		gui.refreshAll();
	}
	
	public GUI getGUI() {
		return gui;
	}
	
	public Board getBoard() {
		return board;
	}

	public MovePiece getMove() {
		this.movePiece = new MovePiece(this);
		return movePiece;
	}
	
}