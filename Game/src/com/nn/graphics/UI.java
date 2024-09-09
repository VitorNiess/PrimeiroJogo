package com.nn.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.nn.entities.Player;
import com.nn.main.Game;

public class UI {

	public void render(Graphics g) {
		//g.setColor(Color.GRAY);
		//g.fillRect(0, 146, 160, 14);
		g.setColor(Color.BLACK);
		g.fillRect(105, 149, 50, 8);
		g.setColor(Color.RED);
		g.fillRect(105, 149, (int)((Game.player.lifeAtual / Game.player.maxLife) * 50), 8);
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.drawString("Saúde: ", 68, 156);
		
		g.drawString("Fogo: " + Player.fireSpellCasting, 5, 156);
		g.drawString("Raio: " + Player.lightingSpellCasting, 5, 144);
		g.drawString("Gelo: " + Player.iceSpellCasting, 5, 134);
		
	}

}
