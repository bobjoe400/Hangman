package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

public class NavigationListener implements ActionListener {

	private JFrame currFrame;
	private boolean isPlaying;

	public NavigationListener(JFrame currFrame) {
		isPlaying = Main.musicStatus();
		this.currFrame = currFrame;
	}

	public void actionPerformed(ActionEvent E) {
		if (E.getSource() instanceof JButton) {
			JButton button = (JButton) E.getSource();
			if(button.getText().contains("Mute")){
				if(isPlaying){
					Main.setMusic(true, true);
				}else{
					Main.setMusic(true, false);
				}
				isPlaying = !isPlaying;
			}
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
				case "Options":
					Main.setDiff(-1);
					Main.setLoc("O");
					break;
				}
			}else if(currFrame instanceof Options){
				switch(button.getText()){
				case "Change Song":
					Main.setMusic(false, false);
					break;
				case "Return to Main Menu":
					Main.setDiff(-1);
					Main.setLoc("M");
					break;
				}
			}else if(currFrame instanceof Game){
				switch(button.getText()){
				case "New Game":
					Main.newGame();
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
		Main.updateBar();
		Main.updateFrame();
	}
}
