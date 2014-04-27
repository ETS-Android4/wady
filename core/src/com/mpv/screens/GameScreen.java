package com.mpv.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mpv.control.GestureHandler;
import com.mpv.control.InputHandler;
import com.mpv.data.Assets;
import com.mpv.data.GVars;
import com.mpv.game.players.Player;
import com.mpv.game.players.Players;
import com.mpv.screens.stages.GameUIStage;
import com.mpv.tween.PlayerAccessor;

public class GameScreen implements Screen {

	private Stage gameStage;
	private GameUIStage uiStage;
	//private Box2DDebugRenderer debugRenderer;
	private GestureHandler gestureHandler = new GestureHandler();
	private InputMultiplexer multiplexer;
	private GL20 gl20 = Gdx.graphics.getGL20();
	private Rectangle glViewport;
	private SpriteBatch batch;
	//private TextureRegion currentFrame;
	//private float stateTime = 0f;
	private OrthogonalTiledMapRenderer otmRendered;
    	
	public GameScreen() {
		Tween.registerAccessor(Player.class, new PlayerAccessor());
		GVars.tweenManager = new TweenManager();
		batch = new SpriteBatch();
		//batch.setShader(Assets.shader);
		//Camera		
		GVars.cam = new OrthographicCamera(GVars.scrWidth, GVars.scrHeight);
		//Game Stage
		uiStage = new GameUIStage(new ScreenViewport(), batch);
	    gameStage = new Stage(new ScreenViewport(GVars.cam), batch);
	    Players.add(gameStage);
		Gdx.graphics.setVSync(true);
		//Input processor for gesture detection
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GestureDetector(gestureHandler));
		multiplexer.addProcessor(new InputHandler());
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(gameStage);
		Gdx.input.setInputProcessor(multiplexer);
		//Physics renderer
		//debugRenderer = new Box2DDebugRenderer();
		//Map
		MapProperties prop = Assets.map1.getProperties();

		int mapWidth = prop.get("width", Integer.class);
		//int mapHeight = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		//int tilePixelHeight = prop.get("tileheight", Integer.class);

		int mapPixelWidth = mapWidth * tilePixelWidth;
		//int mapPixelHeight = mapHeight * tilePixelHeight;
		otmRendered = new OrthogonalTiledMapRenderer(Assets.map1,GVars.scrWidth/mapPixelWidth, batch);
	}

	@Override
	public void render(float delta) {	
		GVars.app.gameObject.worldStep(delta);
		GVars.tweenManager.update(delta);
		Players.positionSync();
		uiStage.act(Gdx.graphics.getDeltaTime());
		gameStage.act(Gdx.graphics.getDeltaTime());
		gl20.glClearColor(0, 0, 0, 1);
		gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl20.glViewport((int)glViewport.x, (int)glViewport.y, (int)glViewport.width, (int)glViewport.height);
		GVars.cam.position.set(GVars.cam.position.x, Players.activePlayer.getY(),0);
		GVars.cam.update();
		//Map
		otmRendered.setView(GVars.cam);
		batch.begin();
		otmRendered.renderTileLayer((TiledMapTileLayer)Assets.map1.getLayers().get(0));
		otmRendered.renderTileLayer((TiledMapTileLayer)Assets.map1.getLayers().get(1));
		batch.end();
		//Player
		gameStage.draw();
		//Decor
		batch.begin();
		otmRendered.renderTileLayer((TiledMapTileLayer)Assets.map1.getLayers().get(2));		
		batch.end();
		//SpriteBatch and animation
		/*stateTime+=delta;
		currentFrame = Assets.animation.getKeyFrame(stateTime, true);
		batch.setProjectionMatrix(GVars.cam.combined);
		batch.begin();
		batch.draw(currentFrame, GVars.scrWidth/2, GVars.scrHeight/2);
		batch.end();*/
		//FPS
		GameUIStage.labelFPS.setText(Float.toString(1/delta).substring(0, 4));
		//Physics debug
		//debugRenderer.render(GVars.world, GVars.cam.combined.scl(GVars.BOX_TO_WORLD));		
		
		uiStage.draw();
		//UI debug
		//Table.drawDebug(uiStage);
		//Table.drawDebug(gameStage);
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
	}
	public void showDialog() {
		Gdx.input.setInputProcessor(uiStage);
	}
}
