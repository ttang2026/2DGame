package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D; // extends the Graphics class for more control over geometry

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{ // class inherits JPanel class
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 769 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	//FPS
	int FPS = 60;
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	// Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension (screenWidth, screenHeight)); // sets the size of the JPanel class
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // enabling improves game rendering performance
		this.addKeyListener(keyH);
		this.setFocusable(true); // GamePanel can be "focused" to receive key input
	}
	
	public void setupGame () {
		
		System.out.println("Welcome to the 2D Treasure Game!");
		System.out.println("You are an adventurous young boy who just stumbled upon locked treasure in the pathway to your left,");
		System.out.println("your objective is to find the keys to open the doors to the locked treasure!!");
		System.out.println("This is no easy task... as you might need an ITEM to speed up finding the keys...");
		System.out.println("To move your adventurous character, you need to press the W, A, S, D keys.");
		System.out.println("Good luck! And try to get the fastest time possible!");
		
		aSetter.setObject(); // creating this method to add more setup stuff in the future
		
		playMusic(0);
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}


	@Override
//	public void run() {
//		
//		double drawInterval = 1000000000/FPS; // 0.01666 seconds
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		while (gameThread != null) {
//			 
//			update();
//
//			repaint();
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime/1000000;
//		
//				if(remainingTime < 0) {
//				remainingTime = 0;
//			}
//			
//			
//			
//			Thread.sleep((long)remainingTime);
//			
//			nextDrawTime += drawInterval;
//			
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
//		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
//				drawCount++;
			}
			
			if(timer >= 1000000000) {
//				System.out.println("FPS:" + drawCount);
//				drawCount = 0;
				timer = 0;
			}
		}
	}
	public void update() {
		
		player.update();
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g; 
		
		// TILE
		tileM.draw(g2);
		
		// OBJECT
		for (int i = 0; i < obj.length; i++) {
			if (obj [i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		// Player
		player.draw(g2);
		
		// UI
		ui.draw(g2);
		
		g2.dispose(); // dispose graphics context and release any system resources that it's using
		
	}
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		
		music.stop();
	}
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
	}
	
}
	