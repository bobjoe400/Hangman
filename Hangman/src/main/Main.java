package main;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.JFrame;

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
		new MainMenu();
	}

	public static void checkVisible(JFrame f, int diff, boolean inGame) {
		if (f instanceof Game && !inGame) {
			f.dispose();
			new MainMenu();
		} else if (f instanceof MainMenu && inGame) {
			f.dispose();
			new Game("cat", diff);
		} else {
			f.setVisible(true);
		}
	}
	
	static class ImagePanel extends JComponent {
	    private Image image;
	    public ImagePanel(Image image) {
	        this.image = image;
	    }
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, this);
	    }
	}
}