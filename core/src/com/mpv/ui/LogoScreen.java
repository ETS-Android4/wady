package com.mpv.ui;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.mpv.data.Assets;
import com.mpv.data.GVars;
import com.mpv.tween.ActorAccessor;

public class LogoScreen implements Screen {

	private Stage stage;
	private TweenManager tweenManager;

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tweenManager.update(delta);
		stage.draw();
		if (tweenManager.getRunningTweensCount() == 0 || Gdx.input.justTouched())
			GVars.app.setScreen(GVars.app.menuScreen);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		stage = new Stage();
		Image image = new Image(Assets.skin.getDrawable("menu-screen"));
		image.getColor().a = 0.4f;
		image.setSize(stage.getWidth(), stage.getWidth() / image.getWidth() * image.getHeight());
		image.setPosition(0, 0);
		stage.addActor(image);
		Image logo = new Image(Assets.skin.getDrawable("batcho"));
		logo.setSize(stage.getWidth() * 0.8f, stage.getWidth() * 0.25f);
		logo.setOrigin(Align.center);
		logo.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
		stage.addActor(logo);
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		// Scale && Rotate
		// Tween.set(logo, ActorAccessor.ALPHA).target(0.5f).start(tweenManager);
		Tween.set(logo, ActorAccessor.SCALE).target(0.98f).start(tweenManager);
		Tween.set(logo, ActorAccessor.ROTATE).target(-1f).start(tweenManager);
		// Tween.to(logo, ActorAccessor.ALPHA, 3f).target(1f).repeatYoyo(100, 0).start(tweenManager);
		Tween.to(logo, ActorAccessor.SCALE, 0.3f).target(1f).repeatYoyo(100, 0).ease(Quad.INOUT).start(tweenManager);
		Tween.to(logo, ActorAccessor.ROTATE, 0.6f).target(1f).repeatYoyo(100, 0f).start(tweenManager);
		Assets.pauseMusic();
		Assets.playMusic(Assets.menuMusic);
	}

	@Override
	public void hide() {
		tweenManager.killAll();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
