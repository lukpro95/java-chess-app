package pieces.com;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import chess.com.Board;
import chess.com.Game;
import gui.com.Library;
import gui.com.Sounds;
import menu.com.Menu;
import movement.com.Coords;
import movement.com.MovePiece;
import players.com.Player;

public class Piece {
	
	public static 	ArrayList<Piece> 	LivingPiecesW 	= new ArrayList<>();
	public static 	ArrayList<Piece> 	LivingPiecesB 	= new ArrayList<>();
	
	protected 		Board 				board 			= null;
	private 		JPanel 				panel 			= null;
	private 		String 				type 			= null;
	private 		String 				team 			= null;
	
	private 		int 				moveCount 		= 0;
	private 		int 				active 			= 0;
	
	protected 		Game 				game 			= null;
	protected 		Piece 				piece 			= null;
	protected 		JLabel 				label 			= null;
	protected 		ArrayList<Integer> 	allies 			= null;
	protected 		ArrayList<Integer> 	enemies 		= null;
	protected 		ArrayList<Coords> 	allowedMoves 	= new ArrayList<>();
	protected 		int 				x 				= 0;
	protected 		int 				y 				= 0;
	protected		int					range			= 0;
	protected 		int[][] 			playGround 		= null;
	protected 		int[][] 			moves 			= null;
	
	public Piece(Game game, Board board, int active, int x, int y){
		
		this.game 		= game;
		this.board 		= board;
		this.playGround = board.playGround;
		this.active 	= active;
		this.x 			= x;
		this.y 			= y;
		
		this.panel = game.getGUI().getPieceFrame();
		
		this.active = active;
		this.x = x;
		this.y = y;

		setUp();
		react();
		
	}
	
	private void setUp() {
		
		DetailedInformation info 	= new DetailedInformation(active);
		team 						= info.getTeam();
		type 						= info.getType();
		allies 						= info.getAllies();
		enemies 					= info.getEnemies();
		
		if(team.equals("White")) {
			LivingPiecesW.add(this);
			Game.piecesW.add(this);
		}
		else {
			LivingPiecesB.add(this);
			Game.piecesB.add(this);
		}
		
		board.living.add(active);
		playGround[x][y] = active;
		
		label = new JLabel();
		label.setBounds(3+x*92, 3+y*92, 90, 90);
		label.setIcon(new Library().getPiece(team.substring(0,1)+type));
		panel.add(label);
		
		Game.pieces.add(this);
		piece = this;
		
	}
	
	public void clear() {
		
		if(active < 59) {
			allowedMoves.clear();
		}
		
		if(active > 60) {
			allowedMoves.clear();
		}
		
	}
	
	// --------- getters
	
	public int fetchMyMoves() {
		checkMovement();
		return allowedMoves.size();
	}
	
	public int[][] getMoves() {
		return this.moves;
	}
	
	public String getTeam() {
		return this.team;
	}
	
	public String getType() {
		return this.type;
	}
	
	public ArrayList<Coords> getAllowedMoves(){
		return allowedMoves;
 	}

	public ArrayList<Integer> getAllies() {
		return this.allies;
	}

	public ArrayList<Integer> getEnemies() {
		return this.enemies;
	}
	
	public Board getBoard() {
		return this.board;
	}

	public JLabel getLabel() {
		return this.label;
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getActive() {
		return this.active;
	}
	
	public int getMoveCount() {
		return this.moveCount;
	}
	
	public int getRange() {
		return this.range;
	}
	
	// ------- setters
	
	public void setAllowedMoves(ArrayList<Coords> newMoves){
		this.allowedMoves.clear();
		this.allowedMoves = newMoves;
 	}
	
	public void setCount() {
		this.moveCount ++;
	}
	
	public void setActive(int newActive) {
		this.active = newActive;
		this.playGround[this.getX()][this.getY()] = newActive;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public void setType(String newType) {
		this.type = newType;
	}
	
	public void castlingX(int newX) {
		System.out.println(type + " is not allowed to castle.");
		// to be overridden
	}
	
	// ------------------------------ Actions ---------------------------------------------
	
	private void generateMovement() {
		checkMovement();
		new MovePiece(game, piece);
	}
	
	protected void checkMovement() {
		// to be overridden
	}
	
	private void react() {
		label.addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e) {
			game.getGUI().refreshAll();
			
			for(Player player: Game.players) {
				if(player.doesPlay() && player.getTeam().equals("White") && piece.getTeam().equals("White")) {
					if(player.getName().equals(Menu.settings.getName())) {
						generateMovement();
					}
				}
				else if(player.doesPlay() && player.getTeam().equals("Black") && piece.getTeam().equals("Black")) {
					if(player.getName().equals(Menu.settings.getName())) {
						generateMovement();
					}
				}
			}
			
		}});
	}

	public void destroy() {
		new Sounds("Capture");
		label.setBounds(0,0,0,0);
		
		Game.pieces.remove(this);
		Game.piecesW.remove(this);
		Game.piecesB.remove(this);
		
		for (int i = 0; i < board.living.size(); i++) {
			if(board.living.get(i) == piece.getActive()) {
				board.living.remove(i);
			}
		}
	}
	
}