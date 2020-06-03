package menu.com;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class MainClass {
	
	public static void main(String[] args) {
		
    	JFrame frame = new JFrame();
		
		try {
        EventQueue.invokeLater(() -> {
            new Menu(frame);
        });
		}
		catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
}
