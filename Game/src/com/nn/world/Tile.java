package com.nn.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nn.main.Game;

public class Tile {

	public static BufferedImage TILE_GRASS1 = Game.spritesheet.getSprite(0, 0, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage TILE_GRASS2 = Game.spritesheet.getSprite(16, 0, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage TILE_DIRT1 = Game.spritesheet.getSprite(32, 112, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage TILE_DIRT2 = Game.spritesheet.getSprite(48, 112, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage TILE_DIRT3 = Game.spritesheet.getSprite(64, 112, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage TILE_BRICKWALL1 = Game.spritesheet.getSprite(32, 0, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage TILE_CAVEGROUND1 = Game.spritesheet.getSprite(80, 112, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage TILE_CAVEGROUND2 = Game.spritesheet.getSprite(96, 112, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage TILE_CAVEWALL1 = Game.spritesheet.getSprite(112, 112, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage TILE_CAVEWALL2 = Game.spritesheet.getSprite(128, 112, World.TILE_SIZE, World.TILE_SIZE);
	
	private BufferedImage sprite;
	private int x, y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y- Camera.y, null);
	}
	
}
