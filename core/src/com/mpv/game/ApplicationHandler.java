package com.mpv.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.mpv.data.Assets;
import com.mpv.data.GVars;
import com.mpv.data.Settings;
import com.mpv.game.world.GameObject;
import com.mpv.screens.GameScreen;
import com.mpv.screens.LevelScreen;
import com.mpv.screens.MenuScreen;
import com.mpv.screens.ScoresScreen;

public class ApplicationHandler extends Game {

	public MenuScreen menuScreen;
	public GameScreen gameScreen;
	public GameObject gameObject;
	public ScoresScreen scoresScreen;
	public LevelScreen levelScreen;
	public static IReqHandler ExternalHandler;
	
	public ApplicationHandler(IReqHandler irh) {
		ApplicationHandler.ExternalHandler = irh;
	}
	
	public ApplicationHandler() {
		
	}
	@Override
	public void create() {
		//Loading native libraries
		GdxNativesLoader.load();
		
		//GVars initial setup
		GVars.app = this;
		GVars.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		//Initialize configuration and resources
		Settings.load();
		Assets.load();

		//Custom buttons
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		
		gameObject = GameObject.getInstance();		
		gameScreen = new GameScreen();
		menuScreen = new MenuScreen();
		scoresScreen = new ScoresScreen();
		levelScreen = new LevelScreen();

		this.setScreen(menuScreen);
	}
	@Override
	public void dispose() {		
		GVars.dispose();
		Assets.dispose();
		super.dispose();
		menuScreen.dispose();
		scoresScreen.dispose();
		levelScreen.dispose();
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}
	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}
}