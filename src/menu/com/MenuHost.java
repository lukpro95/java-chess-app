package menu.com;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuHost {
	
	private JFrame frame;
	
	private Labels labels;
	private Inputs inputs;
	
	public MenuHost(JFrame frame) {
		this.frame = frame;
		initMulti();
	}
	
	private void initMulti() {
		labels = new Labels(frame);
		inputs = new Inputs(frame);
		
		labels.createHostLabels();
		inputs.createHostInputs();
		
		Buttons buttons = new Buttons("MenuMulti", frame);
		buttons.getStartHosting(inputs.getName(), inputs.getPort());
		buttons.getBack();
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public JLabel getConnection() {
		return labels.getConnection();
	}
	
}