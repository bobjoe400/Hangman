package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame{

	private int remainingGuesses;
	private static int diff;
	private String wrongGuesses;
	private String word;
	private String visible;
	

	static class MusicThread implements Runnable {
		public void run() {
			try {
				FileInputStream fs = new FileInputStream("Resources/music.wav");
				BufferedInputStream buffered = new BufferedInputStream(fs);
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(buffered);
				Clip clip = AudioSystem.getClip();
				clip.open(inputStream);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				while (true) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public MainWindow(String toGuess, int difficulty) {

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

		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while(diff == -1){
			System.out.println(diff);
		}
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Thread music = new Thread(new MusicThread());
		music.start();
		diff = -1;
		new Menu();
		new MainWindow("cat",diff);
	}
	static class Menu extends JFrame implements ActionListener{
		
		private ArrayList<JMenuItem> diffs;
		
		public Menu(){
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
			this.setTitle("Main menu");
			this.setSize(300, 300);
			this.setVisible(true);
		}
		
		public void actionPerformed(ActionEvent E){
			if(E.getSource() == diffs.get(0)){
				System.out.println("Easy");
				diff = 0;
			}
			if(E.getSource() == diffs.get(1)){
				System.out.println("Med");
				diff = 1;
			}
			if(E.getSource() == diffs.get(2)){
				System.out.println("Hard");
				diff = 2;
			}
			checkVisible();
		}
		private void checkVisible(){
			this.setVisible((diff == -1)? true: false);
		}

	}
}