package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

public class NavigationListener implements ActionListener {

	private JFrame currFrame;

	public NavigationListener(JFrame currFrame) {
		this.currFrame = currFrame;
	}

	public void actionPerformed(ActionEvent E) {
		if (E.getSource() instanceof JButton) {
			JButton button = (JButton) E.getSource();
			if (currFrame instanceof MainMenu) {
				switch (button.getText()) {
				case "Easy":
					Main.setDiff(0);
					Main.setLoc("G");
					break;
				case "Medium":
					Main.setDiff(1);
					Main.setLoc("G");
					break;
				case "Hard":
					Main.setDiff(2);
					Main.setLoc("G");
					break;
				case "Instructions":
					Main.setDiff(-1);
					Main.setLoc("I");
					break;
				}
			}
		} else if (E.getSource() instanceof JMenuItem) {
			JMenuItem item = (JMenuItem) E.getSource();
			if (currFrame instanceof Game) {
				switch (item.getText()) {
				case "Return to Main Menu":
					Main.setDiff(-1);
					Main.setLoc("M");
					break;
				case "Exit":
					System.exit(0);
					break;
				}
			}
		}
		Main.updateFrame();
	}
}
