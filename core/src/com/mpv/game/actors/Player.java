package com.mpv.game.actors;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Const;
import com.mpv.data.Effect;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObj;
import com.mpv.tween.ActorAccessor;

public class Player extends AnimatedImage {

	public static final int S_IDLE = 0;
	public static final int S_FLY = 1;
	public static final int S_FALL = 2;
	public static final int S_HIT = 3;
	public static final int S_INVISIBLE = 4;

	public static int state = Player.S_IDLE;

	private static final float animFix = 2.5f;
	// private static final Vector2 rightForce = new Vector2(Const.BLOCK_SIZE * 0.8f, Const.BLOCK_SIZE * 0.8f);
	// private static final Vector2 leftForce = new Vector2(-Const.BLOCK_SIZE * 0.8f, Const.BLOCK_SIZE * 0.8f);
	private static boolean right = false;
	private static boolean left = false;
	private static final float force = 3f;
	private static final Vector2 rightForce = new Vector2(force, force);
	private static final Vector2 leftForce = new Vector2(-force, force);

	private static Player instance;
	public Body body;

	public static Player get() {
		if (instance == null) {
			instance = new Player();
		}
		return instance;
	}

	private Player() {
		this.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		// Actor
		this.setSize(Const.PLAYER_SIZE * GVars.BOX_TO_WORLD, Const.PLAYER_SIZE * GVars.BOX_TO_WORLD);
		this.setOrigin(this.getWidth() / 2f, this.getHeight() / animFix);
		this.setRotation(360);
		Tween.set(this, ActorAccessor.ROTATE).target(this.getRotation());
	}

	public void createBody() {
		CircleShape playerShape = new CircleShape();
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		playerShape.setRadius(Const.BODY_SIZE / 2f);
		fixtureDef.filter.categoryBits = Const.CATEGORY_PLAYER;
		fixtureDef.shape = playerShape;
		fixtureDef.density = Const.BLOCK_DENSITY;
		fixtureDef.friction = Const.BLOCK_FRICTION;
		fixtureDef.restitution = Const.BLOCK_RESTITUTION;
		bodyDef.type = BodyType.DynamicBody;
		// Body
		body = GVars.world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setFixedRotation(Const.FIXED_ROTATION);
		body.setLinearDamping(Const.BODY_LINEAR_DAMPING);
		resetGame();
		body.setUserData(this);
		// Dispose shape
		playerShape.dispose();
	}

	public void resetGame() {
		body.setTransform(GameObj.start.getTransform().getPosition(), 0f);
		this.setRotation(360f);
		body.setAwake(false);
		left = false;
		right = false;
		state = S_INVISIBLE;
	}

	public void applyImpulse(Vector2 impulse) {
		body.applyLinearImpulse(impulse, body.getWorldCenter().add(0f, Const.BLOCK_HALF).rotateRad(body.getAngle()),
				true);
		Player.state = Player.S_FLY;
		Effect.wing();
		reset();
	}

	public void applyForces() {
		if (left) {
			body.applyForceToCenter(leftForce, true);
			Player.state = Player.S_FLY;
		}
		if (right) {
			body.applyForceToCenter(rightForce, true);
			Player.state = Player.S_FLY;
		}
	}

	public void interpolate(float alpha) {
		Tween.to(this, ActorAccessor.MOVE, alpha)
				.target((body.getPosition().x - Const.PLAYER_HALF) * GVars.BOX_TO_WORLD,
						(body.getPosition().y - Const.PLAYER_SIZE / animFix) * GVars.BOX_TO_WORLD)
				.start(GVars.tweenManager);
	}

	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
		if (GVars.playerLight != null) {
			GVars.playerLight.setPosition(body.getPosition());
		}
	}

	@Override
	public void setRotation(float degrees) {
		// TODO Auto-generated method stub
		super.setRotation(degrees);
		if (GVars.playerLight != null) {
			GVars.playerLight.setDirection(degrees + 90f);
		}
	}

	public void update() {
		if (Player.state == Player.S_INVISIBLE) {
			Effect.stopSnd(Effect.WING);
			return;
		}

		Vector2 velocity = body.getLinearVelocity();
		float angle = velocity.angle();

		if (velocity.len() < Const.BLOCK_SIZE * 2f && !(left || right)) {
			Player.state = Player.S_IDLE;
			angle = 90f;
		} else if (angle > 180) {
			Player.state = Player.S_FALL;
		}
		// Rotation direction fix
		if (angle > 270) {
			angle = angle - 360;
		}
		this.setPosition((body.getPosition().x - Const.PLAYER_HALF) * GVars.BOX_TO_WORLD,
				(body.getPosition().y - Const.PLAYER_SIZE / animFix) * GVars.BOX_TO_WORLD);

		if (GameObj.state != GameObj.ACTIVE) {
			Effect.stopSnd(Effect.WING);
			return;
		}
		Tween.to(this, ActorAccessor.ROTATE, 0.2f).target(angle + 270f).start(GVars.tweenManager);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (state != S_INVISIBLE) {
			super.draw(batch, parentAlpha);
		}
	}

	public void powerLeft(boolean enable) {
		left = enable;
		if (enable && !right) {
			Effect.wing();
		}
		if (!(left || right)) {
			Effect.stopSnd(Effect.WING);
		}
		reset();
	}

	public void powerRigth(boolean enable) {
		right = enable;
		if (enable && !left) {
			Effect.wing();
		}
		if (!(left || right)) {
			Effect.stopSnd(Effect.WING);
		}
		reset();
	}
}
