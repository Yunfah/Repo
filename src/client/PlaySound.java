package client;

import java.applet.*;
import java.io.File;
import java.net.*;
import java.util.Timer;

public class PlaySound {
	private AudioClip sound;

	public void playElevatorLoop() {
		try{
			File file = new File("files/elevator.wav");
			sound =  Applet.newAudioClip(file.toURI().toURL());
		} catch(MalformedURLException e) {}
		sound.loop();
	}
	public void playDuel() {
		try{
		File file = new File("files/duel.wav");
		sound =  Applet.newAudioClip(file.toURI().toURL());
	} catch(MalformedURLException e) {}
	sound.loop();

	}
	public void stopLoop() {
		sound.stop();
	}

	public static void main(String[] args) {
		PlaySound t1 = new PlaySound();
		t1.playElevatorLoop();


	}
}