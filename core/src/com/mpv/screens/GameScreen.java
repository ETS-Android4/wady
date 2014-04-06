package com.mpv.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mpv.control.GestureHandler;
import com.mpv.control.InputHandler;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.players.Player;
import com.mpv.game.players.Players;
import com.mpv.screens.stages.GameUIStage;
import com.mpv.tween.PlayerAccessor;

public class GameScreen implements Screen {

	private Stage gameStage;
	private GameUIStage uiStage;
	Box2DDebugRenderer debugRenderer;
	private GestureHandler gestureHandler = new GestureHandler();
	private InputMultiplexer multiplexer;
	private GL20 gl20 = Gdx.graphics.getGL20();
	private Rectangle glViewport;
	private SpriteBatch batch;
	private TextureRegion currentFrame;
	private float stateTime = 0f;
	private OrthogonalTiledMapRenderer otmRendered;
    	
	public GameScreen() {
		Tween.registerAccessor(Player.class, new PlayerAccessor());
		GVars.tweenManager = new TweenManager();
		batch = new SpriteBatch();
		batch.setShader(Assets.shader);
		uiStage = new GameUIStage(new ScreenViewport(), batch);
		
		//Camera		
				GVars.cam = new OrthographicCamera(GVars.scrWidth, GVars.scrHeight);
				GVars.cam.position.set(Const.widthInPixels/2, Const.heightInPixels/2, 0);
		//Game Stage
	    gameStage = new Stage(new ScreenViewport(GVars.cam), batch);
	    Players.add(gameStage);
	    gameStage.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//Tween.set(Players.activePlayer, PlayerAccessor.MOVE).target(Players.activePlayer.getX(), Players.activePlayer.getY());
				Tween.to(Players.activePlayer, PlayerAccessor.MOVE, 1f)
					.target(x, y)
					.start(GVars.tweenManager);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	    
		Gdx.graphics.setVSync(true);
		//Input processor for gesture detection
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GestureDetector(gestureHandler));
		multiplexer.addProcessor(new InputHandler());
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(gameStage);
		Gdx.input.setInputProcessor(multiplexer);
		//Physics renderer
		debugRenderer = new Box2DDebugRenderer();
		//Batch
		otmRendered = new OrthogonalTiledMapRenderer(Assets.map1);
	}

	@Override
	public void render(float delta) {	
		//game.worldStep(delta);
		GVars.tweenManager.update(delta);
		uiStage.act(Gdx.graphics.getDeltaTime());
		gameStage.act(Gdx.graphics.getDeltaTime());
		gl20.glClearColor(0, 0, 0, 1);
		gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl20.glViewport((int)glViewport.x, (int)glViewport.y, (int)glViewport.width, (int)glViewport.height);
		GVars.cam.update();
		//Map
		otmRendered.setView(GVars.cam);
		otmRendered.render();
		//SpriteBatch and animation
		stateTime+=delta;
		currentFrame = Assets.animation.getKeyFrame(stateTime, true);
		batch.setProjectionMatrix(GVars.cam.combined);
		batch.begin();
		batch.draw(currentFrame, Const.widthInPixels/2, Const.heightInPixels/2);
		batch.end();
		//FPS
		GameUIStage.labelFPS.setText(Float.toString(1/delta).substring(0, 4));
		//Physics debug
		debugRenderer.render(GVars.world, GVars.cam.combined.scl(Const.BOX_TO_WORLD));		
		gameStage.draw();
		uiStage.draw();
		//UI debug
		//Table.drawDebug(uiStage);
		//Table.drawDebug(gameStage);
	}

	@Override
	public void resize(int width, int height) {
		
		glViewport = new Rectangle(0, 0, width, height);
		uiStage.getViewport().setWorldSize(width, width);;
		gameStage.getViewport().setWorldSize(width, height);
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
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void hide() {

	}
	
	@Override
	public void dispose() {
		otmRendered.dispose();
		uiStage.dispose();
		gameStage.dispose();
		Assets.skin.dispose();
	}
	public void showDialog() {
		Gdx.input.setInputProcessor(uiStage);
	}
}
