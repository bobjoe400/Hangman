package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Main {

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
		makeFrame(0, 0);
	}

	public static void makeFrame(int dec, int diff) {
		if (Frame.getFrames() != null) {
			for (int i = 0; i < Frame.getFrames().length; i++) {
				Frame.getFrames()[i].dispose();
			}
		}
		JFrame f = null;
		switch (dec) {
		case 0:
			f = new MainMenu();
			break;
		case 1:
			f = new Game(diff);
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

	public static void checkVisible(Object f, int diff, boolean inGame) {
		if (f instanceof Game && !inGame) {
			makeFrame(0, 0);
		} else if (f instanceof MainMenu && inGame) {
			makeFrame(1, diff);
		}
	}

}