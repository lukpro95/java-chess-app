package ai.com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import chess.com.Game;

public class Delay {
	
	private Timer timer;
	private int second;
	
	public Delay(Game game, int time){
		
		int second = secondGenerator(time);
		
		this.timer = new Timer(second*1000, new ActionListener(){
			@Override
		    public void actionPerformed(ActionEvent e) {
				new AutoBot(game);
				((Timer)e.getSource()).stop();
		    }
		});
			
	}
	
	private int secondGenerator(int time) {
		
		ArrayList<Integer> seconds = new ArrayList<>();
		seconds.clear();
		
		for(int s = 1; s < time+1; s++) {
			seconds.add(s);
		}
		
		Random r = new Random();
		int RandIndex = r.nextInt(seconds.size()-0)+0;
		int second = seconds.get(RandIndex);
		
		seconds.clear();
		System.out.println("Delay: " + second);
		
		this.second = second;
		return this.second;
	}
	
	public Timer getDelay() {
		return this.timer;
	}
	
}
