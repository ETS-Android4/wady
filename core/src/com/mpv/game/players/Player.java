package com.mpv.game.players;

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
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObject;
import com.mpv.tween.ActorAccessor;

public class Player extends AnimatedImage  {
	
	public static final int S_IDLE = 0;
	public static final int S_LJUMP = 1;
	public static final int S_RJUMP = 2;
	public static final int S_FALL = 3;
	public static final int S_HIT = 4;
	public static final int S_INVISIBLE = 5;
	
	public static int state = Player.S_IDLE;
	
	private static final float animFix = 2.5f;
	
	private static Player instance;
	public Body body;
	
	public static Player getInstance() {
		if (instance == null)  {
            instance = new Player();
        }
        return instance;
	}
	
	private Player() {
		GVars.activePlayer = this;
		
		
		this.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
		//Actor
		this.setSize(Const.PLAYER_SIZE*GVars.BOX_TO_WORLD, Const.PLAYER_SIZE*GVars.BOX_TO_WORLD);
		this.setOrigin(this.getWidth()/2f, this.getHeight()/animFix);
		this.setRotation(360);
		Tween.set(this, ActorAccessor.ROTATE).target(this.getRotation());
	
	}

	public void createBody() {
		CircleShape playerShape = new CircleShape();
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		playerShape.setRadius(Const.BLOCK_HALF);
		fixtureDef.filter.categoryBits = Const.CATEGORY_PLAYER;
		fixtureDef.shape = playerShape;
		fixtureDef.density = Const.BLOCK_DENSITY;
		fixtureDef.friction = Const.BLOCK_FRICTION;
		fixtureDef.restitution = Const.BLOCK_RESTITUTION;
		bodyDef.type = BodyType.DynamicBody;
		//Body
		body = GVars.world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setFixedRotation(Const.FIXED_ROTATION);
	    body.setLinearDamping(Const.BODY_LINEAR_DAMPING);
	    resetGame();
		body.setUserData(this);
		//Dispose shape
		playerShape.dispose();
	}
	public void resetGame() {
		body.setTransform(5f, Const.BLOCK_SIZE*2.5f, 0f);
		this.setRotation(360f);
		body.setAwake(false);
		state = S_IDLE;
	}
	
	public void applyForce(Vector2 impulse) {
		body.applyLinearImpulse(impulse, body.getWorldCenter().add(0f, Const.BLOCK_HALF).rotateRad(body.getAngle()), true);
		Player.state = Player.S_LJUMP;
		Assets.playSnd(Assets.wingSnd);
		reset();
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
		if (GVars.playerLight!=null) {
			GVars.playerLight.setPosition(body.getPosition());
		}
	}

	@Override
	public void setRotation(float degrees) {
		// TODO Auto-generated method stub
		super.setRotation(degrees);
		if (GVars.playerLight!=null) {
			GVars.playerLight.setDirection(degrees+90f);
		}
	}

	public void positionSync() {
		if (Player.state == Player.S_INVISIBLE) return;
		
		Vector2 velocity = body.getLinearVelocity();
		float angle = velocity.angle();
		
		if (velocity.len()<Const.BLOCK_HALF) {
			Player.state = Player.S_IDLE;
			angle = 90f;
		}	else if (angle>180) {
				Player.state = Player.S_FALL;
			}
		//Rotation direction fix
		if (angle > 270) {
			angle = angle - 360;
		}
		this.setPosition(
				(body.getPosition().x-Const.PLAYER_HALF)*GVars.BOX_TO_WORLD, 
				(body.getPosition().y-Const.PLAYER_SIZE/animFix)*GVars.BOX_TO_WORLD
				);
		
		if (GameObject.state != GameObject.ACTIVE) return;
		Tween.to(this, ActorAccessor.ROTATE, 0.2f)
			.target(angle+270f)
			.start(GVars.tweenManager);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (state != S_INVISIBLE) {
			super.draw(batch, parentAlpha);
		}
	}

}
