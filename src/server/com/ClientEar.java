package server.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import chess.com.CheckConditions;
import chess.com.Game;
import gui.com.OnlineGUI;
import gui.com.OnlineGUIButtons;
import menu.com.Menu;
import movement.com.MovePiece;
import pieces.com.Piece;
import players.com.Player;

public class ClientEar implements Runnable {
	
	private Game game;
	
	private int[][] playGround;
	
	private Socket 				server	= null;
	private BufferedReader		input	= null;
	
	private MovePiece movePiece;

	public ClientEar(Game game, Socket s, MovePiece movePiece) throws IOException {
		
		this.game = game;
		this.playGround = game.getBoard().playGround;
		
		this.movePiece = movePiece;
		this.server = s;
		
		this.input	= new BufferedReader(new InputStreamReader(server.getInputStream()));
		
	}
	
	@Override
	public void run() {
		
		try {
			while(true) {
				
				String in = input.readLine();
				
				System.out.println("New server response: " + in);
				
				if(in.equals("Chat")) {
					waitForChat();
				}
				else if(in.equals("Coords")) {
					waitForCoords();
				}
				else if(in.equals("Lobby")) {
					waitForLobby();
				}
				else if(in.equals("Side")) {
					waitForSide();
				}
				else if(in.equals("Joined")) {
					waitForJoined();
				}
				else if(in.equals("Echo")) {
					waitForEcho();
				}
				else if(in.equals("Give Up")) {
					waitForGiveUp();
				}
				else if(in.equals("Result")) {
					waitForResult();
				}
				else if(in.equals("Neuter")) {
					waitForNeuter();
				}
				else if(in.equals("State")) {
					waitForState();
				}
				else if(in.equals("Change")) {
					waitForChange();
				}
				else {
					System.out.println("Dupa");
				}
				
			}
		} catch (IOException e) {
			System.out.println("Disconnected");
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void waitForJoined() throws IOException {
		
		String joined = input.readLine();
		
		String prMSG = "";
		
		if(!OnlineGUI.recordA.getText().equals("")) {
			prMSG = OnlineGUI.recordA.getText() + "\n";
		}
		else {
			prMSG = OnlineGUI.recordA.getText();
		}
		
		OnlineGUI.recordA.setText(prMSG + joined);
		
	}
	
	private void waitForCoords() throws IOException {
		
		System.out.println("Getting coords");
		
		String active = input.readLine();
		String x = input.readLine();
		String y = input.readLine();
		
		if(active != null && x != null && y != null) {
			int activePiece = Integer.valueOf(active);
			int xPos = Integer.valueOf(x);
			int yPos = Integer.valueOf(y);
			
			System.out.println("Piece " + active + " moved onto: " + x + " " + y);
			movePiece.receiveMovement(activePiece, xPos, yPos);
				
		}
		
	}
	
	private void waitForChange() throws IOException {
		
		int active = Integer.parseInt(input.readLine());
		int x = Integer.parseInt(input.readLine());
		int y = Integer.parseInt(input.readLine());
		int promote = Integer.parseInt(input.readLine());
		
		movePiece.changePawn(active, x, y, promote);
		
	}
	
	private void waitForChat() throws IOException {
		
		String msg = input.readLine();

		String prMSG = "";
		
		if(!OnlineGUI.chatA.getText().equals("")) {
			prMSG = OnlineGUI.chatA.getText() + "\n";
		}
		else {
			prMSG = OnlineGUI.chatA.getText();
		}
		
		OnlineGUI.chatA.setText(prMSG + msg);
		
	}
	
	private void waitForEcho() throws IOException {
		
		String msg = input.readLine();
		
		String prMSG = "";
		
		if(!OnlineGUI.recordA.getText().equals("")) {
			prMSG = OnlineGUI.recordA.getText() + "\n";
		}
		else {
			prMSG = OnlineGUI.recordA.getText();
		}
		
		OnlineGUI.recordA.setText(prMSG + msg);
		
	}
	
	private void waitForLobby() throws IOException {
		
		String lobbyList = input.readLine();
		
		OnlineGUI.lobbyA.setText(lobbyList);
		
	}
	
	private void waitForSide() throws IOException {
		
		String sideW = input.readLine();
		String sideB = input.readLine();
		
		game.setOnlinePlayers(sideW, sideB);
		
		OnlineGUI.whiteP.setText(sideW);
		OnlineGUI.blackP.setText(sideB);
		
		if(Menu.settings.getName().equals(sideW) || Menu.settings.getName().equals(sideB)){
			OnlineGUIButtons.changeButtons(10);
		}
		
	}
	
	private void waitForGiveUp() throws IOException {
		
		for(Player player: Game.players) {
			player.setCondition("Won");		
		}
		
		new CheckConditions(game).endAll("");
		
	}
	
	private void waitForResult() throws IOException {
		
		String result = input.readLine();
		
		for(Player player: Game.players) {
			if(player.getName().equals(Menu.settings.getName())) {
				player.setCondition(result);
			}
		}
		
		new CheckConditions(game).endAll(result);
		
	}
	
	private void waitForNeuter() throws IOException {
		
		for(Player player: Game.players) {
			player.setCondition("");
			player.setTeam("");
			player.setPlays(false);
		}
		
	}
	
	private void waitForState() throws IOException {
		
		for(int i = 0; i < 8; i++) {
		for(int j = 0; j < 8; j++) {
			String activeState = input.readLine();
			String xState = input.readLine();
			String yState = input.readLine();
			
			int active = Integer.parseInt(activeState);
			int x = Integer.parseInt(xState);
			int y = Integer.parseInt(yState);
			
			//board position
			playGround[x][y] = active;
			
			//icons position
			if(active > 0) {
				Piece.LivingPiecesW.get(active-1).getLabel().setBounds(3+x*92, 3+y*92, 90, 90);
				game.getGUI().refreshAll();
			}
			else if (active < 0) {
				Piece.LivingPiecesB.get(-(active)-1).getLabel().setBounds(3+x*92, 3+y*92, 90, 90);
				game.getGUI().refreshAll();
			}
			game.getGUI().refreshAll();
			
		}}
		
		game.getBoard().setBoard(playGround);
		game.getGUI().refreshAll();
		
		String playerTurn = input.readLine();
		if(playerTurn.equals("Black")) {
			game.getGUI().getReferee().setText("Player turn: Black");
		}
		else if(playerTurn.equals("White")) {
			game.getGUI().getReferee().setText("Player turn: White");
		}
		
		//players controlling state info
		String whiteActive = input.readLine();
		String blackActive = input.readLine();
		
		OnlineGUI.whiteP.setText(whiteActive);
		OnlineGUI.blackP.setText(blackActive);
		
	}
		
}
