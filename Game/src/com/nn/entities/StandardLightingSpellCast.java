package com.nn.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nn.main.Game;
import com.nn.world.Camera;
import com.nn.world.World;

public class StandardLightingSpellCast extends Entity{

	private int dx;
	private int dy;
	private double speed = 5;
	public static final int stunTime = 30; 
	public static double damage = 2;
	private int maxLife = 80, lifeAtual = 0, limit = 72;
	private int lastPosition = 4;
	private boolean destroy = false, collided = false;
	public final int right_dir = 2, left_dir = 3, up_dir = 1, down_dir = 0;
	
	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 1;
	
	private BufferedImage[] spritesLighting_Up;
	private BufferedImage[] spritesLighting_Down;
	private BufferedImage[] spritesLighting_Right;
	private BufferedImage[] spritesLighting_Left;
	private BufferedImage[] spritesLighting_Explosion;
	
	public StandardLightingSpellCast(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		
		spritesLighting_Up = new BufferedImage[2];
		spritesLighting_Down = new BufferedImage[2];
		spritesLighting_Right = new BufferedImage[2];
		spritesLighting_Left = new BufferedImage[2];
		spritesLighting_Explosion = new BufferedImage[2];
		
		spritesLighting_Up[0] = Entity.LIGHTINGUP_EN1;
		spritesLighting_Up[1] = Entity.LIGHTINGUP_EN2;
		spritesLighting_Down[0] = Entity.LIGHTINGDOWN_EN1;
		spritesLighting_Down[1] = Entity.LIGHTINGDOWN_EN2;
		spritesLighting_Right[0] = Entity.LIGHTINGRIGHT_EN1;
		spritesLighting_Right[1] = Entity.LIGHTINGRIGHT_EN2;
		spritesLighting_Left[0] = Entity.LIGHTINGLEFT_EN1;
		spritesLighting_Left[1] = Entity.LIGHTINGLEFT_EN2;
		spritesLighting_Explosion[0] = Entity.LIGHTINGEXPLOSION_EN1;
		spritesLighting_Explosion[1] = Entity.LIGHTINGEXPLOSION_EN2;
		
		this.depth = 1;
		
	}

	public void tick() {
		lifeAtual++;
		frames++;
		
		if(World.isFree(this.getX(), this.getY() + (int)(speed))) {
			x += (int)(dx * speed);
		} else {
			
			if(!collided) {
				lifeAtual = limit;
				collided = true;
			}
			
		}
		
		if(World.isFree(this.getX(), this.getY() + (int)(speed))) {
			y += (int)(dy * speed);
		} else {
			
			if(!collided) {
				lifeAtual = limit;
				collided = true;
			}
			
		}
		
		if(frames == maxFrames) {
			frames = 0;
			index++;
			
			if(index > maxIndex) {
				index = 0;
			}
			
		}
		
		if(lifeAtual == limit) {
			destroy = true;
		}
		
		if(lifeAtual == maxLife) {
			destroySelf();
			return;
		}
		
	}
	
	public void render(Graphics g) {
		if(Game.player.dir == Game.player.up_dir || lastPosition == Game.player.up_dir) {

			if(lastPosition == 4 || lastPosition == up_dir) {
				lastPosition = up_dir;
			}
			
		} else if(Game.player.dir == Game.player.down_dir || lastPosition == Game.player.down_dir) {
			
			if(lastPosition == 4 || lastPosition == down_dir) {
				lastPosition = down_dir;
			}
			
		} else if(Game.player.dir == Game.player.right_dir || lastPosition == Game.player.right_dir) {
			
			if(lastPosition == 4 || lastPosition == right_dir) {
				lastPosition = right_dir;
			}
			
		} else if(Game.player.dir == Game.player.left_dir || lastPosition == Game.player.left_dir) {
			
			if(lastPosition == 4 || lastPosition == left_dir) {
				lastPosition = left_dir;
			}
			
		}
		
		if(lifeAtual == maxLife) {
			lastPosition = 4;
		}
		
		if(destroy) {
			g.drawImage(spritesLighting_Explosion[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			
			if(lastPosition == Game.player.up_dir) {
				g.drawImage(spritesLighting_Up[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(lastPosition == Game.player.down_dir) {
				g.drawImage(spritesLighting_Down[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(lastPosition == Game.player.right_dir) {
				g.drawImage(spritesLighting_Right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(lastPosition == Game.player.left_dir) {
				g.drawImage(spritesLighting_Left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			
		}
		
	}
	
	public void destroySelf() {
		Game.slightingspell.remove(this);
	}

}
