package menu.com;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.com.GUI;
import gui.com.Sounds;
	
public class ButtonsSettings {
	
	private JFrame frame;
	private String menuName;
	
	private ImageIcon wCell;
	private ImageIcon bCell;

	public ButtonsSettings(String menuName, JFrame frame) {
		this.frame = frame;
		this.menuName = menuName;
	}
	
	public JButton getClassic(JLabel board) {
		JButton classic = new JButton("Classic");
		classic.setBounds(30, 30, 200, 35);
		classic.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		classic.setBackground(Color.LIGHT_GRAY);
		classic.setForeground(Color.BLACK);
		
		addButton(classic);
		
		classic.addActionListener(e -> {
			new Sounds("Select");
			GUI.setSquares("#606069", "#F0F0F0");
			board.setIcon(new ImageIcon("Boards/WhiteBlack.png"));
		});
		
		return classic;
	}
	
	public JButton getSepia(JLabel board) {
		JButton sepia = new JButton("Sepia");
		sepia.setBounds(30, 90, 200, 35);
		sepia.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		sepia.setBackground(Color.LIGHT_GRAY);
		sepia.setForeground(Color.BLACK);
		
		addButton(sepia);
		
		sepia.addActionListener(e -> {
			new Sounds("Select");
			GUI.setSquares("#422910", "#FFFFB2");
			board.setIcon(new ImageIcon("Boards/YellowBrown.png"));
		});
		
		return sepia;
	}
	
	public JButton getFrozen(JLabel board) {
		JButton frozen = new JButton("Frozen Fields");
		frozen.setBounds(30, 150, 200, 35);
		frozen.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		frozen.setBackground(Color.LIGHT_GRAY);
		frozen.setForeground(Color.BLACK);
		
		addButton(frozen);
		
		frozen.addActionListener(e -> {
			new Sounds("Select");
			GUI.setSquares("#F0F8FF", "#2F4F4F");
			board.setIcon(new ImageIcon("Boards/Frozen.png"));
		});
		
		return frozen;
	}
	
	public JButton getMetal(JLabel board) {
		JButton metal = new JButton("Ancient Marbles");
		metal.setBounds(30, 210, 200, 35);
		metal.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		metal.setBackground(Color.LIGHT_GRAY);
		metal.setForeground(Color.BLACK);
		
		addButton(metal);
		
		metal.addActionListener(e -> {
			new Sounds("Select");
			loadImages();
			GUI.setSquares("", "");
			GUI.setImages(wCell, bCell);
			board.setIcon(new ImageIcon("Boards/marbles.png"));
		});
		
		return metal;
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
			makeChanges();
			switch(menuName) {
				case "Menu": new Menu(frame);
					break;
			}
			new MenuWindow(frame);
		});
		
		return back;
	}
	
	private void addButton(JButton button) {
		frame.add(button);
	}
	
	private void loadImages() {
		BufferedImage whiteCell = null;
		try {
			{whiteCell = ImageIO.read(new File("Boards/marble.jpg"));}
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image wdimg = whiteCell.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		wCell = new ImageIcon(wdimg);
		
		BufferedImage blackCell = null;
		try {
			{blackCell = ImageIO.read(new File("Boards/marbleBlack.jpg"));}
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image bdimg = blackCell.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		bCell = new ImageIcon(bdimg);
	}
	
	private void makeChanges() {
		new Sounds("Select");
		
		frame.getContentPane().removeAll();
		frame.repaint();
	}
	
}