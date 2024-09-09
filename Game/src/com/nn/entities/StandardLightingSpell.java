package com.nn.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nn.world.Camera;

public class StandardLightingSpell extends Entity{

	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 3;
	private BufferedImage[] spritesLighting_Spell;
	
	public int spellGain = 10;
	
	public StandardLightingSpell(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		spritesLighting_Spell = new BufferedImage[4];
		
		spritesLighting_Spell[0] = Entity.LIGHTINGSPELL_EN1;
		spritesLighting_Spell[1] = Entity.LIGHTINGSPELL_EN2;
		spritesLighting_Spell[2] = Entity.LIGHTINGSPELL_EN1;
		spritesLighting_Spell[3] = Entity.LIGHTINGSPELL_EN3;
		
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
		g.drawImage(spritesLighting_Spell[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
