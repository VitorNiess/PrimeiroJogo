package com.nn.entities;

import com.nn.main.Game;
import com.nn.main.Sound;
import com.nn.world.Camera;
import com.nn.world.World;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity{

	public boolean up, down, right, left;
	public final int right_dir = 2, left_dir = 3, up_dir = 1, down_dir = 0;
	public int dir = down_dir;
	public int speed = 1;
	
	private int frames = 0, maxFrames = 7, index = 0, maxIndex = 2;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage damagedPlayerUp;
	private BufferedImage damagedPlayerDown;
	private BufferedImage damagedPlayerRight;
	private BufferedImage damagedPlayerLeft;
	
	public boolean isDamaged;
	private int framesDamaged = 0, damageTick = 5;
	
	public static int hasStaff = 0;
	public static int fireSpellCasting = 0;
	public static int lightingSpellCasting = 0;
	public static int iceSpellCasting = 0;
	private int spellSize = 4;
	public boolean fireballcasted = false, lightingcasted = false, icecasted = false;
	
	private int itemDistance = 5;
	
	public final double maxLife = 100;
	public double lifeAtual = maxLife;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];
		upPlayer = new BufferedImage[3];
		downPlayer = new BufferedImage[3];
		
		damagedPlayerDown = Game.spritesheet.getSprite(112, 16, World.TILE_SIZE, World.TILE_SIZE);
		damagedPlayerUp = Game.spritesheet.getSprite(128, 16, World.TILE_SIZE, World.TILE_SIZE);
		damagedPlayerRight = Game.spritesheet.getSprite(144, 16, World.TILE_SIZE, World.TILE_SIZE);
		damagedPlayerLeft = Game.spritesheet.getSprite(0, 32, World.TILE_SIZE, World.TILE_SIZE);
		
		downPlayer[0] = Game.spritesheet.getSprite(48, 0, World.TILE_SIZE, World.TILE_SIZE);
		downPlayer[1] = Game.spritesheet.getSprite(96, 0, World.TILE_SIZE, World.TILE_SIZE);
		downPlayer[2] = Game.spritesheet.getSprite(112, 0, World.TILE_SIZE, World.TILE_SIZE);
		
		upPlayer[0] = Game.spritesheet.getSprite(128, 0, World.TILE_SIZE, World.TILE_SIZE);
		upPlayer[1] = Game.spritesheet.getSprite(144, 0, World.TILE_SIZE, World.TILE_SIZE);
		upPlayer[2] = Game.spritesheet.getSprite(0, 16, World.TILE_SIZE, World.TILE_SIZE);
		
		for(int i = 0; i < 3; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(16 + (i * World.TILE_SIZE), 16, World.TILE_SIZE, World.TILE_SIZE);
		}
		
		for(int i = 0; i < 3; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(64 + (i * World.TILE_SIZE), 16, World.TILE_SIZE, World.TILE_SIZE);
		}
		
		this.depth = 2;
		
	}
	
	public void tick() {
		moved = false;
		
		if(right && World.isFree(this.getX() + speed, this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		}
		
		if(left && World.isFree(this.getX() - speed, this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		
		if(up && World.isFree(this.getX(), this.getY()  - speed)) {
			moved = true;
			dir = up_dir;
			y -= speed;
		}
		
		if(down && World.isFree(this.getX(), this.getY() + speed)) {
			moved = true;
			dir = down_dir;
			y += speed;
		}
		
		if(moved) {
			frames++;
			
			if(frames == maxFrames) {
				frames = 0;
				index++;
				
				if(index > maxIndex) {
					index = 0;
				}
				
			}
			
		}
		
		if(isDamaged) {
			this.framesDamaged++;
			
			if(this.framesDamaged == damageTick) {
				this.framesDamaged = 0;
				isDamaged = false;
			}
			
		}
		
		if(lifeAtual <= 0) {
			
			//Game Over
			lifeAtual= 0;
			Game.gameState = "GAME_OVER";
			
		}
		
		if(fireballcasted) {
			fireballcasted = false;
			
			if(hasStaff == 1 && fireSpellCasting > 0) {
				Sound.fireBallCast.play();
				fireSpellCasting--;
				
				int dx = 0, dy = 0, px = 0, py = 0;
				
				if(dir == up_dir) {
					dy = -1;
					px = 8;
					py = -10;
				} else if(dir == down_dir) {
					dy = 1;
					px = -2;
					py = 2;
				} else if(dir == right_dir) {
					dx = 1;
					py = -2;
					px = 15;
				} else if(dir == left_dir) {
					dx = -1;
					px = -12;
					py = -2;
				}
				
				StandardFireSpellCast projectile = new StandardFireSpellCast(this.getX() + px, this.getY() + py, spellSize, spellSize, null, dx, dy);
				Game.sfirespell.add(projectile);
			}
			
		}
		
		if(lightingcasted) {
			lightingcasted = false;
			
			if(hasStaff == 1 && lightingSpellCasting > 0) {
				Sound.fireBallCast.play();
				lightingSpellCasting--;
				
				int dx = 0, dy = 0, px = 0, py = 0;
				
				if(dir == up_dir) {
					dy = -1;
					px = 8;
					py = -10;
				} else if(dir == down_dir) {
					dy = 1;
					px = -2;
					py = 2;
				} else if(dir == right_dir) {
					dx = 1;
					py = -2;
					px = 15;
				} else if(dir == left_dir) {
					dx = -1;
					px = -12;
					py = -2;
				}
				
				StandardLightingSpellCast projectile = new StandardLightingSpellCast(this.getX() + px, this.getY() + py, spellSize, spellSize, null, dx, dy);
				Game.slightingspell.add(projectile);
			}
		
		}
		
		if(icecasted) {
			icecasted = false;
			
			if(hasStaff == 1 && iceSpellCasting > 0) {
				Sound.fireBallCast.play();
				iceSpellCasting--;
				
				int dx = 0, dy = 0, px = 0, py = 0;
				
				if(dir == up_dir) {
					dy = -1;
					px = 8;
					py = -10;
				} else if(dir == down_dir) {
					dy = 1;
					px = -2;
					py = 2;
				} else if(dir == right_dir) {
					dx = 1;
					py = -2;
					px = 15;
				} else if(dir == left_dir) {
					dx = -1;
					px = -12;
					py = -2;
				}
				
				StandardIceSpellCast projectile = new StandardIceSpellCast(this.getX() + px, this.getY() + py, spellSize, spellSize, null, dx, dy);
				Game.sicespell.add(projectile);
			}
	
		}
		
		checkCollisionLife();
		checkFireCollisionSpell();
		checkLightingCollisionSpell();
		checkIceCollisionSpell();
		checkCollisionStaff();
		
		updateCamera();
		
	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, (World.WIDTH * 16) - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, (World.HEIGHT * 16) - Game.HEIGHT);
	}
	
	public void checkFireCollisionSpell() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			
			if(e instanceof StandardFireSpell) {
				
				if(Entity.isColliding(this, e)) {
					fireSpellCasting += ((StandardFireSpell) e).spellGain;
					Sound.itemEffect.play();
					
					Game.entities.remove(i);
					return;
				}
				
			}
			
		}
		
	}
	
	public void checkLightingCollisionSpell() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			
			if(e instanceof StandardLightingSpell) {
				
				if(Entity.isColliding(this, e)) {
					lightingSpellCasting += ((StandardLightingSpell) e).spellGain;
					Sound.itemEffect.play();
					
					Game.entities.remove(i);
					return;
				}
				
			}
			
		}
		
	}
	
	public void checkIceCollisionSpell() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			
			if(e instanceof StandardIceSpell) {
				
				if(Entity.isColliding(this, e)) {
					iceSpellCasting += ((StandardIceSpell) e).spellGain;
					Sound.itemEffect.play();
					
					Game.entities.remove(i);
					return;
				}
				
			}
			
		}
		
	}
	
	public void checkCollisionLife() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			
			if(e instanceof LifePotion) {
				
				if(Entity.isColliding(this, e)) {
					lifeAtual += ((LifePotion) e).lifeGain;
					Sound.itemEffect.play();
					
					if(lifeAtual >= 100) {
						lifeAtual = 100;
					}
					
					Game.entities.remove(i);
					return;
				}
				
			}
			
		}
		
	}
	
	public void checkCollisionStaff() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			
			if(e instanceof Staff) {
				
				if(Entity.isColliding(this, e)) {
					hasStaff = 1;
					Sound.itemEffect.play();
					
					Game.entities.remove(i);
					return;
				}
				
			}
			
		}
		
	}
	
	public void render(Graphics g) {
		
		if(!isDamaged) {
			
			if(dir == up_dir) {
				
				if(hasStaff == 1) {
					g.drawImage(Entity.STAFF_RIGHT, (this.getX() + itemDistance) - Camera.x, this.getY() - Camera.y, null);
				}
				
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX()- Camera.x, this.getY()- Camera.y, null);
				
				if(hasStaff == 1) {
					g.drawImage(Entity.STAFF_LEFT, (this.getX() - itemDistance) - Camera.x, this.getY() - Camera.y, null);
				}
				
			} else if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX()- Camera.x, this.getY()- Camera.y, null);
				
				if(hasStaff == 1) {
					g.drawImage(Entity.STAFF_RIGHT, (this.getX() + itemDistance) - Camera.x, this.getY() - Camera.y, null);
				}
				
			} else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX()- Camera.x, this.getY()- Camera.y, null);
				
				if(hasStaff == 1) {
					g.drawImage(Entity.STAFF_LEFT, (this.getX() - itemDistance) - Camera.x, this.getY() - Camera.y, null);
				}
				
			}
			
		} else {
			
			if(dir == up_dir) {
				
				if(hasStaff == 1) {
					g.drawImage(Entity.STAFF_RIGHTDMG, (this.getX() + itemDistance) - Camera.x, this.getY() - Camera.y, null);
				}
				
				g.drawImage(damagedPlayerUp, this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if(dir == down_dir) {
				g.drawImage(damagedPlayerDown, this.getX()- Camera.x, this.getY()- Camera.y, null);
				
				if(hasStaff == 1) {
					g.drawImage(Entity.STAFF_LEFTDMG, (this.getX() - itemDistance) - Camera.x, this.getY() - Camera.y, null);
				}
				
			}else if(dir == right_dir) {
				g.drawImage(damagedPlayerRight, this.getX()- Camera.x, this.getY()- Camera.y, null);
				
				if(hasStaff == 1) {
					g.drawImage(Entity.STAFF_RIGHTDMG, (this.getX() + itemDistance) - Camera.x, this.getY() - Camera.y, null);
				}
				
			} else if(dir == left_dir) {
				g.drawImage(damagedPlayerLeft, this.getX()- Camera.x, this.getY()- Camera.y, null);
				
				if(hasStaff == 1) {
					g.drawImage(Entity.STAFF_LEFTDMG, (this.getX() - itemDistance) - Camera.x, this.getY() - Camera.y, null);
				}
				
			}
			
		}
	
	}

}