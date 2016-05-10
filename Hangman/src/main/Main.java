package main;

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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main {

	private static Thread music;
	private static String loc;
	private static JFrame currFrame;
	private static int diff;

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
		diff = -1;
		currFrame = new JFrame();
		updateFrame();
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
		currFrame = f;
		currFrame.pack();
		currFrame.setLocationRelativeTo(null);
		currFrame.setVisible(true);
	}

	public static void newButton(String text, Container container, JFrame currFrame, float align, int size) {
		JButton txt = new JButton(text);
		txt.setFont(new Font("Times New Roman", Font.PLAIN, size));
		txt.setAlignmentX(align);
		txt.addActionListener(new NavigationListener(currFrame));
		container.add(txt);
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

	public static void setLoc(String newLoc) {
		loc = newLoc;
	}

	public static void setDiff(int newDiff) {
		diff = newDiff;
	}
}