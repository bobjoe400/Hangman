package main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Main {

	private static boolean inGame;
	private static boolean exit;

	static class MusicThread implements Runnable {
		public void run() {
			try {
				FileInputStream fs = new FileInputStream("Resources/music.wav");
				BufferedInputStream buffered = new BufferedInputStream(fs);
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(buffered);
				Clip clip = AudioSystem.getClip();
				clip.open(inputStream);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Thread music = new Thread(new MusicThread());
		music.start();
		inGame = false;
		new Menu();
	}

	public static void checkVisible(JFrame f, int diff, boolean inGame) {
		if (f instanceof Game && !inGame) {
			f.dispose();
			new Menu();
		} else if (f instanceof Menu && inGame) {
			f.dispose();
			new Game("cat", diff);
		} else {
			f.setVisible(true);
		}
	}


}