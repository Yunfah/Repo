package client;

import java.applet.*;
import java.io.File;
import java.net.*;
import java.util.Timer;

public class PlaySound extends Thread {
	private AudioClip sound;

	public void playElevatorLoop() {
		try{
			File file = new File("/Users/yammasarwari/git/Repo/Repo/files/elevator.wav");
			sound =  Applet.newAudioClip(file.toURI().toURL());
		} catch(MalformedURLException e) {}
		sound.loop();
	}
	public void playDuel() {
		try{
		File file = new File("/Users/yammasarwari/git/Repo/Repo/files/duel.wav");
		sound =  Applet.newAudioClip(file.toURI().toURL());
	} catch(MalformedURLException e) {}
	sound.loop();

	}
	public void stopLoop() {
		sound.stop();
	}

	public static void main(String[] args) throws InterruptedException {
		PlaySound t1 = new PlaySound();
		t1.playElevatorLoop();
		Thread.sleep(10000);
		t1.stopLoop();
		Thread.sleep(5000);
		t1.playDuel();
		Thread.sleep(5000);
		t1.stopLoop();


	}
}