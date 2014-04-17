package com.mpv.game;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mpv.data.Const;
import com.mpv.data.GVars;

public class GameObject {

	ApplicationHandler app;
	//Body and spite lists
	ArrayList<Sprite> SpriteList;

	//Delta time accumulator
	private float accumulator = 0;

	public GameObject() {

		PolygonShape polygonShape = new PolygonShape();

		setWorldBounds();
		//Defining blocks
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		polygonShape.setAsBox(Const.BLOCK_HALF, Const.BLOCK_HALF);
		fixtureDef.filter.categoryBits = Const.CATEGORY_BLOCK;
		fixtureDef.shape = polygonShape;
		fixtureDef.density = Const.BLOCK_DENSITY;
		fixtureDef.friction = Const.BLOCK_FRICTION;
		fixtureDef.restitution = Const.BLOCK_RESTITUTION;

		bodyDef.type = BodyType.DynamicBody;

		//Adding empty rectangle with index 15 at position 4:4

		//Cloning original SpriteList
		//
		GVars.world.setContactListener(new ContactHandler());
		//Dispose disposable
		polygonShape.dispose();
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

	public ArrayList<Sprite> GetSpriteList() {
		return SpriteList;
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
