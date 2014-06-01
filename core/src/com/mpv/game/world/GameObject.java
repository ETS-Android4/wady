package com.mpv.game.world;

import java.util.concurrent.TimeUnit;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.ContactHandler;
import com.mpv.game.players.Player;

public class GameObject {
	
	public static final int ACTIVE = 0;
	public static final int PAUSE = 1;
	public static final int OVER = 2;
	public static final int FINISH = 3;
	
	public static int state = FINISH;
	
	//Delta time accumulator
	private float accumulator = 0;
	private long startTime, lastTime, seconds;
	private static GameObject instance;
	
	
	public static GameObject getInstance() {
        if (instance == null)  {
            instance = new GameObject();
        }
        return instance;
    }

	private GameObject() {
		setWorldBounds();
		MapBodyBuilder.buildShapes(Assets.map1, 32f, GVars.world);
		GVars.world.setContactListener(new ContactHandler());
	}
	
	public void gameStart() {
		startTime = System.currentTimeMillis();
		lastTime = startTime;
		seconds = 0;
		state = ACTIVE;
	}
	public void gamePause() {
		state = PAUSE;
		Player.state = Player.S_FALL;
	}
	public void gameResume() {
		if (state != PAUSE) {
			gameStart();
		} else {
			lastTime = System.currentTimeMillis();
			seconds = seconds + TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis()-lastTime));
			state = ACTIVE;
		}
	}
	public void gameFinish() {
		state = FINISH;
	}
	
	public void gameOver() {
		state = OVER;
	}
	
	public void gameUpdate(float delta) {
		if (state != ACTIVE) {
			return;
		}
		worldStep(delta);
		Long passed;
		passed = seconds + TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis()-lastTime));
		if (passed >= 3599) {
			gameOver();
		} else {
			GVars.gameTimeMin = (int) (seconds / 60);
			GVars.gameTimeSec = (int) (seconds % 60);
		}
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
