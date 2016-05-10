package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JFrame {

	private int diff;

	public MainMenu() {
		super("Main Menu");

		setResizable(false);

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("Resources/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setLayout(new BorderLayout());
		JLabel image = new JLabel(new ImageIcon(img));
		image.setSize(new Dimension(600, 600));
		setContentPane(image);
		setLayout(new BorderLayout());
		setPreferredSize(image.getSize());

		JPanel p1 = new JPanel();
		p1.setOpaque(false);
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.setOpaque(false);
		Main.newButton("Instructions", buttons, this, Component.CENTER_ALIGNMENT, 32);

		JPanel diffbuttons = new JPanel();
		diffbuttons.setOpaque(false);
		diffbuttons.setLayout(new BoxLayout(diffbuttons, BoxLayout.X_AXIS));
		diffbuttons.add(Box.createHorizontalGlue());
		Main.newButton("Easy", diffbuttons, this, Component.CENTER_ALIGNMENT, 32);
		diffbuttons.add(Box.createHorizontalGlue());
		Main.newButton("Medium", diffbuttons, this, Component.CENTER_ALIGNMENT, 32);
		diffbuttons.add(Box.createHorizontalGlue());
		Main.newButton("Hard", diffbuttons, this, Component.CENTER_ALIGNMENT, 32);
		diffbuttons.add(Box.createHorizontalGlue());
		buttons.add(Box.createVerticalGlue());
		buttons.add(diffbuttons, this);

		JPanel instruc = new JPanel();
		instruc.setOpaque(false);
		instruc.setLayout(new BoxLayout(instruc, BoxLayout.PAGE_AXIS));
		instruc.setAlignmentY(BoxLayout.LINE_AXIS);
		Main.newButton("Instructions", instruc, this, Component.CENTER_ALIGNMENT, 32);

		JLabel title = new JLabel("Welcome to Hangman");
		title.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		p1.add(Box.createVerticalGlue());
		p1.add(title);
		p1.add(Box.createVerticalGlue());
		p1.add(buttons);
		p1.add(Box.createVerticalGlue());

		add(p1);
	}

	public void newImage(Image image, Container container) {
		JLabel img = new JLabel(new ImageIcon(image));
		container.add(img);
	}
}