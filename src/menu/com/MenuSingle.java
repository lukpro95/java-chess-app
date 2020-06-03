package menu.com;

import javax.swing.JFrame;

public class MenuSingle {
	
	private JFrame frame;
	
	public MenuSingle(JFrame frame){
		this.frame = frame;
		initSingle();
	}
	
	private void initSingle() {
		Buttons buttons = new Buttons("Menu", frame);
		
		buttons.getPlayBoth();
		buttons.getPlayWhite();
		buttons.getPlayBlack();
		buttons.getBack();
	}
	
}