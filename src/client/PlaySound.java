package client;

import java.applet.*;
import java.io.File;
import java.net.*;

public class PlaySound {
	private AudioClip sound;

	public void playElevator() {
		try{
			File file = new File("/Users/yammasarwari/git/Repo/Repo/files/elevator.wav");
			sound =  Applet.newAudioClip(file.toURI().toURL());
		} catch(MalformedURLException e) {}
		sound.play();
	}
}