package com.mpv.control;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.mpv.data.GVars;


public class GestureHandler implements GestureListener {
	
	Vector2 vel = new Vector2();
	Vector2 dist = new Vector2();
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {		
		GVars.cam.position.add(-deltaX, deltaY, 0);
		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		float newZoom = GVars.cam.zoom -(distance - initialDistance)*0.0002f;
		if (newZoom < 4f && newZoom > 1f) {
			GVars.cam.zoom = newZoom;
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub		
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
}
