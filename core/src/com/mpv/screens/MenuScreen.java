package com.mpv.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mpv.screens.stages.MainMenuStage;

public class MenuScreen implements Screen {


	private MainMenuStage mainMenuStage;	
	
	public MenuScreen() {

		mainMenuStage = new MainMenuStage();
        Gdx.input.setInputProcessor(mainMenuStage);
		
	}
	
	public void resize (int width, int height) {
	        mainMenuStage.getViewport().setWorldSize(width, height);
	}


	public void dispose() {
		mainMenuStage.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0f, 0f, 0f, 1);    
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainMenuStage.act(Gdx.graphics.getDeltaTime());
        mainMenuStage.draw();
        //Table.drawDebug(stage); // This is optional, but enables debug lines for tables.
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(mainMenuStage);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

}
