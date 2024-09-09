package com.nn.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nn.main.Game;
import com.nn.world.Camera;
import com.nn.world.World;

public class StandardIceSpellCast extends Entity{

	private int dx;
	private int dy;
	private double speed = 2;
	public static final int slowTime = 90;
	public static final double slowRate = 2;
	public static double damage = 4;
	private int maxLife = 60, lifeAtual = 0, limit = 52;
	private int lastPosition = 4;
	private boolean destroy = false, collided = false;
	public final int right_dir = 2, left_dir = 3, up_dir = 1, down_dir = 0;
	
	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 1;
	
	private BufferedImage spritesIce_Up;
	private BufferedImage spritesIce_Down;
	private BufferedImage spritesIce_Right;
	private BufferedImage spritesIce_Left;
	private BufferedImage[] spritesIce_Explosion;
	
	public StandardIceSpellCast(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		
		spritesIce_Explosion = new BufferedImage[2];
		
		spritesIce_Up = Entity.ICEUP_EN;
		spritesIce_Down = Entity.ICEDOWN_EN;
		spritesIce_Right = Entity.ICERIGHT_EN;
		spritesIce_Left = Entity.ICELEFT_EN;
		spritesIce_Explosion[0] = Entity.ICEEXPLOSION_EN1;
		spritesIce_Explosion[1] = Entity.ICEEXPLOSION_EN2;
		
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
			g.drawImage(spritesIce_Explosion[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			
			if(lastPosition == Game.player.up_dir) {
				g.drawImage(spritesIce_Up, this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(lastPosition == Game.player.down_dir) {
				g.drawImage(spritesIce_Down, this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(lastPosition == Game.player.right_dir) {
				g.drawImage(spritesIce_Right, this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(lastPosition == Game.player.left_dir) {
				g.drawImage(spritesIce_Left, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			
		}
		
	}
	
	public void destroySelf() {
		Game.sicespell.remove(this);
	}

}
