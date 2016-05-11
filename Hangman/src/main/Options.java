package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Options extends JFrame	{
	public Options(){
		super("Options");
		setResizable(false);

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("Resources/options.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setLayout(new BorderLayout());
		JLabel image = new JLabel(new ImageIcon(img));
		image.setSize(new Dimension(600, 600));
		setContentPane(image);
		setLayout(new BorderLayout());
		
		JPanel buttons = new JPanel();
		buttons.setOpaque(false);
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.add(Box.createVerticalGlue());
		Main.newButton("Change Song", buttons, this, Component.CENTER_ALIGNMENT, 32);
		buttons.add(Box.createVerticalGlue());
		Main.newButton("Return to Main Menu", buttons, this, Component.CENTER_ALIGNMENT, 32);
		buttons.add(Box.createVerticalGlue());
		add(buttons);
	}
	public void updateBar(){
		
	}
}
