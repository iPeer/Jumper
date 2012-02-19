package ipeer.jumper.sound;

import ipeer.jumper.util.Debug;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	public Sound(String name) {
		try {
			sound = Applet.newAudioClip(Sound.class.getResource(name));
		}
		catch (Exception e) {
			Debug.p("Unable to load sound "+name);
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					sound.play();
				}
			}.start();
		}
		catch (Exception e) {
			Debug.p("Unable to play sound!");
			e.printStackTrace();
		}
	}
	
	private AudioClip sound;
	public static final Sound select = new Sound("select.wav");
	public static final Sound levelstart = new Sound("levelstart.wav");

}
