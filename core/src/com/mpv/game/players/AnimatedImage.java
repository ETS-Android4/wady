package com.mpv.game.players;

import com.badlogic.gdx.graphics.g2d.Batch;
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
		batch.draw((Assets.animation.getKeyFrame(stateTime, true)), this.getX(), this.getY(), 
					this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), 
					this.getScaleX(), this.getScaleY(), this.getRotation());
	}
	
}
