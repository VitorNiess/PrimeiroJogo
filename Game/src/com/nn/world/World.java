package com.nn.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.nn.entities.*;
import com.nn.graphics.Spritesheet;
import com.nn.main.Game;

public class World {
	
	private static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

	public World(String path) {
		
		try {
			
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx = 0; xx < map.getWidth(); xx++) {
				
				for(int yy = 0; yy < map.getHeight(); yy++) {
					
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					
					if(Game.CUR_MAP == 1 || Game.CUR_MAP == 3) {
						
						if(Game.rand.nextInt(101) > 50){
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_GRASS1);
						} else {
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_GRASS2);
						}
						
					} else if(Game.CUR_MAP == 2) {
						
						if(Game.rand.nextInt(101) > 50){
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_CAVEGROUND1);
						} else {
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_CAVEGROUND2);
						}
						
					}
					
					
					if(pixelAtual == 0xFFFF0000) {
						
						//Grass Floor
						
						if(Game.rand.nextInt(101) > 50){
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_GRASS1);
						} else {
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_GRASS2);
						}
						
					} else if(pixelAtual == 0xFFFFC2D9) {
						
						//Dirt Path 1
						
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_DIRT1);
						
					} else if(pixelAtual == 0xFF000088) {
						
						//Dirt Path 2
						
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_DIRT2);
						
					}else if(pixelAtual == 0xFF6D5F66) {
						
						//Dirt Path 3
						
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_DIRT3);
						
					} else if(pixelAtual == 0xFF3F52FF) {
						
						//Cave path
						
						if(Game.rand.nextInt(101) > 50){
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_CAVEGROUND1);
						} else {
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_CAVEGROUND2);
						}
						
					}else if(pixelAtual == 0xFF82FFF4) {
						
						//Cave wall
						
						if(Game.rand.nextInt(101) > 50){
							tiles[xx + (yy * WIDTH)] = new WallTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_CAVEWALL1);
						} else {
							tiles[xx + (yy * WIDTH)] = new WallTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_CAVEWALL2);
						}
						
					} else if(pixelAtual == 0xFFFFFFFF) {
						
						//Stone Bricks
						
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_BRICKWALL1);
						
					} else if(pixelAtual == 0xFFFFD800) {
						
						//Player
						
						Game.player.setX(xx * TILE_SIZE);
						Game.player.setY(yy * TILE_SIZE);
						
					} else if(pixelAtual == 0xFF593B58) {
						
						//Staff
						
						Game.entities.add(new Staff(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.STAFF_EN));
						
					} else if(pixelAtual == 0xFF6B77FF) {
						
						//Queen Spider
						
						Enemy queen_spider = new Enemy(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
						Game.entities.add(queen_spider);
						Game.enemies.add(queen_spider);
						
					} else if(pixelAtual == 0xFF4CFF00) {
						
						//StandardFireSpell
						
						Game.entities.add(new StandardFireSpell(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
						
					} else if(pixelAtual == 0xFF9EFF91) {
						
						//Life Potion
						
						Game.entities.add(new LifePotion(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.LIFEPOTION_EN));
						
					} else if(pixelAtual == 0xFFF9B548) {
						
						//StandardLightingSpell
						
						Game.entities.add(new StandardLightingSpell(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
						
					} else if(pixelAtual == 0xFF1BB9E5) {
							
						//StandardIceSpell
							
						Game.entities.add(new StandardIceSpell(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null));
						
					} else if(pixelAtual == 0xFF22E500) {
						
						//Garlic
						
						GarlicEnemy garlic = new GarlicEnemy(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
						Game.entities.add(garlic);
						Game.garlicenemies.add(garlic);
					
					}
					
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + (TILE_SIZE - 1)) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+ (TILE_SIZE - 1)) / TILE_SIZE;
		
		int x4 = (xnext + (TILE_SIZE - 1)) / TILE_SIZE;
		int y4 = (ynext + (TILE_SIZE - 1)) / TILE_SIZE;
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}
	
	public static void restartGame(String map) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0, 0, TILE_SIZE, TILE_SIZE, Game.spritesheet.getSprite(48, 0, TILE_SIZE, TILE_SIZE));
		Game.entities.add(Game.player);
		Game.world = new World("/" + map);
		Player.iceSpellCasting = 0;
		Player.fireSpellCasting = 0;
		Player.lightingSpellCasting = 0;
		Player.hasStaff = 0;
		
		return;
	}
	
	public static void nextLevel(String map) {
		Game.enemies.clear();
		Game.garlicenemies.clear();
		Game.entities.clear();
		Game.player = new Player(0, 0, TILE_SIZE, TILE_SIZE, Game.spritesheet.getSprite(48, 0, TILE_SIZE, TILE_SIZE));
		Game.entities.add(Game.player);
		
		Game.world = new World("/" + map);
		
		return;
	}
	
	public void render(Graphics g) {
		
		int xstart = Camera.x / TILE_SIZE;
		int ystart = Camera.y / TILE_SIZE;
		int xfinal = xstart + (Game.WIDTH / TILE_SIZE);
		int yfinal = ystart + (Game.HEIGHT / TILE_SIZE);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			
			for(int yy = ystart; yy <= yfinal; yy++) {
				
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
			
		}
		
	}
	
}
