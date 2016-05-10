package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Game extends JFrame {

	private int remainingGuesses;
	private int diff;
	private String wrongGuesses;
	private String word;
	private String visible;

	public Game(int difficulty) {
		super("Hangman");

		diff = difficulty;

		ArrayList<JMenuItem> fileItems = new ArrayList<JMenuItem>();
		fileItems.add(new JMenuItem("Return to Main Menu"));
		fileItems.add(new JMenuItem("Exit"));
		Main.newMenu(fileItems, this);

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

		image = new JLabel(
				new ImageIcon(img.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH)));
		image.setSize(new Dimension(600, 600));
		setContentPane(image);

		setLayout(new BorderLayout());
		setPreferredSize(image.getSize());

		JPanel corePanel = new JPanel();
		corePanel.setLayout(new BorderLayout());
		corePanel.setOpaque(false);

		final JLabel status = new JLabel("You have " + remainingGuesses + " remaining", SwingConstants.CENTER);
		final JLabel wrong = new JLabel("Wrong guesses so far: " + wrongGuesses);
		final JLabel visibleLabel = new JLabel(visible, SwingConstants.CENTER);
		final JLabel diffLabel = new JLabel("Difficulty: " + diffString(), SwingConstants.RIGHT);
		final JTextField input = new JTextField();

		JPanel southPanel = new JPanel(new GridLayout(5, 1));
		southPanel.add(status);
		southPanel.add(visibleLabel);
		southPanel.add(input);
		southPanel.add(wrong);
		southPanel.add(diffLabel);

		corePanel.add(southPanel, BorderLayout.SOUTH);

		final HangmanFigure hf = new HangmanFigure();
		corePanel.add(hf, BorderLayout.CENTER);

		add(corePanel, BorderLayout.CENTER);

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

	private String generateWord() {
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
				word = words.get(Math.abs(new Random().ints(4500, 500, 5000).findAny().getAsInt()));
				break;
			case 2:
				word = words.get(Math.abs(new Random().ints(5000, 5000, 10000).findAny().getAsInt()));
				break;
			default:
				System.out.println("Error: The difficulty is not one of the three. This shouldn't happen.");
				break;
			}
			br.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return word;
	}

	private String diffString() {
		switch (diff) {
		case 0:
			return "Easy";
		case 1:
			return "Medium";
		case 2:
			return "Hard";
		default:
			return "Error calculating difficulty";
		}
	}
}
