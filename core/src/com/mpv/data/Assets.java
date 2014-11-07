package com.mpv.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mpv.game.world.GameObject;

public class Assets {
    // Sound
    public static Sound hit1Snd, failSnd, buttonSnd, winSnd, gongSnd, dingSnd, wingSnd, counterSnd, blopSnd;
    // Music
    public static Music gameMusic, menuMusic;
    // Skin
    public static Skin skin;
    // Maps
    public static TiledMap map;
    public static int mapPixelWidth, mapPixelHeight, mapScaledWidth, mapScaledHeight;
    public static float mapUnitScale;
    // Animation
    private static TextureAtlas animationAtlas;
    public static Animation animation;
    // Shaders
    // public static ShaderProgram shader;
    // Particles
    public static ParticleEffect hitEffect;

    public static void dispose() {
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
	gameMusic.dispose();
	menuMusic.dispose();
	gameMusic = null;
	menuMusic = null;
	// Sound
	hit1Snd.dispose();
	hit1Snd = null;
	failSnd.dispose();
	failSnd = null;
	buttonSnd.dispose();
	buttonSnd = null;
	winSnd.dispose();
	winSnd = null;
	gongSnd.dispose();
	gongSnd = null;
	wingSnd.dispose();
	wingSnd = null;
	dingSnd.dispose();
	dingSnd = null;
	counterSnd.dispose();
	counterSnd = null;
	blopSnd.dispose();
	blopSnd = null;
    }

    public static void load() {
	// Music
	menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menu.mp3"));
	menuMusic.setLooping(true);
	gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/game.mp3"));
	gameMusic.setLooping(true);
	// Sounds
	hit1Snd = Gdx.audio.newSound(Gdx.files.internal("sounds/hit1.mp3"));
	failSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/ouuu.mp3"));
	winSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/wow.mp3"));
	buttonSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/button.mp3"));
	gongSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/gong.mp3"));
	wingSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/wing.mp3"));
	dingSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/ding.mp3"));
	counterSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/counter.mp3"));
	blopSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/blop.mp3"));
	// Skin & Font
	skin = new Skin(Gdx.files.internal("data/skin.json"));
	skin.getFont("normaltext").setScale(GVars.scrWidth / 640f * 1.2f);

	loadAnimation();
	// Shader
	ShaderProgram.pedantic = false;
	/*
	 * shader = new ShaderProgram( Gdx.files.internal("shaders/red.vsh"),
	 * Gdx.files.internal("shaders/red.fsh"));
	 * System.out.println(shader.isCompiled() ? "shader compiled" :
	 * shader.getLog());
	 */
	// Particles
	hitEffect = new ParticleEffect();
	hitEffect.load(Gdx.files.internal("effects/engine.p"), Gdx.files.internal("effects"));
    }

    public static void loadMap(int page, int index) {
	// Tiled maps
	disposeMap();
	map = new TmxMapLoader().load("maps/templ" + String.format("%d%02d", page, index) + ".tmx");

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
	GameObject.getInstance().loadWorld();
    }

    public static void disposeMap() {
	if (map != null) {
	    map.dispose();
	    map = null;
	}
    }

    private static void loadAnimation() {
	animationAtlas = new TextureAtlas(Gdx.files.internal("data/animation.atlas"));
	animation = new Animation(Const.ANIMATION_SPEED, animationAtlas.getRegions());
    }

    public static void pauseMusic() {
	menuMusic.pause();
	gameMusic.pause();
    }

    public static void playMusic(Music music) {
	if (Settings.musicEnabled) {
	    music.play();
	} else {
	    pauseMusic();
	}
    }

    public static void playSnd(Sound sound) {
	playSnd(sound, 1, 1);
    }

    public static void playSnd(Sound sound, float volume) {
	playSnd(sound, volume, 1);
    }

    public static void playSnd(Sound sound, float volume, float pitch) {
	if (Settings.soundEnabled)
	    sound.play(volume, pitch, 0);
    }

    public static void loopSnd(Sound sound) {
	loopSnd(sound, 1, 1);
    }

    public static void loopSnd(Sound sound, float volume) {
	loopSnd(sound, volume, 1);
    }

    public static void loopSnd(Sound sound, float volume, float pitch) {
	if (Settings.soundEnabled)
	    sound.loop(volume, pitch, 0);
    }
}
