package com.nn.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	private AudioClip clip;
	
	public static final Sound fireBallCast = new Sound("/fireball_cast.wav");
	public static final Sound gameMusic = new Sound("/music_back.wav");
	public static final Sound menuMusic = new Sound("/menu_music.wav");
	public static final Sound hurtEffect = new Sound("/hit_hurt2.wav");
	public static final Sound selectOption = new Sound("/select.wav");
	public static final Sound deathEffect = new Sound("/death.wav");
	public static final Sound itemEffect = new Sound("/item_pick.wav");
	
	private Sound(String name) {
		
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch(Throwable e) {
			
		}
		
	}
	
	public void play() {
		
		try {
			
			new Thread() {
				
				public void run() {
					clip.play();
				}
				
			}.start();
			
		} catch(Throwable e) {
			
		}
		
	}
	
	public void stop() {
		
		try {
			
			new Thread() {
				
				public void run() {
					clip.stop();
				}
				
			}.start();
			
		} catch(Throwable e) {
			
		}
		
	}
	
	public void loop() {
		
		try {
			
			new Thread() {
				
				public void run() {
					clip.loop();
				}
				
			}.start();
			
		} catch(Throwable e) {
			
		}
		
	}

}
