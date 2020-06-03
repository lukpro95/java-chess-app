package menu.com;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuSettings {

	private JFrame frame;
	
	public MenuSettings(JFrame frame) {
		
		this.frame = frame;
		initSettings();
		
	}
	
	private void initSettings() {
		JLabel imageLabel = new JLabel();
		imageLabel.setBounds(250, 60, 200, 200);
		imageLabel.setIcon(new ImageIcon("Boards/WhiteBlack.png"));
		imageLabel.setOpaque(false);
		imageLabel.setLayout(null);
		
		ButtonsSettings buttons = new ButtonsSettings("Menu", frame);
		
		buttons.getClassic(imageLabel);
		buttons.getSepia(imageLabel);
		buttons.getFrozen(imageLabel);
		buttons.getMetal(imageLabel);
		buttons.getBack();
		
		frame.add(imageLabel);
	}
	
}