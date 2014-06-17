package com.mpv.tween;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mpv.screens.dialogs.FinishDialog;

import aurelienribon.tweenengine.TweenAccessor;

public class ActorAccessor implements TweenAccessor<Actor> {
	
	public static final int MOVE 	= 0;
	public static final int ROTATE 	= 1;
	public static final int TEXT 	= 2;
	public static final int SCALE 	= 3;
	public static final int ALPHA 	= 4;
	
	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case MOVE:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case ROTATE:
			returnValues[0] = target.getRotation();
			return 1;
		case TEXT:
			returnValues[0] = ((Integer)target.getUserObject()).floatValue();
			return 1;
		case SCALE:
			returnValues[0] = target.getScaleX();
			return 1;	
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch(tweenType) {
		case MOVE:
			target.setPosition(newValues[0], newValues[1]);
			return;
		case ROTATE:
			target.setRotation(newValues[0]);
			return;
		case ALPHA:
			target.getColor().a = newValues[0];
			return;
		case SCALE:
			target.setScale(newValues[0]);
			return;	
		case TEXT: 
			target.setUserObject(Integer.valueOf((int)newValues[0]));
			FinishDialog.points.setText(String.format("%04d", (int)newValues[0]));
			return;
		default:
			assert false;
		}
	}

}
