package menu.com;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import chess.com.Game;
import gui.com.Sounds;

public class Buttons {
	
	private JFrame frame;
	private String menuName;

	public Buttons(String menuName, JFrame frame) {
		this.frame = frame;
		this.menuName = menuName;
	}
	
	public JButton getSingle() {
		JButton play = new JButton("Train alone!");
		play.setBounds(30, 30, 200, 35);
		play.setToolTipText("Click here play against yourself.");
		play.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		play.setBackground(Color.LIGHT_GRAY);
		play.setForeground(Color.BLACK);
		
		addButton(play);
		
		play.addActionListener(e -> {
			makeChanges();
			new MenuSingle(frame);
			createWindow();
		});
		
		return play;
	}
	
	public JButton getMulti() {
		JButton playM = new JButton("Play with others!");
		playM.setBounds(30, 90, 200, 35);
		playM.setToolTipText("Click here to play against players.");
		playM.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		playM.setBackground(Color.LIGHT_GRAY);
		playM.setForeground(Color.BLACK);
		
		addButton(playM);
		
		playM.addActionListener(e -> {
			makeChanges();
			new MenuMulti(frame);
			createWindow();
		});
		
		return playM;
	}
	
	public JButton getSettings() {
		JButton settings = new JButton("Settings!");
		settings.setBounds(30, 150, 200, 35);
		settings.setToolTipText("Click here to change board style.");
		settings.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		settings.setBackground(Color.LIGHT_GRAY);
		settings.setForeground(Color.BLACK);
		
		addButton(settings);
		
		settings.addActionListener(e -> {
			makeChanges();
			new MenuSettings(frame);
			MenuWindow window = new MenuWindow(frame);
			window.stylePanel();
		});
		
		return settings;
	}
	
	public JButton getExit() {
		JButton exit = new JButton("Exit!");
		exit.setBounds(30, 280, 80, 20);
		exit.setToolTipText("Click here to exit menu.");
		exit.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		exit.setBackground(Color.LIGHT_GRAY);
		exit.setForeground(Color.BLACK);
		
		addButton(exit);
		
		exit.addActionListener(e -> {
			System.exit(0);
		});
		
		return exit;
	}
	
	public JButton getPlayBoth() {
		JButton manual = new JButton("Play both sides!");
		manual.setBounds(30, 30, 200, 35);
		manual.setToolTipText("Click here play against yourself.");
		manual.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		manual.setBackground(Color.LIGHT_GRAY);
		manual.setForeground(Color.BLACK);
		
		addButton(manual);
		
		manual.addActionListener(e -> {
			frame.dispose();
			new Game(null, null, "");
		});
		
		return manual;
	}
	
	public JButton getPlayWhite() {
		JButton versusBlack = new JButton("Play versus Black!");
		versusBlack.setBounds(30, 90, 200, 35);
		versusBlack.setToolTipText("Click here to play as White pieces");
		versusBlack.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		versusBlack.setBackground(Color.LIGHT_GRAY);
		versusBlack.setForeground(Color.BLACK);
		
		addButton(versusBlack);
		
		versusBlack.addActionListener(e -> {
			frame.dispose();
			new Game(null, null, "Black");
		});
		
		return versusBlack;
	}
	
	public JButton getPlayBlack() {
		JButton versusWhite = new JButton("Play versus White!");
		versusWhite.setBounds(30, 150, 200, 35);
		versusWhite.setToolTipText("Click here to play as Black pieces");
		versusWhite.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		versusWhite.setBackground(Color.LIGHT_GRAY);
		versusWhite.setForeground(Color.BLACK);
		
		addButton(versusWhite);
		
		versusWhite.addActionListener(e -> {
			frame.dispose();
			new Game(null, null, "White");
		});
		
		return versusWhite;
	}
	
	public JButton getHost() {
		JButton host = new JButton("Host a Game");
		host.setBounds(30, 30, 200, 35);
		host.setToolTipText("Click here to host a game of chess.");
		host.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		host.setBackground(Color.LIGHT_GRAY);
		host.setForeground(Color.BLACK);
		
		addButton(host);
		
		host.addActionListener(e -> {
			makeChanges();
			new MenuHost(frame);
			createWindow();
		});
		
		return host;
	}
	
	public JButton getJoin() {
		JButton join = new JButton("Join a Game");
		join.setBounds(30, 90, 200, 35);
		join.setToolTipText("Click here to join a server.");
		join.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		join.setBackground(Color.LIGHT_GRAY);
		join.setForeground(Color.BLACK);
		
		addButton(join);
		
		join.addActionListener(e -> {
			makeChanges();
			new MenuJoin(frame);
			createWindow();
		});
		
		return join;
	}
	
	public JButton getStartHosting(JTextField name, JTextField port) {
		JButton host = new JButton("Start Server!");
		host.setBounds(320, 30, 120, 20);
		host.setToolTipText("Click here to host a game of chess.");
		host.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		host.setBackground(Color.LIGHT_GRAY);
		host.setForeground(Color.BLACK);
		
		addButton(host);
		
		host.addActionListener(e -> {
			new MultiInit().Host(frame, name.getText(), port.getText());
		});
		
		return host;
	}
	
	public JButton getStartJoining(JTextField name, JTextField ip, JTextField port, JLabel connection) {
		JButton join = new JButton("Start Connection!");
		join.setName("JoinGame");
		join.setBounds(300, 30, 150, 20);
		join.setToolTipText("Click here to host a game of chess.");
		join.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		join.setBackground(Color.LIGHT_GRAY);
		join.setForeground(Color.BLACK);
		
		addButton(join);
		
		join.addActionListener(e -> {
			new MultiInit().Join(frame, name.getText(), ip.getText(), port.getText(), connection);
		});
		
		return join;
	}
	
	public JButton getBack() {
		JButton back = new JButton("Back");
		back.setBounds(30, 280, 80, 20);
		back.setToolTipText("Click here to get back to main menu");
		back.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		back.setBackground(Color.LIGHT_GRAY);
		back.setForeground(Color.BLACK);
		
		addButton(back);
		
		back.addActionListener(e -> {
			System.out.println(menuName);
			makeChanges();
			switch(menuName) {
				case "Menu": new Menu(frame);
					break;
				case "MenuMulti": new MenuMulti(frame);
					break;
			}
			createWindow();
		});
		
		return back;
	}
	
	private void addButton(JButton button) {
		frame.add(button);
	}
	
	private void makeChanges() {
		new Sounds("Select");
		frame.getContentPane().removeAll();
		frame.repaint();
	}
	
	private void createWindow() {
		MenuWindow window = new MenuWindow(frame);
		window.stylePanel();
		window.stylePicture();
	}
	
}