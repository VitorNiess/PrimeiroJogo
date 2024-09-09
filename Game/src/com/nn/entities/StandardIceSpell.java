package com.nn.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nn.world.Camera;

public class StandardIceSpell extends Entity{

	private int frames = 0, maxFrames = 50, index = 0, maxIndex = 3;
	private BufferedImage[] spritesIce_Spell;
	
	public int spellGain = 12;
	
	public StandardIceSpell(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		spritesIce_Spell = new BufferedImage[4];
		
		spritesIce_Spell[0] = Entity.ICESPELL_EN2;
		spritesIce_Spell[1] = Entity.ICESPELL_EN1;
		spritesIce_Spell[2] = Entity.ICESPELL_EN2;
		spritesIce_Spell[3] = Entity.ICESPELL_EN3;
		
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
		g.drawImage(spritesIce_Spell[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
