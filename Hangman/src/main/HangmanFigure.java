package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class HangmanFigure extends JPanel {
	// Sup
	private int guesses;
	private String[] imagenames = {"Resources/Man/left.jpg", "Resources/Man/top.jpg", "Resources/Man/rope.png", "Resources/Man/bot.jpg", "Resources/Man/body.png", "Resources/Man/leftarm.png", "Resources/Man/rightarm.png", "Resources/Man/leftleg.png", "Resources/Man/rightleg.png", "Resources/Man/head.png"};
	private BufferedImage[] images = new BufferedImage[imagenames.length];

	public HangmanFigure() {
		super();
		guesses = 0;
		int i = 0;
		try {
			for (String name : imagenames) {

				BufferedImage image = ImageIO.read(new File(name));
				images[i] = image;
				i++;
			}
		} catch (Exception E) {
			E.printStackTrace();

		}
		setPreferredSize(new Dimension(600, 600));
		setOpaque(true);
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);

		// base
		if (guesses > 0) {
			//g.drawLine(1, 299, 299, 299);
			g.drawImage(images[3], 35, 432, null);

		}

		// right wall
		if (guesses > 1) {
			//g.drawLine(299, 299, 299, 1);
			g.drawImage(images[0], 50, 50, null);

		}

		// top line
		if (guesses > 2) {
			//g.drawLine(150, 1, 299, 1);
			g.drawImage(images[1], 117, 50, null);

		}

		// hanging line
		if (guesses > 3) {
			//g.drawLine(150, 1, 150, 70);
			g.drawImage(images[2], 240, 79, null);

		}

		// face
		if (guesses > 4) {
			// g.drawOval(150 - 25, 70, 50, 50);
			g.drawImage(images[9], 150, 110, null);

		}

		// body
		if (guesses > 5) {
			//g.drawLine(150, 120, 150, 200);
			g.drawImage(images[4], 156, 112, null);

		}

		// left hand
		if (guesses > 6) {
			//g.drawLine(150, 150, 110, 140);
			g.drawImage(images[5], 156, 112, null);

		}

		// right hand
		if (guesses > 7) {
			//g.drawLine(150, 150, 190, 140);
			g.drawImage(images[6], 156, 112, null);

		}

		// left leg
		if (guesses > 8) {
			// g.drawLine(150, 200, 120, 250);
			g.drawImage(images[7], 156, 112, null);

		}

		// right leg
		if (guesses > 9) {
			//g.drawLine(150, 200, 180, 250);
			g.drawImage(images[8], 156, 112, null);

		}
	}

	public void set() {
		guesses++;
		paintComponent(getGraphics());
	}

}