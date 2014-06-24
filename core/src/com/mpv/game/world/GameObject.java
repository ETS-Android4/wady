package com.mpv.game.world;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.data.Settings;
import com.mpv.game.ContactHandler;
import com.mpv.game.players.Player;
import com.mpv.screens.stages.GameUIStage;

public class GameObject {
	
	public static final int ACTIVE = 0;
	public static final int PAUSE = 1;
	public static final int OVER = 2;
	public static final int FINISH = 3;
	
	public static int state = FINISH;
	
	//Delta time accumulator
	private float accumulator = 0;
	private float mapLimit;
	public static int mapIndex = -1;
	private static GameObject instance;
	public static Body start, exit;
	
	public static GameObject getInstance() {
        if (instance == null)  {
            instance = new GameObject();
        }
        return instance;
    }

	public void loadWorld() {
		if (GVars.world != null) {
			GVars.world.dispose();
			GVars.world = null;
		}
		GVars.world = new World(new Vector2(0, -9.8f), true);
		GVars.world.setContactListener(new ContactHandler());
		setWorldBounds();
		MapBodyBuilder.buildShapes(Assets.map, 32f, GVars.world);
		Player.getInstance().createBody();
		//Time limit
		mapLimit = Integer.parseInt((String)Assets.map.getProperties().get("Time"));
		//Light
		if (GVars.rayHandler!=null) {
			GVars.rayHandler.dispose();
		}
		GVars.rayHandler = new RayHandler(GVars.world);
		GVars.sceneryLight = new ConeLight(GVars.rayHandler, 24, new Color(0.5f, 0.5f, 0.5f, 1f), Const.widthInMeters/3.2f, Const.BLOCK_SIZE, Const.BLOCK_SIZE, 0f, 180f);
		GVars.sceneryLight.attachToBody(Player.getInstance().body, 0f, 0f);
		GVars.playerLight = new ConeLight(GVars.rayHandler, 24, new Color(1,1,1,1), Const.widthInMeters, Const.BLOCK_SIZE, Const.BLOCK_SIZE, 90f, 30f);
		GVars.playerLight.setSoft(false);
		
	}
	
	public void gameStart() {
		GameTimer.getInstance().setTimer(mapLimit);
		Player.getInstance().resetGame();
		state = ACTIVE;
		Player.state = Player.S_IDLE;
		Assets.playSnd(Assets.dingSnd);
	}
	public void gamePause() {
		state = PAUSE;
		Player.state = Player.S_FALL;
	}
	public void gameResume() {
		if (state != PAUSE) {
			gameStart();
		} else {
			state = ACTIVE;
		}
	}
	public void gameFinish() {
		state = FINISH;
		Player.state = Player.S_INVISIBLE;
		Settings.points[mapIndex] = GameTimer.getInstance().getLeftSec()*10;
		
		GameUIStage.getInstance().gameFinish();
	}
	
	public void gameOver() {
		state = OVER;
		Player.state = Player.S_INVISIBLE;
		GameUIStage.getInstance().gameOver();
	}
	
	public void gameUpdate(float delta) {
		if (state != ACTIVE) {
			return;
		}
		GameTimer.getInstance().update(delta);
		worldStep(delta);
	}
	
	public float getMapLimit() {
		return mapLimit;
	}
	
	private void setWorldBounds() {
		
		Vector2 lowerLeftCorner = new Vector2(Const.startpointX, Const.startpointY);
		Vector2 lowerRightCorner = new Vector2(Const.widthInMeters-Const.startpointX, Const.startpointY);
		Vector2 upperLeftCorner = new Vector2(Const.startpointX, Const.heightInMeters-Const.startpointY);
		Vector2 upperRightCorner = new Vector2(Const.widthInMeters-Const.startpointX, Const.heightInMeters-Const.startpointY);
		EdgeShape edgeBoxShape = new EdgeShape();
		Body groundBody;
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.StaticBody;
		FixtureDef groundFixtureDef = new FixtureDef();
		groundFixtureDef.shape = edgeBoxShape;
		groundFixtureDef.density = 0f;
		groundFixtureDef.filter.categoryBits = Const.CATEGORY_SCENERY;
		groundFixtureDef.restitution = 0f;
		groundBody = GVars.world.createBody(groundBodyDef);		
		edgeBoxShape.set(lowerLeftCorner, lowerRightCorner);
		groundBody.createFixture(groundFixtureDef);
		edgeBoxShape.set(lowerLeftCorner, upperLeftCorner);
		groundBody.createFixture(groundFixtureDef);
		edgeBoxShape.set(upperLeftCorner, upperRightCorner);
		groundBody.createFixture(groundFixtureDef);
		edgeBoxShape.set(lowerRightCorner, upperRightCorner);
		groundBody.createFixture(groundFixtureDef);
		//Dispose
		edgeBoxShape.dispose();
	}

	public void worldStep (float delta){
		//Should be improved on heavy applications (< 60 FPS)
		if (delta >= (Const.BOX_STEP/3)) {
			GVars.world.step(delta, Const.BOX_VELOCITY_ITERATIONS, Const.BOX_POSITION_ITERATIONS);
			accumulator = 0;
		} else {
			accumulator += delta;
			if (accumulator >= Const.BOX_STEP) {
				GVars.world.step(accumulator, Const.BOX_VELOCITY_ITERATIONS, Const.BOX_POSITION_ITERATIONS);
				accumulator = 0;
			}
		}
	}
}
