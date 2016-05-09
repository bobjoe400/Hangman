package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Game extends JFrame implements ActionListener {

	private ArrayList<JMenuItem> fileItems;

	private int remainingGuesses;
	private int diff;
	private String wrongGuesses;
	private String word;
	private String visible;

	public Game(int difficulty) {
		super("Hangman");
		
		diff = difficulty;

		fileItems = new ArrayList<JMenuItem>();

		fileItems.add(new JMenuItem("Return to main menu"));
		fileItems.add(new JMenuItem("Exit"));

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		for (JMenuItem item : fileItems) {
			item.addActionListener(this);
			fileMenu.add(item);
		}
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		
		remainingGuesses = 10;
		wrongGuesses = "";
		word = generateWord();

		visible = "";

		for (int i = 0; i < word.length(); ++i) {
			visible += "_ ";
		}
		
		setLayout(new BorderLayout());
		
		JLabel image = new JLabel();
		image.setSize(new Dimension(600, 600));
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("Resources/game.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		image = new JLabel(new ImageIcon(img.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH)));
		image.setSize(new Dimension(600,600));
		setContentPane(image);
		
		setLayout(new BorderLayout());
		setPreferredSize(image.getSize());
		
		JPanel corePanel = new JPanel();
		corePanel.setLayout(new BorderLayout());
		corePanel.setOpaque(false);

		final JLabel status = new JLabel("You have " + remainingGuesses + " remaining", SwingConstants.CENTER);
		final JLabel wrong = new JLabel("Wrong guesses so far: " + wrongGuesses);
		final JLabel visibleLabel = new JLabel(visible, SwingConstants.CENTER);
		final JTextField input = new JTextField();

		JPanel southPanel = new JPanel(new GridLayout(4, 1));
		southPanel.add(status);
		southPanel.add(visibleLabel);
		southPanel.add(input);
		southPanel.add(wrong);

		corePanel.add(southPanel, BorderLayout.SOUTH);

		final HangmanFigure hf = new HangmanFigure();
		corePanel.add(hf, BorderLayout.CENTER);

		this.add(corePanel, BorderLayout.CENTER);

		input.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = input.getText();

				if (text.length() == 1 && text.matches("[a-z]")) {

					boolean guessFound = false;

					for (int i = 0; i < word.length(); ++i) {
						if (text.charAt(0) == word.charAt(i)) {
							guessFound = true;

							String newVisible = "";
							for (int j = 0; j < visible.length(); ++j) {
								if (j == (i * 2)) {
									newVisible += word.charAt(i);
								} else {
									newVisible += visible.charAt(j);
								}
							}
							visible = newVisible;
							visibleLabel.setText(visible);
						}
					}

					if (!guessFound) {
						if (--remainingGuesses >= 0) {
							status.setText("You have " + remainingGuesses + " guesses remaining");
							wrongGuesses += text + " ";
							wrong.setText("Wrong guesses so far: " + wrongGuesses);
							hf.set();
						} else {
							status.setText("You lost: the word was " + word);
							input.setEnabled(false);
						}
					} else {
						String actualVisible = "";
						for (int i = 0; i < visible.length(); i += 2) {
							actualVisible += visible.charAt(i);
						}

						if (actualVisible.equals(word)) {
							status.setText("Congratulations, you have won!");
							input.setEnabled(false);
						}
					}

				} else {
					System.out.println("Invalid input!");
				}

				input.setText("");
			}

		});
	}

	public String generateWord() {
		ArrayList<String> words = new ArrayList<String>();
		String word = "";
		try {
			FileInputStream stream = new FileInputStream(new File("Resources/dictionary.txt"));
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = br.readLine()) != null) {
				words.add(line);
			}
			switch (diff) {
			case 0:
				word = words.get(Math.abs(new Random().nextInt(500)));
				break;
			case 1:
				word = words.get(Math.abs(new Random().ints(4500,500,5000).findAny().getAsInt()));
				break;
			case 2:
				word = words.get(Math.abs(new Random().ints(5000,5000,10000).findAny().getAsInt()));
				break;
			}
			br.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return word;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fileItems.get(0)) {
			Main.setLoc("M");
		}
		if (e.getSource() == fileItems.get(1)) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		Main.makeFrame(diff);
	}

}
