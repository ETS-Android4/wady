package com.mpv.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

	//Sound
	public static Sound 	
	blockSound,
	newPosSound,
	gameOverSound,
	edgeSound,
	buttonSound;
	//Music
	public static Music
	gameMusic,
	menuMusic;
	public static Button menuButton, gameButton;
	//Skin
	public static Skin skin;
	//Maps
	public static TiledMap map1; 
	//Textures & regions
	private static TextureAtlas textureAtlas;
	public static Animation animation;
	//Shaders
	//public static ShaderProgram shader;
	
	public static void dispose() {
		audioDispose();
		skin.dispose();
		skin = null;
		map1.dispose();
		map1 = null;
		//shader.dispose();
		//shader = null;
		textureAtlas.dispose();
		textureAtlas = null;
		animation = null;
	}

	private static void audioDispose(){
		//Music
		gameMusic.stop();
		menuMusic.stop();
		gameMusic.dispose();
		menuMusic.dispose();
		gameMusic = null;
		menuMusic = null;
		//Sound
		blockSound.dispose();
		blockSound = null;
		newPosSound.dispose();
		newPosSound = null;
		gameOverSound.dispose();
		gameOverSound = null;
		edgeSound.dispose();
		edgeSound = null;
		buttonSound.dispose();
		buttonSound = null;
	}
	
	public static void Load() {
		//Music
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menu.mp3"));
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/game.mp3"));
		gameMusic.setVolume(0.3f);
		//Sounds
		blockSound = Gdx.audio.newSound(Gdx.files.internal("sounds/clack1.mp3"));
		newPosSound = Gdx.audio.newSound(Gdx.files.internal("sounds/stuck.mp3"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3"));
		edgeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/edge_hit.mp3"));
		buttonSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button.mp3"));
		//Skin & Font
		skin = new Skin(Gdx.files.internal("data/skin.json"));
		skin.getFont("normaltext").setScale(0.5f);
		//Tiled maps
		map1 = new TmxMapLoader().load("maps/level01.tmx");
		//
		loadAnimation();
		//Shader
		ShaderProgram.pedantic = false;
		/*shader = new ShaderProgram(
				Gdx.files.internal("shaders/red.vsh"), 
				Gdx.files.internal("shaders/red.fsh"));
		System.out.println(shader.isCompiled() ? "shader compiled" : shader.getLog());*/

	}
	
	private static void loadAnimation() {

		textureAtlas = new TextureAtlas(Gdx.files.internal("data/animation.atlas"));
		animation = new Animation(Const.ANIMATION_SPEED, textureAtlas.getRegions());
		
	}
	

	
	public static void pauseMusic() {
		menuMusic.pause();
		gameMusic.pause();
		menuButton.setChecked(true);
		gameButton.setChecked(true);
	}
	public static void playMusic(Music music) {
		if (Settings.musicEnabled) {
			music.play();
			menuButton.setChecked(false);
			gameButton.setChecked(false);
		} else {
			pauseMusic();
		}
	}
	public static void playSnd (Sound sound) { 		playSnd(sound, 1, 1); 	}
	public static void playSnd (Sound sound, float volume) {	playSnd(sound, volume, 1); 	}
	public static void playSnd (Sound sound, float volume, float pitch) {
		if (Settings.soundEnabled) 	sound.play(volume, pitch, 0);
	}
	
}
