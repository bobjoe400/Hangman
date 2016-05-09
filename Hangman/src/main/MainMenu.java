package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainMenu extends JFrame implements ActionListener {

	private ArrayList<JMenuItem> diffs;
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
		newButton("Instructions", buttons);

		JPanel diffbuttons = new JPanel();
		diffbuttons.setOpaque(false);
		diffbuttons.setLayout(new BoxLayout(diffbuttons, BoxLayout.X_AXIS));
		diffbuttons.add(Box.createHorizontalGlue());
		newButton("Easy", diffbuttons);
		diffbuttons.add(Box.createHorizontalGlue());
		newButton("Medium", diffbuttons);
		diffbuttons.add(Box.createHorizontalGlue());
		newButton("Hard", diffbuttons);
		diffbuttons.add(Box.createHorizontalGlue());
		buttons.add(Box.createVerticalGlue());
		buttons.add(diffbuttons);

		JPanel instruc = new JPanel();
		instruc.setOpaque(false);
		instruc.setLayout(new BoxLayout(instruc, BoxLayout.PAGE_AXIS));
		instruc.setAlignmentY(BoxLayout.LINE_AXIS);
		newButton("Instructions", instruc);

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

	public void newButton(String text, Container container) {

		JButton txt = new JButton(text);
		txt.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		txt.setAlignmentX(Component.CENTER_ALIGNMENT);
		txt.addActionListener(this);
		container.add(txt);
	}

	public void actionPerformed(ActionEvent E) {
		if (E.getSource() instanceof JButton) {
			JButton button = (JButton) E.getSource();
			switch (button.getText()) {
			case "Easy":
				diff = 0;
				Main.setLoc("G");
				break;
			case "Medium":
				diff = 1;
				Main.setLoc("G");
				break;
			case "Hard":
				diff = 2;
				Main.setLoc("G");
				break;
			case "Instructions":
				diff = -1;
				Main.setLoc("I");
				break;
			}
		}
		Main.makeFrame(diff);
	}

	public class ImagePanel extends JPanel {
		private int width, height;
		private Image image;

		public ImagePanel(Image image) {
			this.image = image;

			// so we can set the JPanel preferred size to the image width and
			// height
			ImageIcon ii = new ImageIcon(this.image);
			width = ii.getIconWidth();
			height = ii.getIconHeight();
		}

		// so our panel is the same size as image
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(width, height);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
		}

	}
}