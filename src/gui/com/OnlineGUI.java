package gui.com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import server.com.ClientMouth;
import server.com.ServerHost;

public class OnlineGUI implements KeyListener {

	private GUI gui;
	private ServerHost sH;
	private ClientMouth cM;
	
	private JPanel onlineSpace;
	
	private JPanel players;
	private JPanel lobby;
	private JPanel chat;
	private JPanel record;
	
	private static JPanel buttons;
	
	private static JScrollPane			scrollR		= null;
	private static JScrollPane			scrollRR		= null;
	
	public static JTextArea lobbyA = new JTextArea();
	public static JTextArea chatA = new JTextArea();
	public static JTextField chatT = new JTextField();
	public static JTextArea recordA = new JTextArea();
	
	public static JLabel whiteP = new JLabel();
	public static JLabel blackP = new JLabel();
	
	public OnlineGUI(GUI gui) {
		
		this.gui = gui;
		this.sH = gui.getsH();
		this.cM = gui.getcM();
		
		setComponents();
	}
	
	private void setComponents() {
		
		onlineSpace = new JPanel();
		
		setPlayers();
		setLobby();
		setChat();
		setRecord();
		setButtons();
		
		// main Panel
		
		onlineSpace.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		onlineSpace.setBackground(Color.gray);
		onlineSpace.setBounds(755, 12, 310, 740);
		onlineSpace.setLayout(new FlowLayout());
		
		onlineSpace.add(players);
		onlineSpace.add(lobby);
		onlineSpace.add(chat);
		onlineSpace.add(record);
		onlineSpace.add(buttons);
		
		gui.mainFrame.add(onlineSpace);
		
	}
	
	private void setPlayers() {
		
		players = new JPanel();
		players.setPreferredSize(new Dimension(300, 62));
		players.setBackground(Color.LIGHT_GRAY);
		players.setLayout(null);
		
		JLabel playersL = new JLabel();
		playersL.setSize(300, 62);
		playersL.setIcon(new ImageIcon("GUI/players.png"));
		
		whiteP = new JLabel("Awaiting...");
		whiteP.setBounds(7, 10, 230, 18);
		whiteP.setIcon(new ImageIcon("GUI/white.png"));
		
		blackP = new JLabel("Awaiting...");
		blackP.setBounds(7, 30, 230, 18);
		blackP.setIcon(new ImageIcon("GUI/black.png"));
		
		players.add(whiteP);
		players.add(blackP);
		players.add(playersL);
		
	}
	
	private void setLobby() {
		
		lobby = new JPanel();
		lobby.setPreferredSize(new Dimension(300, 112));
		lobby.setBackground(Color.LIGHT_GRAY);
		lobby.setLayout(null);
		
		JLabel lobbyL = new JLabel();
		lobbyL.setSize(300, 112);
		lobbyL.setIcon(new ImageIcon("GUI/lobby.png"));
		
		lobbyA.setBackground(Color.LIGHT_GRAY);
		lobbyA.setBounds(10, 5, 260, 90);
		lobbyA.setFocusable(false);
		
		lobby.add(lobbyL);
		lobby.add(lobbyA);
		
	}
	
	private void setChat() {
		
		chat = new JPanel();
		chat.setPreferredSize(new Dimension(300, 380));
		chat.setBackground(Color.LIGHT_GRAY);
		chat.setLayout(null);
		
		chatA.setBackground(Color.LIGHT_GRAY);
		chatA.setBounds(5, 5, 290, 340);
		chatA.setFocusable(false);
		chatA.setLineWrap(true);
		chatA.setWrapStyleWord(true);
		
		DefaultCaret caret = (DefaultCaret)chatA.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		scrollR = new JScrollPane (chatA);
		scrollR.setBounds(chatA.getBounds());
		
		chatT.setBackground(Color.LIGHT_GRAY);
		chatT.setBounds(5, 361, 230, 15);
		
		JLabel chatL = new JLabel();
		chatL.setSize(300, 380);
		chatL.setIcon(new ImageIcon("GUI/chat.png"));
		
		chatT.addKeyListener(this);
		
		chat.add(chatT);
		chat.add(chatL);
		chat.add(scrollR);
		
	}
	
	private void setRecord() {
		
		record = new JPanel();
		record.setPreferredSize(new Dimension(300, 112));
		record.setBackground(Color.LIGHT_GRAY);
		record.setLayout(null);
		
		recordA.setBackground(Color.LIGHT_GRAY);
		recordA.setBounds(5, 5, 290, 102);
		recordA.setFocusable(false);
		recordA.setLineWrap(true);
		recordA.setWrapStyleWord(true);
		
		DefaultCaret caret = (DefaultCaret)recordA.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		scrollRR = new JScrollPane (recordA);
		scrollRR.setBounds(recordA.getBounds());
		
		JLabel reordL = new JLabel();
		reordL.setSize(300, 112);
		reordL.setIcon(new ImageIcon("GUI/record.png"));
		
		record.add(reordL);
		record.add(scrollRR);
		
	}
	
	private void setButtons() {
		
		buttons = new JPanel();
		
		buttons.setPreferredSize(new Dimension(240, 35));
		buttons.setOpaque(false);
		
		new OnlineGUIButtons(gui, buttons);
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getSource() == chatT) {
			
			if(e.getKeyChar() == KeyEvent.VK_ENTER) {
				if(cM == null) {
					sH.sendMSG(chatT.getText());
					chatT.setText("");
				}
				if(sH == null) {
					cM.sendMSG(chatT.getText());
					chatT.setText("");
				}
			}
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
}