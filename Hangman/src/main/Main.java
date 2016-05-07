package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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