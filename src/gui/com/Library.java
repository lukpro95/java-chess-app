package gui.com;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Library {
	
	private ImageIcon piece;
	
	public Library(){
		
	}
	
	public ImageIcon getPiece(String pieceName) {
		
		BufferedImage img = null;
		try {
			{img = ImageIO.read(new File("Icons/" + pieceName + ".png"));}
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image dimg = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		piece = new ImageIcon(dimg);
		
		return piece;

	}
	
	public JLabel getTempSquare(int showSquare, int active) {
		
		JLabel temp = new JLabel();
		
		ImageIcon activeSquare = new ImageIcon("Icons/ActiveSquare.png");
		ImageIcon greenSquare = new ImageIcon("Icons/NeutralSquare.png");
		ImageIcon redSquare = new ImageIcon("Icons/EnemySquare.png");
		
		if(showSquare == 0 ) {temp.setIcon(greenSquare);}
		if(showSquare < 0 && active > 0) {temp.setIcon(redSquare);}
		if(showSquare > 0 && active < 0) {temp.setIcon(redSquare);}
		if((showSquare == active)) {temp.setIcon(activeSquare);}
		
		GUI.getTempFrame().add(temp);
		GUI.getTempFrame().repaint();
		
		return temp;
	}
	
}