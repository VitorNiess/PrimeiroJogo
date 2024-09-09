package com.nn.entities;

import java.awt.image.BufferedImage;

public class Staff extends Entity{

	public Staff(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.depth = 0;
	}


}
