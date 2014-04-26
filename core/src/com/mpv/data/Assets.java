package com.mpv.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private static Texture animationTexture;
	private static TextureRegion[] _frames;
	public static Animation animation;
	//Shaders
	public static ShaderProgram shader;

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
		skin.getFont("normaltext").setScale(0.4f);
		//Tiled maps
		map1 = new TmxMapLoader().load("maps/level01.tmx");
		//Animation
		animationTexture = new Texture(Gdx.files.internal("data/exp2.png")); 
		TextureRegion[][] tmp = TextureRegion.split(animationTexture, animationTexture.getWidth() / 
				4, animationTexture.getHeight() / 4);
		int index = 0;
		_frames = new TextureRegion[4*4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				_frames[index++] = tmp[i][j];
			}
		}
		animation = new Animation(Const.ANIMATION_SPEED, _frames);
		//Shader
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(
				Gdx.files.internal("shaders/red.vsh"), 
				Gdx.files.internal("shaders/red.fsh"));
		System.out.println(shader.isCompiled() ? "shader compiled" : shader.getLog());

	}

	public static void dispose() {
		audioDispose();
		skin.dispose();
		animationTexture.dispose();
		map1.dispose();
		shader.dispose();
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
		newPosSound.dispose();
		gameOverSound.dispose();
		edgeSound.dispose();
		buttonSound.dispose();
	}

}
