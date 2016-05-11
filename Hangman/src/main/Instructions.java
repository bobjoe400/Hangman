package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Instructions extends JFrame {
	
	public Instructions(){
		super("Instructions");
		setResizable(false);

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("Resources/instructions.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setLayout(new BorderLayout());
		JLabel image = new JLabel(new ImageIcon(img));
		image.setSize(new Dimension(600, 600));
		setContentPane(image);
		setLayout(new BorderLayout());
		setPreferredSize(image.getSize());
		JPanel words = new JPanel();
	}
}
