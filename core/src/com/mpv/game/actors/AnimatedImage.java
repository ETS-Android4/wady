package com.mpv.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mpv.data.Assets;

public class AnimatedImage extends Actor {

	private float stateTime = 0;
	
	@Override
	public void act(float delta)
	{
		stateTime += delta;
	    super.act(delta);
	}
	
	public void reset()
	{
	    stateTime = 0;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		TextureRegion textureRegion;
		switch (Player.state) {
		case Player.S_IDLE:
			textureRegion = Assets.animation.getKeyFrames()[0];
			break;
		case Player.S_LJUMP:
			textureRegion = Assets.animation.getKeyFrame(stateTime, true);
			break;
		case Player.S_RJUMP:
			textureRegion = Assets.animation.getKeyFrame(stateTime, true);
			break;
		case Player.S_FALL:
			textureRegion = Assets.animation.getKeyFrames()[15];
			break;
		case Player.S_HIT:
			textureRegion = Assets.animation.getKeyFrames()[15];
			break;
		default: textureRegion = Assets.animation.getKeyFrames()[0];
			break;
		}
		batch.draw(textureRegion, this.getX(), this.getY(), 
					this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), 
					this.getScaleX(), this.getScaleY(), this.getRotation());
	}
	
}
