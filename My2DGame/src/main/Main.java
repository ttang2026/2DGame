package main;

import javax.swing.JFrame;

public class Main {
	
	public static void main (String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Treasure Game");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); // window to be sized to fit preferred size and layouts of its subcomponents (=GamePanel)
		
		window.setLocationRelativeTo(null); // window will be displayed at center of the screen
		window.setVisible(true);
		
		gamePanel.startGameThread();
		
		
	}

}
