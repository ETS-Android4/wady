package com.mpv.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mpv.control.GestureHandler;
import com.mpv.control.InputHandler;
import com.mpv.data.Assets;
import com.mpv.data.GVars;
import com.mpv.game.actors.Player;
import com.mpv.game.world.MapManager;
import com.mpv.screens.stages.GameUIStage;
import com.mpv.tween.ActorAccessor;

public class GameScreen implements Screen {

	private Stage gameStage;
	private GameUIStage uiStage;
	private Box2DDebugRenderer debugRenderer;
	private GestureHandler gestureHandler = new GestureHandler();
	public static InputMultiplexer multiplexer;
	private GL20 gl20 = Gdx.graphics.getGL20();
	private Rectangle glViewport;
	private SpriteBatch batch;

	public GameScreen() {
		ActorAccessor actorAccessor = new ActorAccessor();
		Tween.registerAccessor(Player.class, actorAccessor);
		Tween.registerAccessor(Label.class, actorAccessor);
		Tween.registerAccessor(Image.class, actorAccessor);
		GVars.tweenManager = new TweenManager();
		batch = GVars.spriteBatch;
		// batch.setShader(Assets.shader);
		// Game Stage
		uiStage = new GameUIStage(new ScreenViewport(), batch);
		gameStage = new Stage(new ScreenViewport(GVars.frCam), batch);
		gameStage.addActor(Player.getInstance());
		Gdx.graphics.setVSync(true);
		// Input processor for gesture detection
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(gameStage);
		multiplexer.addProcessor(new GestureDetector(gestureHandler));
		multiplexer.addProcessor(new InputHandler());
		Gdx.input.setInputProcessor(multiplexer);
		// Physics renderer
		debugRenderer = new Box2DDebugRenderer();
		debugRenderer.setDrawVelocities(true);
		// UI debug
		// uiStage.setDebugAll(true);
		// gameStage.setDebugAll(true);
	}

	@Override
	public void render(float delta) {
		GVars.app.gameObject.gameUpdate(delta);
		GVars.tweenManager.update(delta);
		GVars.activePlayer.positionSync();

		uiStage.act(Gdx.graphics.getDeltaTime());
		gameStage.act(Gdx.graphics.getDeltaTime());
		// Camera and various updates
		GVars.update();
		// Clear
		gl20.glClearColor(0, 0, 0, 1);
		gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl20.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);
		// Background
		GVars.bgCam.position.set(GVars.frCam.position).scl(0.25f).add(glViewport.width / 2f, glViewport.height / 2f, 0);
		GVars.bgCam.update();
		batch.setProjectionMatrix(GVars.bgCam.combined);
		batch.begin();
		Assets.skin.getTiledDrawable("bricks").draw(batch, 0, 0, Assets.mapScaledWidth, Assets.mapScaledHeight);
		batch.end();
		// Parallax layer
		GVars.bgCam.position.set(GVars.frCam.position).scl(0.5f).add(glViewport.width / 4f, glViewport.height / 4f, 0);
		GVars.bgCam.update();
		GVars.otmRendered.setView(GVars.bgCam);
		batch.begin();
		GVars.otmRendered.renderTileLayer(MapManager.getInst().getLayerObtacles());
		batch.end();
		// Main scene
		GVars.otmRendered.setView(GVars.frCam);

		GVars.rayHandler.updateAndRender();
		batch.begin();
		GVars.otmRendered.renderTileLayer(MapManager.getInst().getLayerObtacles());
		GVars.otmRendered.renderTileLayer(MapManager.getInst().getLayerItems());
		batch.end();
		// Player
		gameStage.draw();
		// Effects
		batch.begin();
		Assets.hitEffect.draw(batch, delta);
		batch.end();
		// FPS
		// GameUIStage.labelFPS.setText(Float.toString(1/delta).substring(0, 4));
		// Physics debug
		// debugRenderer.render(GVars.world,
		uiStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		glViewport = new Rectangle(0, 0, width, height);
		GVars.resize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		GameUIStage.getInstance().gameStart();
		Assets.pauseMusic();
		Assets.playMusic(Assets.gameMusic);
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		uiStage.dispose();
		gameStage.dispose();
	}
}