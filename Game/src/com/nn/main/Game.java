package com.nn.main;

import com.nn.entities.*;
import com.nn.graphics.*;
import com.nn.world.World;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 320;
	public static final int HEIGHT = 160;
	public static final int scale = 4;
	
	public static int CUR_MAP = 1;
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<GarlicEnemy> garlicenemies;
	public static List<StandardFireSpellCast> sfirespell;
	public static List<StandardLightingSpellCast> slightingspell;
	public static List<StandardIceSpellCast> sicespell;
	public static Spritesheet spritesheet;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public UI ui;
	public Menu menu;
	
	public static String gameState = "MENU";
	private boolean restartGame = false, saveGame = false;
	
	public int[] pixels;
	public BufferedImage lightmap;
	public int[] lightMapPixels;
	
	public Game() {
		Sound.menuMusic.loop();
		rand = new Random();
		addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH * scale, HEIGHT * scale));
		initFrame();
		
		ui = new UI();
		menu = new Menu();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		try {
			lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0, lightmap.getWidth());
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		garlicenemies = new ArrayList<GarlicEnemy>();
		sfirespell = new ArrayList<StandardFireSpellCast>();
		slightingspell = new ArrayList<StandardLightingSpellCast>();
		sicespell = new ArrayList<StandardIceSpellCast>();
		
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(48, 0, 16, 16));
		entities.add(player);
		world = new World("/map1.png");
	}
	
	public void initFrame() {
		frame = new JFrame("Game");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this); 
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		
		if(gameState == "GAME") {
			Sound.gameMusic.loop();
			
			if(this.saveGame) {
				
				enemies.clear();
				garlicenemies.clear();
				
				this.saveGame = false;
				String[] opt1 = {"map", "life", "fire", "ice", "lighting", "hasStaff", "playerPosX", "playerPosY", "spiders", "garlics"};
				int[] opt2 = {CUR_MAP, (int)(player.lifeAtual), Player.fireSpellCasting, Player.iceSpellCasting, 
						Player.lightingSpellCasting, (int)(Player.hasStaff), player.getX(), player.getY(), enemies.size(), garlicenemies.size()};
				
				Menu.saveGame(opt1, opt2, 47);
			}
			
			this.restartGame = false;
			
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				
				e.tick();
			}
			
			for(int i = 0; i < sfirespell.size(); i++) {
				sfirespell.get(i).tick();
			}
			
			for(int i = 0; i < slightingspell.size(); i++) {
				slightingspell.get(i).tick();
			}
			
			for(int i = 0; i < sicespell.size(); i++) {
				sicespell.get(i).tick();
			}
			
			//Map changing
			
			if(CUR_MAP == 1) {
				
				if(player.getX() == 284 && player.getY() == 16) {
					
					if(CUR_MAP != 2) {
						CUR_MAP = 2;
						
						Sound.deathEffect.play();
						String newWorld = "map" + CUR_MAP + ".png"; 
						World.nextLevel(newWorld);
					}
					
				}
				
			}
			
			if(CUR_MAP == 2) {
				
				if(player.getX() == 158 && player.getY() == 16) {
					
					if(CUR_MAP != 3) {
						CUR_MAP = 3;

						Sound.deathEffect.play();
						String newWorld = "map" + CUR_MAP + ".png"; 
						World.nextLevel(newWorld);
					}
					
				}
				
			}

			
		} else if(gameState == "GAME_OVER") {
			
			if(restartGame) {
				this.restartGame = false;
				gameState = "GAME";
				CUR_MAP = 1;
				String newWorld = "map" + CUR_MAP + ".png"; 
				World.restartGame(newWorld);
			}
			
		} else if(gameState == "MENU") {
			menu.tick();
		}
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		
		Collections.sort(entities, Entity.nodeSorter);
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		for(int i = 0; i < sfirespell.size(); i++) {
			sfirespell.get(i).render(g);
		}
		
		for(int i = 0; i < slightingspell.size(); i++) {
			slightingspell.get(i).render(g);
		}
		
		for(int i = 0; i < sicespell.size(); i++) {
			sicespell.get(i).render(g);
		}
		
		ui.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * scale, HEIGHT * scale, null);
		
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * scale, HEIGHT * scale);
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 28));
			g.drawString("Game Over!", 550, 300);
			g.setFont(new Font("arial", Font.BOLD, 28));
			g.drawString("Pressione Enter para continuar!", 425, 400);
		} else if(gameState == "MENU") {
			Sound.menuMusic.play();
			menu.render(g);
		}
		
		bs.show();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
		Sound.menuMusic.loop();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		requestFocus();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
			
		}
		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			
			if(gameState == "MENU") {
				menu.down = true;
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			
			if(gameState == "MENU") {
				menu.up = true;
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_E) {
			player.fireballcasted = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F) {
			player.lightingcasted = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X) {
			player.icecasted = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			restartGame = true;
			
			if(gameState == "MENU") {
				menu.enter = true;
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "MENU";
			Menu.pause = true;
			Sound.selectOption.play();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_L) {
			this.saveGame = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		
	}

}