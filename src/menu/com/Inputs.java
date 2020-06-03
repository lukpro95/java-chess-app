package menu.com;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextField;

import gui.com.TextLimit;

public class Inputs {

	private JFrame frame;
	
	private JTextField port;
	private JTextField name;
	private JTextField ip;

	public Inputs(JFrame frame) {
		this.frame = frame;
	}
	
	public void createHostInputs() {
		port = new JTextField();
		name = new JTextField();
		
		port.setBounds(205, 30, 40, 20);
		port.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		port.setDocument(new TextLimit(4));
		port.setText(Menu.settings.getPortServer());
		port.setToolTipText("Provide a Port of the host here.");
		
		name.setBounds(177, 60, 70, 20);
		name.setDocument(new TextLimit(0, 14));
		name.setText(Menu.settings.getName());
		name.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		name.setToolTipText("Set your nickname.");
		
		frame.add(port);
		frame.add(name);
	}
	
	public void createJoinInputs() {
		ip 		= new JTextField();
		port 	= new JTextField("2222");
		name 	= new JTextField();
		
		ip.setBounds(147, 30, 75, 20);
		ip.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		ip.setDocument(new TextLimit(16));
		ip.setToolTipText("Provide an IP Address of the host here.");
		
		port.setBounds(227, 30, 40, 20);
		port.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		port.setDocument(new TextLimit(4));
		port.setToolTipText("Provide a Port of the host here.");
		
		name.setBounds(197, 60, 70, 20);
		name.setDocument(new TextLimit(0, 14));
		name.setText(Menu.settings.getName());
		name.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		name.setToolTipText("Set your nickname.");
		
		frame.add(ip);
		frame.add(port);
		frame.add(name);
	}
	
	public JTextField getName() {
		return this.name;
	}
	
	public JTextField getPort() {
		return this.port;
	}

	public JTextField getIP() {
		return this.ip;
	}
	
}