package com.nn.world;

public class Camera {

	public static int x, y;
	
	public static int clamp(int atual, int min, int max) {
		
		if(atual < min) {
			atual = min;
		}
		
		if(atual > max) {
			atual = max;
		}
		
		return atual;
	}

}
