package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
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
	private boolean inGame;
	private final Integer bottom = JLayeredPane.DEFAULT_LAYER;

	public MainMenu() {
		super("Main Menu");
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
		setJMenuBar(menuBar);
		
		setResizable(false);
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("Resources/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setLayout(new BorderLayout());
		JLabel image = new JLabel(new ImageIcon(img));
		image.setPreferredSize(new Dimension(600,600));
		setContentPane(image);
		setLayout(new FlowLayout());
		JLayeredPane p1 = new JLayeredPane();
		p1.setPreferredSize(new Dimension(600,600));
		JPanel buttons = new JPanel();
		buttons.setOpaque(false);
		buttons.setSize(p1.getPreferredSize());
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
		buttons.add(Box.createRigidArea(new Dimension(40,0)));
		newButton("Easy",buttons);
		buttons.add(Box.createHorizontalGlue());
		newButton("Medium",buttons);
		buttons.add(Box.createHorizontalGlue());
		newButton("Hard",buttons);
		buttons.add(Box.createRigidArea(new Dimension(40,0)));
		buttons.add(Box.createRigidArea(new Dimension(0,15)));
		p1.add(buttons, 1);
		JLabel title = new JLabel("Welcome to Hangman");
		title.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		FontMetrics fm = title.getFontMetrics(title.getFont());
		System.out.println(fm);
		System.out.println(fm.stringWidth(title.getText()));
		System.out.println(p1.getPreferredSize());
		title.setBounds((int) (p1.getPreferredSize().getWidth()/2 - fm.stringWidth(title.getText())), 32, fm.stringWidth(title.getText()),40);
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setAlignmentY(TOP_ALIGNMENT);
		p1.add(title, 0);
		add(p1);
	}

	public void newImage(Image image, Container container){
		JLabel img = new JLabel(new ImageIcon(image));
		container.add(img);
	}
	
	public void newButton(String text, Container container){
		
		JButton txt = new JButton(text);
		txt.setFont(new Font("Times New Roman",Font.PLAIN,32));
		txt.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txt.addActionListener(this);
		container.add(txt);
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
		if (E.getSource() instanceof JButton){
			JButton button = (JButton) E.getSource();
			switch(button.getText()){
			case "Easy":
				diff = 0;
				break;
			case "Medium":
				diff = 1;
				break;
			case "Hard":
				diff= 2;
				break;
			}
			inGame = true;
		}
		Main.checkVisible(this, diff, inGame);
	}
	
	public class ImagePanel extends JPanel
	{
	    private int width,height;
	    private Image image;

	    public ImagePanel(Image image) 
	    {
	          this.image = image;

	          //so we can set the JPanel preferred size to the image width and height
	          ImageIcon ii = new ImageIcon(this.image);
	          width = ii.getIconWidth();
	          height = ii.getIconHeight();
	     }

	     //so our panel is the same size as image
	     @Override
	     public Dimension getPreferredSize() {
	          return new Dimension(width, height);
	     }

	     @Override
	     protected void paintComponent(Graphics g) 
	     {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, null);
	     }

	}
}