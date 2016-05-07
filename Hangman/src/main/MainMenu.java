package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenu extends JFrame implements ActionListener, WindowListener {

	private ArrayList<JMenuItem> diffs;
	private int diff;
	private boolean inGame;

	public MainMenu() {
		this.addWindowListener(this);

		diffs = new ArrayList<JMenuItem>();
		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("Difficulty");
		diffs.add(new JMenuItem("Easy"));
		diffs.add(new JMenuItem("Medium"));
		diffs.add(new JMenuItem("Hard"));
		for (JMenuItem item : diffs) {
			item.addActionListener(this);
			menu1.add(item);
		}
		menuBar.add(menu1);
		this.setJMenuBar(menuBar);
		
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File("Resources/menu.jpg"));
			this.setContentPane(new Main.ImagePanel(img));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			this.setTitle("Main menu");
			this.setSize(img.getWidth(),img.getHeight());
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		}
	}

	public void actionPerformed(ActionEvent E) {
		if (E.getSource() == diffs.get(0)) {
			System.out.println("Easy");
			diff = 0;
			inGame = true;
		}
		if (E.getSource() == diffs.get(1)) {
			System.out.println("Med");
			diff = 1;
			inGame = true;
		}
		if (E.getSource() == diffs.get(2)) {
			System.out.println("Hard");
			diff = 2;
			inGame = true;
		}
		Main.checkVisible(this, diff, inGame);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}