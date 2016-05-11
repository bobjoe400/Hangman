package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main {

	private static Thread musicThread;
	private static MusicThread music;
	private static String loc;
	private static JFrame currFrame;
	private static int diff;
	private static int file;
	private static String word = null;

	static class MusicThread implements Runnable {

		volatile boolean play;

		public MusicThread(boolean play) {
			this.play = play;
		}

		public void run() {
			try {
				FileInputStream fs = new FileInputStream("Resources/music" + file + ".wav");
				BufferedInputStream buffered = new BufferedInputStream(fs);
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(buffered);
				Clip clip = AudioSystem.getClip();
				clip.open(inputStream);
				while (play) {
					if (!clip.isRunning()) {
						clip.start();
					}
				}
				clip.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void toggle(boolean b) {
			if(b){
				play = !play;
			}
			new Main(file, play, loc, diff, currFrame);
		}
	}
	
	public Main(int file, boolean play, String loc, int diff, JFrame f){
		this.file = file;
		music = new MusicThread(play);
		musicThread = new Thread(music);
		musicThread.start();
		this.loc = loc;
		this.diff = diff;
		currFrame = f;
		updateFrame();
	}

	public static void main(String[] args) {
		new Main(0, true, "M", -1, new JFrame());
	}

	public static void updateFrame() {
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
			f = new Game(diff,word);
			Game g  = (Game) f;
			word = g.getWord();
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
		currFrame = f;
		currFrame.pack();
		currFrame.setLocationRelativeTo(null);
		currFrame.setVisible(true);
	}

	public static JButton newButton(String text, Container container, JFrame currFrame, float align, int size) {
		JButton txt = new JButton(text);
		txt.setFont(new Font("Times New Roman", Font.PLAIN, size));
		txt.setAlignmentX(align);
		txt.addActionListener(new NavigationListener(currFrame));
		container.add(txt);
		return txt;
	}

	public static void newMenu(ArrayList<JMenuItem> items, JFrame currFrame) {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		for (JMenuItem item : items) {
			item.addActionListener(new NavigationListener(currFrame));
			fileMenu.add(item);
		}
		menuBar.add(fileMenu);
		currFrame.setJMenuBar(menuBar);
	}

	public static void addStatus(JFrame f, Container c) {
		String s = "";
		if (f instanceof Game) {
			Game g = (Game) f;
			s = "Difficulty: " + g.diffString() + "Music: " + (musicStatus() ? "On" : "Off");
		} else {
			s = "Music: " + musicStatus();
		}
		JLabel j = new JLabel(s);
		j.setAlignmentX(Component.RIGHT_ALIGNMENT);
		c.add(j, BorderLayout.SOUTH);
	}

	public static void setLoc(String newLoc) {
		loc = newLoc;
	}

	public static void setDiff(int newDiff) {
		diff = newDiff;
	}

	public static void setMusic(int song, boolean mute) {
		file = song;
		if (mute) {
			music.toggle(true);
		}
		music.toggle(false);
	}

	public static boolean musicStatus() {
		return !musicThread.isInterrupted();
	}

	public static int getMusicFile() {
		return file;
	}
}