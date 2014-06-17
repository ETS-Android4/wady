package com.mpv.tween;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.TweenAccessor;

public class LabelAccessor implements TweenAccessor<Label> {

	public static final int TEXT = 0;

	@Override
	public int getValues(Label target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case TEXT:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Label target, int tweenType, float[] newValues) {
		// TODO Auto-generated method stub
		switch(tweenType) {
		case TEXT:
			target.setPosition(newValues[0], newValues[1]);
			return;
		default:
			assert false;
		}
	}
}
