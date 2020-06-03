package menu.com;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import server.com.ClientMouth;
import server.com.ServerHost;

public class MultiInit {
	
	public void Host(JFrame frame, String name, String port) {
		if(name.equals("")) {
			JOptionPane.showMessageDialog(null, "Set up your nickname!");
		}
		else if(port.equals("")) {
			JOptionPane.showMessageDialog(null, "Provide host's Port!");
		}
		else {
			Menu.settings.setName(name);
			Menu.settings.setPortServer(port);
			
			frame.dispose();
			
			try {
				new Thread(new ServerHost()).start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	public void Join(JFrame frame, String name, String ip, String port, JLabel connection) {
		System.out.println("IP: " +ip);
		System.out.println("PORT: " +port);
		if(ip.equals("") || port.equals("")) {
			JOptionPane.showMessageDialog(null, "Provide a full Address of the Host!");
		}
		else if(name.equals("")) {
			JOptionPane.showMessageDialog(null, "Set up your nickname!");
		}
		else {
			Menu.settings.setName(name);
			connection.setText("Connecting to the server...");
			int iPort = Integer.parseInt(port);
			new Thread(new ClientMouth(frame, ip, iPort, name, connection)).start();
		}
	}

}
