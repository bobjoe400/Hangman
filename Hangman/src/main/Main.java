package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Main {

	private static MusicThread musicThread;
	private static boolean isPlaying = true;
	private static String loc;
	private static JFrame currFrame;
	private static JFrame prevFrame;
	private static JPanel statusBar;
	private static JLabel statusLabel;
	private static int diff;
	private static int file;
	private static int numSongs;
	private static String word;

	static class MusicThread extends Thread {

		Clip clip;

		public MusicThread() {
			super();
		}

		@Override
		public void run() {
			try {
				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			setMusic();
		}

		public void stopMusic() {
			isPlaying = false;
			clip.stop();
		}

		public void startMusic() {
			isPlaying = true;
			clip.loop(-1);
		}

		public void setMusic() {
			try {
				if (clip.isOpen()) {
					clip.stop();
					clip.drain();
					clip.close();
				}
				FileInputStream fs = new FileInputStream("Resources/music" + file + ".wav");
				BufferedInputStream buffered = new BufferedInputStream(fs);
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(buffered);
				clip.open(inputStream);
				clip.loop(-1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Main(int file, String loc, int diff, JFrame f, JFrame p) {
		Main.file = file;
		musicThread = new MusicThread();
		musicThread.start();
		Main.loc = loc;
		Main.diff = diff;
		statusBar = new JPanel();
		statusLabel = new JLabel();
		prevFrame = p;
		currFrame = f;
		numSongs();
		updateFrame();
	}

	public static void main(String[] args) {
		new Main(0, "M", -1, new JFrame(), new JFrame());
	}

	public static void updateFrame() {
		prevFrame = currFrame;
		JFrame f = null;
		switch (loc) {
		case "M":
			f = new MainMenu();
			break;
		case "G":
			f = new Game(diff);
			break;
		case "O":
			f = new Options();
			break;
		}
		System.out.println(currFrame.getTitle() + "|" + prevFrame.getTitle());
		if (!prevFrame.getTitle().equals(f.getTitle()) || currFrame.getTitle().equals("")) {
			for (int i = 0; i < Frame.getFrames().length; i++) {
				Frame.getFrames()[i].dispose();
			}
			currFrame = f;
			f.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		}

		System.out.println("hit2");
		prevFrame = currFrame;
		JPanel mute = new JPanel();
		mute.setLayout(new BoxLayout(mute, BoxLayout.X_AXIS));
		mute.setOpaque(false);
		/*if(currFrame.getTitle().equals("Hangman")){
			newButton("New game", mute, currFrame, Component.RIGHT_ALIGNMENT, 16);
		}*/
		mute.add(Box.createHorizontalGlue());
		newButton("Mute music", mute, currFrame, Component.RIGHT_ALIGNMENT, 16);
		addStatus(currFrame);
		currFrame.setPreferredSize(new Dimension(600, 600));
		currFrame.add(mute, BorderLayout.NORTH);
		currFrame.pack();
		currFrame.setLocationRelativeTo(null);
		currFrame.revalidate();
		currFrame.repaint();
		currFrame.setVisible(true);
	}

	public static JButton newButton(String text, Container container, JFrame currFrame, float align, int size) {
		JButton txt = new JButton(text);
		txt.setFont(new Font("Times New Roman", Font.PLAIN, size));
		txt.setAlignmentX(align);
		txt.addActionListener(new NavigationListener(currFrame));
		txt.setSelected(false);
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

	public static void addStatus(JFrame f) {
		statusBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		String status = "";
		status = "Song selected: " + getMusicFile() + " | Music: " + ((musicStatus()) ? "On" : "Off");
		if (f instanceof Game) {
			Game g = (Game) f;
			status = "Difficulty: " + g.diffString() + " | " + status;
		}
		System.out.println(status);
		statusLabel.setText(status);
		statusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		statusBar.add(statusLabel);
		f.add(statusBar, BorderLayout.SOUTH);
	}

	public static void updateBar() {
		statusLabel.repaint();
	}
	
	public static void newGame(){
		currFrame.setTitle("");
	}

	public static void setLoc(String newLoc) {
		loc = newLoc;
	}

	public static void setDiff(int newDiff) {
		diff = newDiff;
	}

	public static void numSongs() {
		File resources = new File("Resources");
		int counter = 0;
		for (File f : resources.listFiles()) {
			if (f.getName().endsWith(".wav")) {
				counter++;
			}
		}
		numSongs = counter;
	}

	public static void setMusic(boolean a, boolean b) {
		int newFile = file;
		while (newFile == file) {
			newFile = Math.abs(new Random().nextInt() % numSongs);
		}
		if (a) {
			if (b) {
				musicThread.stopMusic();
				return;
			}
			musicThread.startMusic();
			return;
		}
		file = newFile;
		musicThread.setMusic();
	}
	

	public static int getMusicFile() {
		return file;
	}

	public static boolean musicStatus() {
		return isPlaying;

	}
}