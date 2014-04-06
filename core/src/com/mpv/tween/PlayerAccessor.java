package com.mpv.tween;

import com.mpv.game.players.Player;
import aurelienribon.tweenengine.TweenAccessor;

public class PlayerAccessor implements TweenAccessor<Player> {
	
	public static final int MOVE = 0;
	public static final int ROTATE = 1;

	@Override
	public int getValues(Player target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case MOVE:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case ROTATE:
			returnValues[0] = target.getRotation();
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Player target, int tweenType, float[] newValues) {
		// TODO Auto-generated method stub
		switch(tweenType) {
		case MOVE:
			target.setPosition(newValues[0], newValues[1]);
			return;
		case ROTATE:
			target.setRotation(newValues[0]);
			return;
		default:
			assert false;
		}
	}

}
