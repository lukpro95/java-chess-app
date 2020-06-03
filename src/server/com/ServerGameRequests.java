package server.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import chess.com.CheckConditions;
import chess.com.Game;
import gui.com.OnlineGUI;
import movement.com.MovePiece;
import players.com.Player;

public class ServerGameRequests {
	
	private Game game;
	private MovePiece movePiece;
	private ServerHost sH;
	private ServerClientHandler cH;
	
	private int[][] playGround;
	
	private BufferedReader input;
	private ArrayList<ServerClientHandler> clients;
	
	public ServerGameRequests(Game game, ServerHost sH, ServerClientHandler ch, MovePiece movePiece, BufferedReader input, ArrayList<ServerClientHandler> clients , String command) {
		
		this.game = game;
		this.sH = sH;
		this.movePiece = movePiece;
		
		this.cH = ch;
		this.input = input;
		this.clients = clients;
		
		try {
			passCommand(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void passCommand(String command) throws IOException {
		
		if(command.equals("Coords")) {
			waitForCoords();
		}
		else if(command.equals("Change")) {
			waitForChange();
		}
		else if(command.equals("Give Up")) {
			waitForGiveUp();
		}
		else if(command.equals("State")) {
			waitForGetState();
		}
		
	}
	
	private void waitForGiveUp() throws IOException {
		
		String lost = input.readLine();
		
		for(String e: sH.playingList) {
			if(!e.equals(lost)) {
				sendEcho(e + " is the winner!");
				sH.winner = e;
			}
			if(e.equals(lost)) {
				sendEcho(lost + " has given up!");
				sH.defeated = lost;
			}
		}
		
		sendResult();
		
	}
	
	private void sendEcho(String echo) {
		for(ServerClientHandler e: clients) {
			e.output.println("Echo");
			e.output.flush();
			
			e.output.println(echo);
			e.output.flush();
		}
	}
	
	private void sendResult() {
		
		for(ServerClientHandler e: clients) {
			if(e.name.equals(sH.winner)) {
				e.output.println("Result");
				e.output.flush();
				
				e.output.println("Won");
				e.output.flush();
			}
			else if(e.name.equals(sH.defeated)) {
				e.output.println("Result");
				e.output.flush();
				
				e.output.println("Lost");
				e.output.flush();
			}
			else if(!e.name.equals(sH.defeated) && !e.name.equals(sH.winner)) {
				e.output.println("Result");
				e.output.flush();
				
				e.output.println(sH.winner);
				e.output.flush();
			}
		}
		
		for(Player player: Game.players) {
			
			if(player.getName().equals(sH.winner)) {
				player.setCondition("Won");
			}
			
			else if(player.getName().equals(sH.defeated)) {
				player.setCondition("Lost");
			}
			
			else{player.setCondition("Spect");}
			
		}
		
		new CheckConditions(game).endAll(sH.winner);
		
	}
	
	private void waitForChange() throws IOException {
		
		System.out.print("\tResponding to Change\n");
		String activeR = input.readLine();
		String xR = input.readLine();
		String yR = input.readLine();
		String promoteR = input.readLine();
		
		for(ServerClientHandler e: clients) {
			if(e != cH) {
				e.output.println("Change");
				e.output.flush();
				
				e.output.println(activeR);
				e.output.flush();
				
				e.output.println(xR);
				e.output.flush();
				
				e.output.println(yR);
				e.output.flush();
				
				e.output.println(promoteR);
				e.output.flush();
			}
		}
		
		int active = Integer.parseInt(activeR);
		int x = Integer.parseInt(xR);
		int y = Integer.parseInt(yR);
		int promote = Integer.parseInt(promoteR);
		
		System.out.println("Promotion: " + promote);
		movePiece.changePawn(active, x, y, promote);
		
	}
	
	private void waitForGetState() throws IOException {
		
		System.out.println("\tResponding to State\n");
		
		playGround = game.getBoard().playGround;
		
		for(ServerClientHandler f: clients) {
			if(f == cH) {
				f.output.println("State");
				f.output.flush();
				
				for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					f.output.println(playGround[i][j]);
					f.output.flush();
					
					f.output.println(i);
					f.output.flush();
					
					f.output.println(j);
					f.output.flush();
				}}
				
				int first = game.getGUI().getReferee().getText().length()-6;
				int last = game.getGUI().getReferee().getText().length()-1;
				String turn = game.getGUI().getReferee().getText().substring(first, last);
				
				f.output.println(turn);
				f.output.flush();
				
				f.output.println(OnlineGUI.whiteP.getText());
				f.output.flush();
				
				f.output.println(OnlineGUI.blackP.getText());
				f.output.flush();
			}
		}
		
	}
	
	private void waitForCoords() throws IOException {
		
		System.out.print("\tResponding to Coords\n");
		String active = input.readLine();
		String x = input.readLine();
		String y = input.readLine();
		
		sendOutCoords(active, x, y);
		
		int activeS = Integer.valueOf(active);
		int xS = Integer.valueOf(x);
		int yS = Integer.valueOf(y);
		
		movePiece.receiveMovement(activeS, xS, yS);
		
	}
	
	private void sendOutCoords(String active, String x, String y) throws IOException {
		
		for(ServerClientHandler f: clients) {
			if(f != cH) {
				f.output.println("Coords");
				f.output.flush();
				
				f.output.println(active);
				f.output.flush();
				
				f.output.println(x);
				f.output.flush();
				
				f.output.println(y);
				f.output.flush();
			}
		}
		
	}

}