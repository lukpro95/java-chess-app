package gui.com;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chess.com.Game;
import players.com.Player;
import server.com.ClientMouth;
import server.com.ServerHost;

public class OnlineGUIButtons {
	
	private ServerHost sH;
	private ClientMouth cM;
	
	private static JPanel buttons;
	
	public static JButton wish;
	public static JButton quit;
	public static JButton start;
	public static JButton wait;
	public static JButton giveUp;
	
	public static boolean wishToPlay = false;
	
	public OnlineGUIButtons(GUI gui, JPanel buttons) {
		
		this.sH = gui.getsH();
		this.cM = gui.getcM();
		
		OnlineGUIButtons.buttons = buttons;
		
		if(sH != null) {
			getServerButtons();
		}
		
		else if (cM != null) {
			getClientButtons();
		}
		
	}
	
	// ------------------------ Server's Buttons ------------------------------------
	
	private void getServerButtons() {
		
		wish = new JButton();
		wish.setPreferredSize(new Dimension(115, 30));
		wish.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		wish.setIcon(new ImageIcon("GUI/wish.png"));
		
		wish.addActionListener(e -> {
			new Sounds("Select1");
			
			if(wishToPlay == false) {
				sH.wishList.add(sH.name);
				sH.sendEcho(sH.name + " wishes to play!");
				wishToPlay = true;
			}
			else {
				String drawMessage = "You are already on the list!";
				JOptionPane.showMessageDialog(null, drawMessage);
			}
		});
		
		buttons.add(wish);
		
		wait = new JButton();
		wait.setPreferredSize(new Dimension(115, 30));
		wait.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		wait.setIcon(new ImageIcon("GUI/wait.png"));
		
		giveUp = new JButton();
		giveUp.setPreferredSize(new Dimension(115, 30));
		giveUp.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		giveUp.setIcon(new ImageIcon("GUI/giveup.png"));
		
		giveUp.addActionListener(e -> {
			
			new Sounds("Select1");
			
			sH.sendGiveUp();
		});
		
		start = new JButton();
		start.setPreferredSize(new Dimension(115, 30));
		start.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		start.setIcon(new ImageIcon("GUI/start.png"));
		
		start.addActionListener(e -> {
			
			if(sH.wishList.size() > 1) {
				new Sounds("Select1");
				sH.sendOutStart();
				changeButtons(0);
			}
			else {
				String drawMessage = "Not enough players to start!";
				JOptionPane.showMessageDialog(null, drawMessage);
			}

		});
		
		buttons.add(start);
		
	}
	
	// ---------------- Client's buttons --------------------------
	
	private void getClientButtons() {
		
		JButton wish = new JButton();
		wish.setPreferredSize(new Dimension(115, 30));
		wish.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		wish.setIcon(new ImageIcon("GUI/wish.png"));
		
		wish.addActionListener(e -> {
			new Sounds("Select1");
			
			if(wishToPlay == false) {
				wishToPlay = true;
				cM.sendWish(cM.name);
				cM.sendEcho(cM.name + " wishes to play!");
			}
			else {
				String drawMessage = "You are already on the list!";
				JOptionPane.showMessageDialog(null, drawMessage);
			}
		});
		
		buttons.add(wish);
		
		giveUp = new JButton();
		giveUp.setPreferredSize(new Dimension(115, 30));
		giveUp.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		giveUp.setIcon(new ImageIcon("GUI/giveup.png"));
		
		giveUp.addActionListener(e -> {
			
			cM.sendGiveUp();
			changeButtons(11);
			
		});
		
		quit = new JButton();
		quit.setPreferredSize(new Dimension(115, 30));
		quit.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		quit.setIcon(new ImageIcon("GUI/quit.png"));
		
		quit.addActionListener(e -> {
			
			new Sounds("Select1");
			
			try {
				cM.sendDisconnect();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			System.exit(0);
		});
		
		buttons.add(quit);
		
	}
	
	// ---- Buttons Changer --------
	
	public static void changeButtons(int n) {
		
		if(n == 0) {
			
			for(Player player: Game.players) {
				
				if(player.getTeam().equals("")) {
					buttons.remove(start);
					buttons.add(wait);
					buttons.repaint();
				}
				else {
					buttons.remove(start);
					buttons.add(giveUp);
					buttons.repaint();
				}
				
			}

		}
		
		if(n == 1) {
			
			for(Player player: Game.players) {
				
				if(player.getTeam().equals("")) {
					buttons.remove(wait);
					buttons.add(start);
					buttons.repaint();
					
					wishToPlay = false;
				}
				else {
					buttons.remove(giveUp);
					buttons.add(start);
					buttons.repaint();
					
					wishToPlay = false;
				}
				
			}
			
		}
		
		if(n == 10) {
			buttons.remove(quit);
			buttons.add(giveUp);
			buttons.repaint();
		}
		
		if(n == 11) {
			buttons.remove(giveUp);
			buttons.add(quit);
			buttons.repaint();
			
			wishToPlay = false;
		}
		
	}

}