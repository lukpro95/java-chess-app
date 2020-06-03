package server.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;

import javax.swing.JFrame;
import javax.swing.JLabel;

import chess.com.CheckConditions;
import chess.com.Game;
import gui.com.OnlineGUI;
import movement.com.MovePiece;
import players.com.Player;

public class ClientMouth implements Runnable {
	
	private static String SERVER_IP = "";
	private static int SERVER_PORT = 9090;
	
	private JFrame frame;
	private Game game;
	private MovePiece movePiece;
	
	private Socket				socket		= null;
	private PrintWriter			output		= null;
	private ClientEar			serverConn	= null;
	
	public String name;
	private JLabel connection;
	
	public ClientMouth(JFrame frame, String IP, int PORT, String name, JLabel connection) {

		this.frame = frame;
		this.name = name;
		this.connection = connection;
		SERVER_PORT = PORT;
		SERVER_IP = IP;
		
	}

	@Override
	public void run() {
		
		try {
			joinServer();
			connection.setText("Connected successfully!");
		} catch (IOException e) {
			System.out.println("Cannot connect");
			connection.setText("Couldn't connect to the server! Try again.");
		}
		
	}
	
	private void joinServer() throws IOException {
		
		socket		= new Socket(SERVER_IP, SERVER_PORT);
		System.out.println("Connected");
		
		game = new Game(null, this, null);
		movePiece = game.getMove();
		serverConn 	= new ClientEar(game, socket, movePiece);
		
		startGame();
		
		new Thread(serverConn).start();
		
		output		= new PrintWriter(socket.getOutputStream(), true);
		
		sendName();
		sendJoined();
		sendStateRequest();
		
	}
	
	private void sendName() {
		output.println("Lobby");
		output.flush();
		
		System.out.println("Sending name: " + name);
		output.println(name);
		output.flush();
	}
	
	private void sendJoined() {
		
		output.println("Joined");
		output.flush();
		
	}
	
	private void sendStateRequest() {
		
		System.out.println("Requesting State");
		output.println("State");
		output.flush();
		
	}
	
	public void sendCoords(int active, int x, int y) throws IOException {
		
		System.out.println("Requesting Coords");
		
		output.println("Coords");
		output.flush();
		
		output.println(active);
		output.flush();
		
		output.println(x);
		output.flush();
		
		output.println(y);
		output.flush();
		
	}
	
	public void sendChange(int active, int x, int y, int promote) {
		
		output.println("Change");
		output.flush();
		
		output.println(active);
		output.flush();
		
		output.println(x);
		output.flush();
		
		output.println(y);
		output.flush();
		
		output.println(promote);
		output.flush();
		
	}
	
	public void sendMSG(String msg) {
		
		output.println("Chat");
		output.flush();
		
		output.println(name + ": " + msg);
		output.flush();
		
		LocalTime time = LocalTime.now();
		String timeNow = time.toString().substring(0, 5) + " | ";
		
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
		
		output.println("Echo");
		output.flush();
		
		output.println(echo);
		output.flush();
		
		String prMSG = "";
		
		if(!OnlineGUI.recordA.getText().equals("")) {
			prMSG = OnlineGUI.recordA.getText() + "\n";
		}
		else {
			prMSG = OnlineGUI.recordA.getText();
		}
		
		OnlineGUI.recordA.setText(prMSG + echo);
		
	}
	
	public void sendWish(String name) {
		
		output.println("Wish");
		output.flush();
		
		output.println(name);
		output.flush();
		
	}
	
	public void sendGiveUp() {
		
		output.println("Give Up");
		output.flush();
		
		output.println(this.name);
		output.flush();
		
		for(Player player: Game.players) {
			if(player.getName().equals(this.name)) {
				player.setCondition("Lost");
			}
		}
			
		new CheckConditions(game).endAll("");
		
	}
	
	private void startGame() {
		
		frame.dispose();
		System.out.println("Starting game");
		
	}
	
	public void sendDisconnect() throws IOException {
		
		output.println("Disconnected");
		output.flush();
		
		output.println(this.name);
		output.flush();
		
	}

}
