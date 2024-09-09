package com.nn.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.nn.main.Game;
import com.nn.main.Sound;
import com.nn.world.Camera;
import com.nn.world.World;

public class GarlicEnemy extends Entity{
	
	private final double speed = 1;
	private double speedE = speed;
	private double damage = 0.2;
	private double trackingDistance = 200;
	public double distancePlayer = 0;
	private final int maxLife = 5;
	private double lifeAtual = maxLife;
	private int stunTick = 0, slowTick = 0, burnTick = 0, burnTimeTick = 0;
	
	private int maskx = 0, masky = 0, maskw = 16, maskh = 16;
	
	private int frames = 0, maxFrames = 15, index = 0, maxIndex = 2;
	private BufferedImage[] spritesGarlic;
	private BufferedImage[] spritesGarlicDMG;
	
	private boolean isDamaged = false, isStunned = false, isSlowed = false, isBurnt = false;
	private int framesDamaged = 0, damageTick = 10;

	public GarlicEnemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		spritesGarlic = new BufferedImage[3];
		spritesGarlicDMG = new BufferedImage[3];
		
		spritesGarlic[0] = Entity.GARLIC_EN1;
		spritesGarlic[1] = Entity.GARLIC_EN2;
		spritesGarlic[2] = Entity.GARLIC_EN3;
		spritesGarlicDMG[0] = Entity.GARLICDMG_EN1;
		spritesGarlicDMG[1] = Entity.GARLICDMG_EN2;
		spritesGarlicDMG[2] = Entity.GARLICDMG_EN2;
		
		this.depth = 1;
		
	}
	
	public void tick() {
		this.distancePlayer = this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY());
		
		if(distancePlayer < trackingDistance) {
			
			if(isCollidingWithPlayer() == false) {
				
				if(Game.rand.nextInt(101) >= 80) {
					
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
		Game.garlicenemies.remove(this);
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
		
		for(int i = 0; i < Game.garlicenemies.size(); i++) {
			GarlicEnemy e = Game.garlicenemies.get(i);
			
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
			g.drawImage(spritesGarlic[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			g.drawImage(spritesGarlicDMG[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
	}

}
