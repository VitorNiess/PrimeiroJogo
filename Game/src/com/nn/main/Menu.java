package com.nn.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.nn.entities.Player;
import com.nn.graphics.Spritesheet;
import com.nn.world.World;

public class Menu {

	public String[] options = {"novo jogo", "continuar", "sair"};
	public static String arquivo_save = "save.txt";
	
	public int curOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;
	public static boolean pause = false, saveExists = false, saveGame = false;
	
	public static Spritesheet spriteMenuBackground = new Spritesheet("/menu_image.png");
	public static BufferedImage menu_background = spriteMenuBackground.getSprite(0, 0, Game.WIDTH, Game.HEIGHT);
	
	public void tick() {
		File file = new File(arquivo_save);
		
		if(file.exists()) {
			saveExists = true;
		} else {
			saveExists = false;
		}
		
		if(up) {
			Sound.selectOption.play();
			up = false;
			curOption--;
			
			if(curOption < 0) {
				curOption = maxOption;
			}
			
		}
		
		if(down) {
			Sound.selectOption.play();
			down = false;
			curOption++;
			
			if(curOption > maxOption) {
				curOption = 0;
			}
			
		}
		
		if(enter) {
			Sound.selectOption.play();
			enter = false;
			
			switch(options[curOption]) {
				case "novo jogo":
					Game.gameState = "GAME";
					pause = false;
					Sound.menuMusic.stop();
					Sound.gameMusic.loop();
					file = new File(arquivo_save);
					file.delete();
					break;
					
				case "sair":
					System.exit(1);
					break;
					
				case "continuar":
					file = new File(arquivo_save);
					if(file.exists()) {
						String saver = loadGame(47);
						applySave(saver);
					}
					break;		
			}
			
		}
		
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			
			switch(spl2[0]) {
				case "map":
					World.restartGame("map" + spl2[1] + ".png");
					Game.gameState = "GAME";
					pause = false;
					break;
				case "life":
					Game.player.lifeAtual = Integer.parseInt(spl2[1]);
					break;
				case "fire":
					Player.fireSpellCasting = Integer.parseInt(spl2[1]);
					break;
				case "ice":
					Player.iceSpellCasting = Integer.parseInt(spl2[1]);
					break;
				case "lighting":
					Player.lightingSpellCasting = Integer.parseInt(spl2[1]);
					break;
				case "hasStaff":
					Player.hasStaff = Integer.parseInt(spl2[1]);
					break;
				case "posX":
					Game.player.setX(Integer.parseInt(spl2[1]));
					break;
				case "posY":
					Game.player.setY(Integer.parseInt(spl2[1]));
					break;
				case "spiders":
					System.out.println(Integer.parseInt(spl2[1]));
					break;
				case "garlics":
					System.out.println(Integer.parseInt(spl2[1]));
					break;
					
			}
			
		}
		
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File(arquivo_save);
		if(file.exists()) {
			
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader(arquivo_save));
				
				try {
					
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						
						for(int i = 0; i < val.length; i++) {
							val[i] -= encode;
							trans[1] += val[i];
						}
						
						line += trans[0];
						line += ":";
						line += trans[1];
						line += "/";
					}
					
				} catch(IOException e) {
					
				}
				
			} catch(FileNotFoundException e) {
				
			}
			
		}
		
		return line;
	}
	
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		
		try {
			write = new BufferedWriter(new FileWriter(arquivo_save));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			
			for(int n = 0; n < value.length; n++) {
				value[n] += encode;
				current += value[n];
			}
			
			try {
				write.write(current);
				
				if(i < val1.length - 1) {
					write.newLine();
				}
				
			} catch(IOException e) {
				
			}
		}
		
		try {
			write.flush();
			write.close();
		} catch(IOException e) {
			
		}
		
	}
	
	public void render(Graphics g) {
		
		if(!pause) {
			g.drawImage(menu_background, 0, 0, Game.WIDTH * Game.scale, Game.HEIGHT * Game.scale, null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.drawString("O jogo", 550, 200);
		} else {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, Game.WIDTH * Game.scale, Game.HEIGHT * Game.scale);
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.drawString("Pause", 550, 200);
		}
		
		g.setFont(new Font("arial", Font.BOLD, 24));
		
		if(!pause) {
			g.drawString("Novo Jogo", 450, 300);
		} else {
			g.drawString("Voltar ao jogo", 450, 300);
		}
		
		g.drawString("Carregar jogo salvo", 450, 350);
		g.drawString("Sair", 450, 400);
		
		if(options[curOption] == "novo jogo") {
			g.drawString(">", 420, 300);
		} else if(options[curOption] == "continuar") {
			g.drawString(">", 420, 350);
		} else if(options[curOption] == "sair") {
			g.drawString(">", 420, 400);
		}
		
	}

}
