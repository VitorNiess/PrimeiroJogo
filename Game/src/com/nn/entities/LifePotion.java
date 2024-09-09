package com.nn.entities;

import java.awt.image.BufferedImage;

public class LifePotion extends Entity{

	public int lifeGain = 50;
	
	public LifePotion(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.depth = 0;
	}
	
}
