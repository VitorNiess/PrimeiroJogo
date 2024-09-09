package com.nn.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;

import com.nn.main.Game;
import com.nn.world.Camera;
import com.nn.world.World;

public class Entity {
	
	public int x;
	public int y;
	protected int width;
	protected int height;
	public int depth;
	
	public static BufferedImage LIFEPOTION_EN = Game.spritesheet.getSprite(80, 32, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage GARLIC_EN1 = Game.spritesheet.getSprite(96, 96, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage GARLIC_EN2 = Game.spritesheet.getSprite(112, 96, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage GARLIC_EN3 = Game.spritesheet.getSprite(128, 96, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage GARLICDMG_EN1 = Game.spritesheet.getSprite(144, 96, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage GARLICDMG_EN2 = Game.spritesheet.getSprite(0, 112, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage GARLICDMG_EN3 = Game.spritesheet.getSprite(16, 112, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage QUEENSPIDER_EN1 = Game.spritesheet.getSprite(16, 32, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage QUEENSPIDER_EN2 = Game.spritesheet.getSprite(32, 32, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage QUEENSPIDERDMG_EN1 = Game.spritesheet.getSprite(48, 32, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage QUEENSPIDERDMG_EN2 = Game.spritesheet.getSprite(64, 32, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIRESPELL_EN1 = Game.spritesheet.getSprite(96, 32, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIRESPELL_EN2 = Game.spritesheet.getSprite(128, 32, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIRESPELL_EN3 = Game.spritesheet.getSprite(112, 32, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGSPELL_EN1 = Game.spritesheet.getSprite(64, 64, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGSPELL_EN2 = Game.spritesheet.getSprite(80, 64, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGSPELL_EN3 = Game.spritesheet.getSprite(96, 64, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage ICESPELL_EN1 = Game.spritesheet.getSprite(112, 64, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage ICESPELL_EN2 = Game.spritesheet.getSprite(128, 64, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage ICESPELL_EN3 = Game.spritesheet.getSprite(144, 64, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage STAFF_EN = Game.spritesheet.getSprite(144, 32, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage STAFF_LEFT = Game.spritesheet.getSprite(0, 48, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage STAFF_RIGHT = Game.spritesheet.getSprite(16, 48, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage STAFF_RIGHTDMG = Game.spritesheet.getSprite(48, 48, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage STAFF_LEFTDMG = Game.spritesheet.getSprite(32, 48, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIREBALLUP_EN1 = Game.spritesheet.getSprite(64, 48, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIREBALLUP_EN2 = Game.spritesheet.getSprite(80, 48, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIREBALLDOWN_EN1 = Game.spritesheet.getSprite(96, 48, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIREBALLDOWN_EN2 = Game.spritesheet.getSprite(112, 48, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIREBALLRIGHT_EN1 = Game.spritesheet.getSprite(0, 64, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIREBALLRIGHT_EN2 = Game.spritesheet.getSprite(16, 64, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIREBALLLEFT_EN1 = Game.spritesheet.getSprite(128, 48, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIREBALLLEFT_EN2 = Game.spritesheet.getSprite(144, 48, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGUP_EN1 = Game.spritesheet.getSprite(64, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGUP_EN2 = Game.spritesheet.getSprite(80, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGDOWN_EN1 = Game.spritesheet.getSprite(96, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGDOWN_EN2 = Game.spritesheet.getSprite(112, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGRIGHT_EN1 = Game.spritesheet.getSprite(0, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGRIGHT_EN2 = Game.spritesheet.getSprite(16, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGLEFT_EN1 = Game.spritesheet.getSprite(32, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGLEFT_EN2 = Game.spritesheet.getSprite(48, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage ICEUP_EN = Game.spritesheet.getSprite(0, 96, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage ICEDOWN_EN = Game.spritesheet.getSprite(0, 96, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage ICERIGHT_EN = Game.spritesheet.getSprite(128, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage ICELEFT_EN = Game.spritesheet.getSprite(144, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIREBALEXPLOSION_EN1 = Game.spritesheet.getSprite(32, 64, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage FIREBALEXPLOSION_EN2 = Game.spritesheet.getSprite(48, 64, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage ICEEXPLOSION_EN1 = Game.spritesheet.getSprite(32, 96, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage ICEEXPLOSION_EN2 = Game.spritesheet.getSprite(48, 96, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGEXPLOSION_EN1 = Game.spritesheet.getSprite(64, 96, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIGHTINGEXPLOSION_EN2 = Game.spritesheet.getSprite(80, 96, World.TILE_SIZE, World.TILE_SIZE);
	
	private BufferedImage sprite;
	
	private int maskx, masky, maskwidth, maskheight;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.maskwidth = width;
		this.maskheight = height;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0, Entity n1) {
			
			if(n1.depth < n0.depth) {
				return +1;
			}
			
			if(n1.depth > n0.depth) {
				return -1;
			}
			
			return 0;
		}
		
	};
	
	public void setMask(int maskx, int masky, int maskwidth, int maskheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.maskwidth = maskwidth;
		this.maskheight = maskheight;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	public void tick() {
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.maskwidth, e1.maskheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.maskwidth, e2.maskheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
	}
	
}
