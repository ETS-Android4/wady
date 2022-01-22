package com.mpv.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LabelAccessor implements TweenAccessor<Label> {

    public static final int TEXT = 0;

    @Override
    public int getValues(Label target, int tweenType, float[] returnValues) {
        if (tweenType == TEXT) {
            returnValues[0] = target.getX();
            returnValues[1] = target.getY();
            return 1;
        }
        assert false;
        return -1;
    }

    @Override
    public void setValues(Label target, int tweenType, float[] newValues) {
        if (tweenType == TEXT) {
            target.setPosition(newValues[0], newValues[1]);
        } else {
            assert false;
        }
    }
}
