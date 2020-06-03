package server.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import chess.com.Game;
import gui.com.OnlineGUI;

public class ServerChatRequests {
	
	private ServerHost sH;
	private ServerClientHandler cH;
	
	private BufferedReader input;
	private ArrayList<ServerClientHandler> clients;
	
	public ServerChatRequests(Game game, ServerHost sH, ServerClientHandler cH, BufferedReader input, ArrayList<ServerClientHandler> clients, String command) {
		
		this.sH = sH;
		this.cH = cH;
		this.input = input;
		this.clients = clients;
		
		try {
			passCommand(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void passCommand(String command) throws IOException {
		
		if(command.equals("Chat")) {
			waitForChat();
		}
		else if(command.equals("Lobby")) {
			sendOutLobby();
		}
		else if(command.equals("Wish")) {
			waitForWish();
		}
		else if(command.equals("Joined")) {
			sendJoined();
		}
		else if(command.equals("Echo")) {
			waitForEcho();
		}
		else if(command.equals("Disconnected")) {
			waitForDisconnected();
		}
		
	}
	
private void waitForChat() throws IOException {
		
		System.out.print("\tResponding to Chat\n");
		String msg = input.readLine();
		
		sendOutChat(msg);
		
		LocalTime time = LocalTime.now();
		String timeNow = time.toString().substring(0, 5) + " | ";
		
		String prMSG = "";
		
		if(!OnlineGUI.chatA.getText().equals("")) {
			prMSG = OnlineGUI.chatA.getText() + "\n";
		}
		else {
			prMSG = OnlineGUI.chatA.getText();
		}
		
		OnlineGUI.chatA.setText(prMSG + timeNow + msg);
		
		
	}

	private void sendOutChat(String msg) {
		
		for(ServerClientHandler e: clients) {
			if(e != cH) {
				e.output.println("Chat");
				e.output.flush();
				
				e.output.println(msg);
				e.output.flush();
			}
		}
		
	}
	
	private void waitForEcho() throws IOException {
		
		String msg = input.readLine();
		
		sendOutChat(msg);
		
		String prMSG = "";
		
		if(!OnlineGUI.recordA.getText().equals("")) {
			prMSG = OnlineGUI.recordA.getText() + "\n";
		}
		else {
			prMSG = OnlineGUI.recordA.getText();
		}
		
		OnlineGUI.recordA.setText(prMSG + msg);
		
	}
	
	private void waitForWish() throws IOException {
		
		System.out.print("\tResponding to Wish\n");
		String wishToPlay = input.readLine();
		sH.wishList.add(wishToPlay);
		
	}
	
	private void sendJoined() {
		
		System.out.print("\tResponding to Joined\n");
		sH.sendEcho(cH.name + " has joined the server!");
		
	}
	
	private void sendOutLobby() {
		
		System.out.print("\tResponding to Lobby\n");
		try {
			cH.name = input.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		sH.clients.add(cH);
		
		sH.nameList.clear();
		sH.nameList.add(sH.getName());
		
		for(ServerClientHandler e: sH.clients) {
			sH.nameList.add(e.name);
		}
		
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
	
	private void removeFromLobby() {
		
		sH.clients.remove(cH);
		sH.nameList.remove(cH.name);
		
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
	
	private void waitForDisconnected() throws IOException {
		
		String name = input.readLine();
		
		for(ServerClientHandler e: clients) {
			if(e.name.equals(name)) {
				sH.sendEcho(cH.name + " has disconnected from the server!");
				removeFromLobby();
				
				e.client.close();
				e.output.close();
				e.input.close();
			}
		}
		
	}
	
}
