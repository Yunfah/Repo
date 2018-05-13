package client;

import java.applet.*;
import java.io.File;
import java.net.*;
import java.util.Timer;

public class PlaySound {
	private AudioClip sound;
	private AudioClip pop;
	private AudioClip lose;
	private AudioClip win;
	private boolean tast;

	public void playElevatorLoop() {
		try{
			File file = new File("files/elevator.wav");
			sound =  Applet.newAudioClip(file.toURI().toURL());
		} catch (MalformedURLException e) {}
		sound.loop();
	}
//
//	public void lose() {
//		try {
//			File file = new File("files/lose.wav");
//			lose = Applet.newAudioClip(file.toURI().toURL());
//		} catch (MalformedURLException e) {}
//		lose.play();
//		}
//
//	public void win() {
//		try {
//			File file = new File("files/win.wav");
//			win = Applet.newAudioClip(file.toURI().toURL());
//		} catch (MalformedURLException e) {}
//		win.play();
//	}

//	public void pop() {
//		if (!tast) {
//			try {
//				File file = new File("files/pop.wav");
//				pop = Applet.newAudioClip(file.toURI().toURL());
//			} catch (MalformedURLException e) {
//			}
//			tast = true;
//			pop.play();
//		} else {
//			pop.play();
//		}
//	}
//
//	public void stopLoop() {
//		sound.stop();
//	}

	public static void main(String[] args) {
		PlaySound t1 = new PlaySound();
		t1.playElevatorLoop();





	}
}