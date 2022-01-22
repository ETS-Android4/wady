package com.mpv.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mpv.data.Assets;
import com.mpv.ui.stages.MainMenuStage;

public class MenuScreen implements Screen {

    private final MainMenuStage mainMenuStage;

    public MenuScreen() {
        mainMenuStage = new MainMenuStage();
        // mainMenuStage.setDebugAll(true);
    }

    public void resize(int width, int height) {
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
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(mainMenuStage);
        Assets.pauseMusic();
        Assets.playMusic(Assets.menuMusic);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        // No-op
    }

    @Override
    public void resume() {
        // No-op
    }
}
