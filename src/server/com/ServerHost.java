package server.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

import chess.com.CheckConditions;
import chess.com.Game;
import gui.com.OnlineGUI;
import menu.com.Menu;
import players.com.Player;

public class ServerHost extends JFrame implements Runnable {
	
	private Game game;
	
	public ArrayList<ServerClientHandler> clients = new ArrayList<>();
	public ArrayList<String> nameList = new ArrayList<>();
	public ArrayList<String> wishList = new ArrayList<>();
	public ArrayList<String> playingList = new ArrayList<>();
	
	public String defeated = "";
	public String winner = "";
	
	private static final long serialVersionUID = 1L;
	
	private int 					port 		= 0;
	private int						limit		= 12;
	
	private ServerSocket 			listener 	= null;
	private Socket 					socket		= null;
	private ExecutorService 		pool 		= null;
	private PrintWriter 			output		= null;
	
	public String name;

	public ServerHost() throws IOException {
		this.name = Menu.settings.getName();
		this.game = new Game(this, null, null);
		this.nameList.add(name);
		this.port = Integer.valueOf(Menu.settings.getPortServer());
		this.pool = Executors.newFixedThreadPool(limit);
		this.listener = new ServerSocket(port);
	}

	@Override
	public void run() {
		
		setName();
		
		try {
			while(clients.size() < (limit+1)) {
				System.out.println("[SERVER]: Waiting for a client connection...");
				socket 	= listener.accept();
				
				output	= new PrintWriter(socket.getOutputStream(), true);
				output.flush();
				System.out.println("[SERVER]: Client connected to the server!");
				
				//Query
				ServerClientHandler client;
				client = new ServerClientHandler(game, this, socket, clients);
				pool.execute(client);
			}
		} catch (IOException e) {
			System.out.println(e);
		}
			
	}
	
	public void disconnect() {
		try {
			listener.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setName() {
		OnlineGUI.lobbyA.setText(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void sendChange(int active, int x, int y, int promote) throws IOException{
		
		for(ServerClientHandler e: clients) {
			e.output.println("Change");
			e.output.flush();
			
			e.output.println(active);
			e.output.flush();
			
			e.output.println(x);
			e.output.flush();
			
			e.output.println(y);
			e.output.flush();
			
			e.output.println(promote);
			e.output.flush();
		}
		
	}
	
	public void sendCoords(int active, int x, int y) throws IOException {
		
		for(ServerClientHandler e: clients) {
			e.output.println("Coords");
			e.output.flush();
			
			e.output.println(active);
			e.output.flush();
			
			e.output.println(x);
			e.output.flush();
			
			e.output.println(y);
			e.output.flush();
		}
	}
	
	public void sendMSG(String msg) {
		
		LocalTime time = LocalTime.now();
		String timeNow = time.toString().substring(0, 5) + " | ";
		
		for(ServerClientHandler e: clients) {
				e.output.println("Chat");
				e.output.flush();

				e.output.println(timeNow + name + ": " + msg);
				e.output.flush();
		}
		
		String prMSG = "";
		
		if(!OnlineGUI.chatA.getText().equals("")) {
			prMSG = OnlineGUI.chatA.getText() + "\n";
		}
		else {
			prMSG = OnlineGUI.chatA.getText();
		}
		
		OnlineGUI.chatA.setText(prMSG + timeNow + name + ": " + msg);
	}
	
	public void sendEcho(String echo) {
		
		for(ServerClientHandler e: clients) {
			e.output.println("Echo");
			e.output.flush();
			
			e.output.println(echo);
			e.output.flush();
		}
		
		String prMSG = "";
		
		if(!OnlineGUI.recordA.getText().equals("")) {
			prMSG = OnlineGUI.recordA.getText() + "\n";
		}
		else {
			prMSG = OnlineGUI.recordA.getText();
		}
		
		OnlineGUI.recordA.setText(prMSG + echo);
		
	}
	
	public void sendOutStart() {
		
		Random r1 = new Random();
		int randIndex1 = r1.nextInt(wishList.size()-0)+0;
		String randName1 = wishList.get(randIndex1);
		
		Random r2 = new Random();
		int randIndex2 = r2.nextInt(wishList.size()-0)+0;
		while(randIndex2 == randIndex1) {
			randIndex2 = r2.nextInt(wishList.size()-0)+0;
		}
		String randName2 = wishList.get(randIndex2);

		sendEcho("Game has started!");
		sendEcho(randName1 + " plays as White!");
		sendEcho(randName2 + " plays as Black!");
		sendEcho("Good Luck!");
		
		playingList.clear();
		playingList.add(randName1);
		playingList.add(randName2);
		
		game.setOnlinePlayers(randName1, randName2);
		
		OnlineGUI.whiteP.setText(randName1);
		OnlineGUI.blackP.setText(randName2);
		
		for(ServerClientHandler e: clients) {
				e.output.println("Side");
				e.output.flush();
				
				e.output.println(randName1);
				e.output.flush();
				
				e.output.println(randName2);
				e.output.flush();
		}
			
	}
	
	public void sendGiveUp() {
		
		for(ServerClientHandler e: clients) {
			e.output.println("Give Up");
			e.output.flush();
		}
		
		sendEcho(this.name + " has given up!");
		
		for(String e: playingList) {
			if(!e.equals(this.name)) {
				sendEcho(e + " is the winner!");
			}
			
			for(Player player: Game.players) {
				if(player.getName().equals(this.name)) {
					player.setCondition("Lost");
				}
				else if(player.getName().equals(e)) {
					player.setCondition("Won");
				}
			}
			
		}
		
		new CheckConditions(game).endAll("");
		
	}
	
	public void sendWinner(String name) {
		
		if(name == null) {
			sendEcho("Nobody wins! It's a draw!");
		}
		
		else {
			for(Player player: Game.players) {
				if(player.getName().equals(name)) {
					sendEcho(player.getName() + " is the winner!");
				}
			}
		}
		
	}
	
	public void sendNeuter() {
		
		for(ServerClientHandler e: clients) {
			e.output.println("Neuter");
			e.output.flush();
		}
		
		for(Player player: Game.players) {
			player.setCondition("");
			player.setTeam("");
			player.setPlays(false);
		}
		
	}
	
}