package gui.com;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sounds {
	
	private String sound;
	
	public Sounds(String sound) {
		this.sound = sound;
		play();
	}
	
	private void play() {
		
	      try {
	          // Open an audio input stream.
	          URL url = this.getClass().getClassLoader().getResource("Sounds/" + sound + ".wav");
	          AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	          // Get a sound clip resource.
	          Clip clip = AudioSystem.getClip();
	          // Open audio clip and load samples from the audio input stream.
	          clip.open(audioIn);
	          clip.start();
	       } catch (UnsupportedAudioFileException e) {
	          e.printStackTrace();
	       } catch (IOException e) {
	          e.printStackTrace();
	       } catch (LineUnavailableException e) {
	          e.printStackTrace();
	       }
		
	}

}