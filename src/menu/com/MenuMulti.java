package menu.com;

import javax.swing.JFrame;

public class MenuMulti {
	
	private JFrame frame;

	public MenuMulti(JFrame frame) {
		this.frame = frame;
		createButtons();
	}
	
	private void createButtons() {
		Buttons buttons = new Buttons("Menu", frame);
		
		buttons.getHost();
		buttons.getJoin();
		buttons.getBack();
	}
	
}