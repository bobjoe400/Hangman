package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Game extends JFrame implements ActionListener, WindowListener {

	private ArrayList<JMenuItem> fileItems;

	private int remainingGuesses;
	private int diff;
	private boolean inGame;
	private String wrongGuesses;
	private String word;
	private String visible;

	public Game(String toGuess, int difficulty) {

		this.addWindowListener(this);

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

		remainingGuesses = 10;
		wrongGuesses = "";
		word = toGuess;

		visible = "";

		for (int i = 0; i < word.length(); ++i) {
			visible += "_ ";
		}

		JPanel corePanel = new JPanel();
		corePanel.setLayout(new BorderLayout());

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
		this.setJMenuBar(menuBar);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fileItems.get(0)) {
			inGame = false;
		}
		if (e.getSource() == fileItems.get(1)) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		Main.checkVisible(this, diff, inGame);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
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
