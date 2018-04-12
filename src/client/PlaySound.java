package client;

import java.applet.*;
import java.io.File;
import java.net.*;
import java.util.Timer;

public class PlaySound extends Thread {
	private AudioClip sound;

	public void playElevatorLoop() {
		sound = null;
		try{
			File file = new File("files/elevator.wav");
			sound =  Applet.newAudioClip(file.toURI().toURL());
		} catch(MalformedURLException e) {}
		sound.loop();
	}
	public void playDuel() {
		sound = null;
		try{
		File file = new File("files/duel.wav");
		sound =  Applet.newAudioClip(file.toURI().toURL());
	} catch(MalformedURLException e) {}
	sound.play();

	}
	public void resumeElevatorLoop() {
		try {
			Thread.sleep(7000);
			playElevatorLoop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void stopLoop() {
		sound.stop();
	}

	public static void main(String[] args) {
		PlaySound t1 = new PlaySound();
		t1.playElevatorLoop();


	}
}