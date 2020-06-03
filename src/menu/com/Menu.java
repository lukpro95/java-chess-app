package menu.com;

import javax.swing.JFrame;

import settings.com.Settings;

public class Menu {

	public static Settings settings = new Settings();
	private JFrame frame;
	
	public Menu(JFrame frame) {
		this.frame = frame;
		initMenu();
	}
	
	private void initMenu() {
		createButtons();
		MenuWindow window = new MenuWindow(frame);
		window.stylePanel();
		window.stylePicture();
	}
	
	protected void createButtons() {
		Buttons buttons = new Buttons("MenuMain", frame);
	
		buttons.getSingle();
		buttons.getMulti();
		buttons.getSettings();
		buttons.getExit();
	}
	
	public Settings getSettings() {
		return settings;
	}
	
}