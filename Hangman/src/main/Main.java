package main;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Main {

	private static Thread music;
	private static String loc;

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
		music = new Thread(new MusicThread());
		music.start();
		loc = "M";
		makeFrame(-1);
	}

	public static void makeFrame(int diff) {
		if (Frame.getFrames() != null) {
			for (int i = 0; i < Frame.getFrames().length; i++) {
				Frame.getFrames()[i].dispose();
			}
		}
		JFrame f = null;
		switch (loc) {
		case "M":
			f = new MainMenu();
			break;
		case "G":
			f = new Game(diff);
			break;
		case "I":
			f = new Instructions();
			break;
		}
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public static void setLoc(String newLoc) {
		loc = newLoc;
	}
}