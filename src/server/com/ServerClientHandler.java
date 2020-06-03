package server.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import chess.com.Game;
//import gui.com.GUI;
import gui.com.OnlineGUI;
import movement.com.MovePiece;

public class ServerClientHandler implements Runnable {
	
	private Game game;
	private MovePiece movePiece;
	private ServerHost sH;
	
	public ArrayList<ServerClientHandler> 	clients = null;
	public Socket 							client	= null;
	public BufferedReader					input	= null;
	public PrintWriter 						output	= null;
	
	public  String						name	= "";
	public 	String						ip		= "";

	public ServerClientHandler(Game game, ServerHost sH, Socket socket, ArrayList<ServerClientHandler> clients) throws IOException {
		
		this.game = game;
		this.sH = sH;
		this.movePiece = game.getMove();
		
		this.client = socket;
		this.clients = clients;
		this.ip = client.getInetAddress().getHostAddress();
		
		input	= new BufferedReader(new InputStreamReader(client.getInputStream()));
		
		output	= new PrintWriter(client.getOutputStream(), true);
		output.flush();
		
	}

	@Override
	public void run() {
		
		try {
			while(true) {
				
				String in = input.readLine();
				System.out.println("New request: " + in);
				new ServerGameRequests(game, sH, this, movePiece, input, clients, in);
				new ServerChatRequests(game, sH, this, input, clients, in);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			sH.sendEcho(this.name + " has disconnected from the server!");
		} finally {
			removeFromLobby();
			try {
				client.close();
				output.close();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void removeFromLobby() {
		
		sH.clients.remove(this);
		sH.nameList.remove(this.name);
		
		OnlineGUI.lobbyA.setText("");
		
		for(String s: sH.nameList) {
			if(OnlineGUI.lobbyA.getText().equals("")) {
				OnlineGUI.lobbyA.setText(s);
			}
			else {
				OnlineGUI.lobbyA.setText(OnlineGUI.lobbyA.getText() + ", " + s);
			}
		}
		
		String list = OnlineGUI.lobbyA.getText();
		
		for(ServerClientHandler e: sH.clients) {
			e.output.println("Lobby");
			e.output.flush();
			
			e.output.println(list);
			e.output.flush();
		}
		
	}
	
}
