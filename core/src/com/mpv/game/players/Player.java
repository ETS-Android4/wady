package com.mpv.game.players;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;

public class Player extends Image  {
	
	final Player inst;
	private Body body;
	
	public Player() {
		inst = this;
		this.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Players.activePlayer = inst;
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
		PolygonShape polygonShape = new PolygonShape();
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		polygonShape.setAsBox(Const.BLOCK_HALF, Const.BLOCK_HALF);
		fixtureDef.filter.categoryBits = Const.CATEGORY_BLOCK;
		fixtureDef.shape = polygonShape;
		fixtureDef.density = Const.BLOCK_DENSITY;
		fixtureDef.friction = Const.BLOCK_FRICTION;
		fixtureDef.restitution = Const.BLOCK_RESTITUTION;
		bodyDef.type = BodyType.DynamicBody;
		//Body
		body = GVars.world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setFixedRotation(Const.FIXED_ROTATION);
	    body.setLinearDamping(Const.BODY_LINEAR_DAMPING);
	    body.setTransform(5f, 5f, 0);
		body.setUserData(this);
		//Actor
		this.setDrawable(Assets.skin, "button");
		this.setSize(Const.BLOCK_SIZE*GVars.BOX_TO_WORLD, Const.BLOCK_SIZE*GVars.BOX_TO_WORLD);
		//Dispose disposable
		polygonShape.dispose();
	}

	public void applyForce(Vector2 impulse) {
		body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
	}
	
	public void positionSync() {
		this.setPosition(
				(body.getPosition().x-Const.BLOCK_HALF)*GVars.BOX_TO_WORLD, 
				(body.getPosition().y-Const.BLOCK_HALF)*GVars.BOX_TO_WORLD
				);
	}
}
