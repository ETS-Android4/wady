package com.mpv.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mpv.game.world.GameObj;
import com.thesecretpie.shader.ShaderManager;

public class Assets {

	// AssetManager
	public static AssetManager am = new AssetManager();
	public static ShaderManager sm = new ShaderManager("shaders", am);
	// Music
	public static Music gameMusic, menuMusic, gameAmbient;
	// Skin
	public static Skin skin;
	// Maps
	public static TiledMap map;
	public static int mapPixelWidth, mapPixelHeight, mapScaledWidth, mapScaledHeight;
	public static float mapUnitScale;
	// Animation
	private static TextureAtlas animationAtlas;
	public static Animation<?> animation;
	// Particles
	public static ParticleEffect hitEffect;

	public static void dispose() {
		am.dispose();
		sm.dispose();
		audioDispose();
		skin.dispose();
		skin = null;
		disposeMap();
		// shader.dispose();
		// shader = null;
		animationAtlas.dispose();
		animationAtlas = null;
		animation = null;
		hitEffect.dispose();
	}

	private static void audioDispose() {
		// Music
		gameMusic.stop();
		menuMusic.stop();
		gameAmbient.stop();
		gameMusic.dispose();
		menuMusic.dispose();
		gameMusic = null;
		gameAmbient = null;
		menuMusic = null;
	}

	public static void load() {
		// Music
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menu.mp3"));
		menuMusic.setLooping(true);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameloop.mp3"));
		gameMusic.setLooping(true);
		gameAmbient = Gdx.audio.newMusic(Gdx.files.internal("music/game.mp3"));
		gameAmbient.setLooping(true);

		Sounds.load(am);

		// Skin & Font
		skin = new Skin(Gdx.files.internal("data/skin.json"));
		skin.getFont("normaltext").getData().setScale(GVars.scrWidth / 640f * 1.2f);

		loadAnimation();
		// Shader
		sm.add("bloom", "default.vert", "bloom.frag");
		sm.createFB("bg_fb");
		// Particles
		hitEffect = new ParticleEffect();
		hitEffect.load(Gdx.files.internal("effects/engine.p"), Gdx.files.internal("effects"));
	}

	public static void loadMap(int index) {
		// Tiled maps
		disposeMap();
		map = new TmxMapLoader().load("maps/templ" + String.format("%03d", index) + ".tmx");

		MapProperties prop = Assets.map.getProperties();

		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		int tilePixelHeight = prop.get("tileheight", Integer.class);

		mapPixelWidth = mapWidth * tilePixelWidth;
		mapPixelHeight = mapHeight * tilePixelHeight;
		mapUnitScale = GVars.scrWidth / Const.VIEWPORT_PIXELS;
		mapScaledWidth = Math.round(mapPixelWidth * mapUnitScale);
		mapScaledHeight = Math.round(mapPixelHeight * mapUnitScale);
		// int mapPixelHeight = mapHeight * tilePixelHeight;
		GVars.otmRendered = new OrthogonalTiledMapRenderer(Assets.map, mapUnitScale, GVars.spriteBatch);
		GVars.widthInMeters = mapScaledWidth * GVars.WORLD_TO_BOX;
		GVars.heightInMeters = mapScaledHeight * GVars.WORLD_TO_BOX;
		GameObj.get().loadWorld();
	}

	public static void disposeMap() {
		if (map != null) {
			map.dispose();
			map = null;
		}
	}

	private static void loadAnimation() {
		animationAtlas = new TextureAtlas(Gdx.files.internal("data/animation.atlas"));
		animation = new Animation<>(Const.ANIMATION_SPEED, animationAtlas.getRegions());
	}

	public static void pauseMusic() {
		menuMusic.pause();
		gameMusic.pause();
		gameAmbient.pause();
	}

	public static void playMusic(Music music) {
		if (Settings.musicEnabled) {
			music.play();
		} else {
			pauseMusic();
		}
	}

	public static void gameMusic() {
		playMusic(gameMusic);
		playMusic(gameAmbient);
	}
}
