package menu.com;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MenuWindow {

	private static JFrame frame;
	private static JPanel imagePanel;
	private static JLabel imageLabel;
	
	private static Dimension resolution = new Dimension(500, 400);

	public MenuWindow(JFrame frame) {
		MenuWindow.frame = frame;
		styleFrame();
	}
	
	private void styleFrame() {
		frame.setSize(resolution);
		frame.setTitle("Chess Game");
		frame.setIconImage(new ImageIcon("Icons/small.png").getImage());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public void stylePicture() {
		imageLabel = new JLabel();
		imageLabel.setBounds(250, 160, 170, 149);
		imageLabel.setIcon(new ImageIcon("Icons/figure.png"));
		imageLabel.setOpaque(false);
		imageLabel.setLayout(null);
		
		imagePanel.add(imageLabel);
	}
	
	public void stylePanel() {
		imagePanel = new JPanel();
		imagePanel.setBackground(Color.DARK_GRAY);
		imagePanel.setSize(resolution);
		imagePanel.setLayout(null);
		
		frame.add(imagePanel);
	}
	
}