package menu.com;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuJoin {
	
	private JFrame frame;
	private Labels labels;
	private Inputs inputs;
	
	public MenuJoin(JFrame frame) {
		this.frame = frame;
		initMulti();
	}
	
	private void initMulti() {
		
		labels = new Labels(frame);
		inputs = new Inputs(frame);
		
		labels.createJoinLabels();
		inputs.createJoinInputs();
		
		Buttons buttons = new Buttons("MenuMulti", frame);
		buttons.getStartJoining(inputs.getName(), inputs.getIP(), inputs.getPort(), labels.getConnection());
		buttons.getBack();
	}
	
	public JLabel getConnection() {
		return labels.getConnection();
	}
	
}