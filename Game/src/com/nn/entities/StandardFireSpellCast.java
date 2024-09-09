package com.nn.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nn.main.Game;
import com.nn.world.Camera;
import com.nn.world.World;

public class StandardFireSpellCast extends Entity{

	private int dx;
	private int dy;
	private double speed = 1;
	public static double damage = 8, dmgPS = 1, burnTime = 2;
	private int maxLife = 60, lifeAtual = 0, limit = 52;
	private int lastPosition = 4;
	private boolean destroy = false, collided = false;
	public final int right_dir = 2, left_dir = 3, up_dir = 1, down_dir = 0;
	
	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 1;
	
	private BufferedImage[] spritesFireball_Up;
	private BufferedImage[] spritesFireball_Down;
	private BufferedImage[] spritesFireball_Right;
	private BufferedImage[] spritesFireball_Left;
	private BufferedImage[] spritesFireball_Explosion;
	
	public StandardFireSpellCast(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		
		spritesFireball_Up = new BufferedImage[2];
		spritesFireball_Down = new BufferedImage[2];
		spritesFireball_Right = new BufferedImage[2];
		spritesFireball_Left = new BufferedImage[2];
		spritesFireball_Explosion = new BufferedImage[2];
		
		spritesFireball_Up[0] = Entity.FIREBALLUP_EN1;
		spritesFireball_Up[1] = Entity.FIREBALLUP_EN2;
		spritesFireball_Down[0] = Entity.FIREBALLDOWN_EN1;
		spritesFireball_Down[1] = Entity.FIREBALLDOWN_EN2;
		spritesFireball_Right[0] = Entity.FIREBALLRIGHT_EN1;
		spritesFireball_Right[1] = Entity.FIREBALLRIGHT_EN2;
		spritesFireball_Left[0] = Entity.FIREBALLLEFT_EN1;
		spritesFireball_Left[1] = Entity.FIREBALLLEFT_EN2;
		spritesFireball_Explosion[0] = Entity.FIREBALEXPLOSION_EN2;
		spritesFireball_Explosion[1] = Entity.FIREBALEXPLOSION_EN1;
		
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
			g.drawImage(spritesFireball_Explosion[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			
			if(lastPosition == Game.player.up_dir) {
				g.drawImage(spritesFireball_Up[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(lastPosition == Game.player.down_dir) {
				g.drawImage(spritesFireball_Down[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(lastPosition == Game.player.right_dir) {
				g.drawImage(spritesFireball_Right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(lastPosition == Game.player.left_dir) {
				g.drawImage(spritesFireball_Left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			
		}
		
	}
	
	public void destroySelf() {
		Game.sfirespell.remove(this);
	}

}
