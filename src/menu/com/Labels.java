package menu.com;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Labels {
	
	private JFrame frame;
	private String myIP;
	
	private JLabel portL;
	private JLabel connection;
	private JLabel ipL;
	private JLabel nameL;

	public Labels(JFrame frame) {
		this.frame = frame;
	}
	
	public void createHostLabels() {
		portL = new JLabel("   Set Server's Port Number: ");
		nameL = new JLabel("   Set your Nickname: ");
		ipL = new JLabel();
		connection = new JLabel();
		
		fetchIP();
		
		portL.setBounds(30, 27, 220, 26);
		portL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		portL.setForeground(Color.WHITE);
		
		nameL.setBounds(30, 57, 220, 26);
		nameL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		nameL.setForeground(Color.WHITE);
		
		ipL.setBounds(30, 87, 220, 26);
		ipL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		ipL.setForeground(Color.WHITE);
		ipL.setText("   Your IP Address is:    " + myIP);
		
		connection.setBounds(130, 340, 300, 15);
		connection.setForeground(Color.WHITE);
		connection.setText("");

		frame.add(portL);
		frame.add(nameL);
		frame.add(ipL);
		frame.add(connection);
	}
	
	public void createJoinLabels() {
		ipL		= new JLabel("   Server's Address                            :");
		nameL	= new JLabel("   Set your Nickname: ");
		connection = new JLabel();
		
		ipL.setBounds(30, 27, 240, 26);
		ipL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		ipL.setForeground(Color.WHITE);
		
		nameL.setBounds(30, 57, 240, 26);
		nameL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		nameL.setForeground(Color.WHITE);
		
		connection.setBounds(130, 340, 300, 15);
		connection.setForeground(Color.WHITE);
		connection.setText("");
		
		frame.add(nameL);
		frame.add(ipL);
		frame.add(connection);
	}
	
	private void fetchIP() {
		URL whatismyip;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com/");
			BufferedReader in = new BufferedReader(new InputStreamReader(
	                whatismyip.openStream()));

			myIP = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JLabel getConnection() { 
		return connection;
	}

}