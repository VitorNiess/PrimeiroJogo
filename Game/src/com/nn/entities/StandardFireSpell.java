package com.nn.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nn.world.Camera;

public class StandardFireSpell extends Entity{

	private int frames = 0, maxFrames = 25, index = 0, maxIndex = 3;
	private BufferedImage[] spritesFire_Spell;
	
	public int spellGain = 8;
	
	public StandardFireSpell(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		spritesFire_Spell = new BufferedImage[4];
		
		spritesFire_Spell[0] = Entity.FIRESPELL_EN1;
		spritesFire_Spell[1] = Entity.FIRESPELL_EN2;
		spritesFire_Spell[2] = Entity.FIRESPELL_EN3;
		spritesFire_Spell[3] = Entity.FIRESPELL_EN2;
		
		this.depth = 0;
	}
	
	public void tick() {
		frames++;
		
		if(frames == maxFrames) {
			frames = 0;
			index++;
			
			if(index > maxIndex) {
				index = 0;
			}
			
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(spritesFire_Spell[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
