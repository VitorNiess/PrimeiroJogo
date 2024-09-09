package com.nn.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.nn.main.Game;
import com.nn.main.Sound;
import com.nn.world.Camera;
import com.nn.world.World;

public class Enemy extends Entity{
	
	private final double speed = 1.5;
	private double speedE = speed;
	private double damage = 0.8;
	private double trackingDistance = 120;
	public double distancePlayer = 0;
	private final int maxLife = 10;
	private double lifeAtual = maxLife;
	private int stunTick = 0, slowTick = 0, burnTick = 0, burnTimeTick = 0;
	
	private int maskx = 0, masky = 0, maskw = 16, maskh = 16;
	
	private int frames = 0, maxFrames = 30, index = 0, maxIndex = 1;
	private BufferedImage[] spritesSpider_Queen;
	private BufferedImage[] spritesSpider_QueenDMG;
	
	private boolean isDamaged = false, isStunned = false, isSlowed = false, isBurnt = false;
	private int framesDamaged = 0, damageTick = 10;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		spritesSpider_Queen = new BufferedImage[2];
		spritesSpider_QueenDMG = new BufferedImage[2];
		
		spritesSpider_Queen[0] = Entity.QUEENSPIDER_EN1;
		spritesSpider_Queen[1] = Entity.QUEENSPIDER_EN2;
		spritesSpider_QueenDMG[0] = Entity.QUEENSPIDERDMG_EN1;
		spritesSpider_QueenDMG[1] = Entity.QUEENSPIDERDMG_EN2;
		
		this.depth = 1;
		
	}
	
	public void tick() {
		this.distancePlayer = this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY());
		
		if(distancePlayer < trackingDistance) {
			
			if(isCollidingWithPlayer() == false) {
				
				if(Game.rand.nextInt(101) > 60) {
					
					if(x < Game.player.getX() && World.isFree(this.getX() + (int)(speedE), this.getY()) && !isColliding(this.getX() + (int)(speedE), this.getY())) {
						x += speedE;
					} else if(x > Game.player.getX() && World.isFree(this.getX() - (int)(speedE), this.getY()) && !isColliding(this.getX() - (int)(speedE), this.getY())) {
						x -= speedE;
					} 
					
					if(y > Game.player.getY() && World.isFree(this.getX(), this.getY() - (int)(speedE)) && !isColliding(this.getX(), this.getY() - (int)(speedE))) {
						y -= speedE;
					} else if(y < Game.player.getY() && World.isFree(this.getX(), this.getY() + (int)(speedE)) && !isColliding(this.getX(), this.getY() + (int)(speedE))) {
						y += speedE;
					}
					
				}
				
			} else {
				
				//Damaging Player
				
				Game.player.lifeAtual -= damage;
				Game.player.isDamaged = true;
				Sound.hurtEffect.play();
				
			}
			
		}
		
		frames++;
		
		if(frames == maxFrames) {
			frames = 0;
			index++;
			
			if(index > maxIndex) {
				index = 0;
			}
			
		}
		
		burning();
		stunned();
		slowed();
		
		if(lifeAtual <= 0) {
			Sound.deathEffect.play();
			destroySelf();
			return;
		}
		
		if(isDamaged) {
			this.framesDamaged++;
			
			if(this.framesDamaged == this.damageTick) {
				this.framesDamaged = 0;
				this.isDamaged = false;
			}
			
		}
		
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	
	public void burning() {
		
		if(isCollidingStandardFireSpell()) {
			isBurnt = true;
		}
		
		if(isBurnt) {
			burnTick++;
			if(burnTimeTick <= (StandardFireSpellCast.burnTime * 60)) {
				burnTimeTick++;
				
				if(burnTick >= 60) {
					burnTick = 0;
					lifeAtual -= StandardFireSpellCast.dmgPS;
					isDamaged = true;
				}
				
			} else {
				isBurnt = false;
				isDamaged = false;
			}
			
		}
		
	}
	
	public void stunned() {
		
		if(isCollidingStandardLightingSpell()) {
			speedE = 0;
			isStunned = true;
		}
		
		if(stunTick >= StandardLightingSpellCast.stunTime) {
			speedE = speed;
			stunTick = 0;
			isStunned = false;
		}
		
		if(isStunned) {
			stunTick++;
		}
		
	}
	
	public void slowed() {
		
		if(isCollidingStandardIceSpell()) {
			speedE = speed / StandardIceSpellCast.slowRate;
			isSlowed = true;
		}
		
		if(slowTick >= StandardIceSpellCast.slowTime) {
			speedE = speed;
			slowTick = 0;
			isSlowed = false;
		}
		
		if(isSlowed) {
			slowTick++;
		}
		
	}
	
	public boolean isCollidingWithPlayer() {
		
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), World.TILE_SIZE, World.TILE_SIZE);
		
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColliding(int xnext, int ynext) {
		
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			
			if(e == this) {
				continue;
			}
			
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
			
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public boolean isCollidingStandardFireSpell() {
		
		for(int i = 0; i < Game.sfirespell.size(); i++) {
			Entity e = Game.sfirespell.get(i);
			
			if(e instanceof StandardFireSpellCast) {
				
				if(Entity.isColliding(this, e)) {
					isDamaged = true;
					lifeAtual -= StandardFireSpellCast.damage;
					Game.sfirespell.remove(e);
					return true;
				}
				
			}
			
		}
		
		return false;
	}
	
	public boolean isCollidingStandardLightingSpell() {
		
		for(int i = 0; i < Game.slightingspell.size(); i++) {
			Entity e = Game.slightingspell.get(i);
			
			if(e instanceof StandardLightingSpellCast) {
				
				if(Entity.isColliding(this, e)) {
					isDamaged = true;
					lifeAtual -= StandardLightingSpellCast.damage;
					Game.slightingspell.remove(e);
					return true;
				}
				
			}
			
		}
		
		return false;
	}

	public boolean isCollidingStandardIceSpell() {
		
		for(int i = 0; i < Game.sicespell.size(); i++) {
			Entity e = Game.sicespell.get(i);
			
			if(e instanceof StandardIceSpellCast) {
				
				if(Entity.isColliding(this, e)) {
					isDamaged = true;
					lifeAtual -= StandardIceSpellCast.damage;
					Game.sicespell.remove(e);
					return true;
				}
				
			}
			
		}
		
		return false;
	}	
	
	public void render(Graphics g) {
		
		if(!isDamaged) {
			g.drawImage(spritesSpider_Queen[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			g.drawImage(spritesSpider_QueenDMG[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
	}

}
