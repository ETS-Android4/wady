package com.mpv.game.players;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;

public class Player extends Actor  {
	
	final Player inst;
	public static final TextureRegion common = Assets.skin.getRegion("button");
	public static final TextureRegion active = Assets.skin.getRegion("button-down");
	final Body body;
	
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

		body = GVars.world.createBody(bodyDef);
		body.setUserData(this);
		//Dispose disposable
		polygonShape.dispose();
	}

	@Override
	public void draw(Batch spriteBatch, float parentAlpha) {
		if (this != Players.activePlayer) {
			spriteBatch.draw(common, this.getX(), this.getY());
		} else {
			spriteBatch.draw(active, this.getX(), this.getY());
		}
	}
	
}
