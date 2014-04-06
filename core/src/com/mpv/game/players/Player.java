package com.mpv.game.players;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;

public class Player extends Actor  {
	
	final Player inst;
	public static final TextureRegion common = Assets.skin.getRegion("button");
	public static final TextureRegion active = Assets.skin.getRegion("button-down");
	
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
