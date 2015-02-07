package com.mpv.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mpv.ApplicationHandler;
import com.mpv.game.actors.Player;
import com.mpv.game.world.GameObj;

public class InputHandler implements InputProcessor {

	ApplicationHandler app;

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if (GameObj.state != GameObj.ACTIVE) {
			return false;
		}
		switch (keycode) {
		case Keys.LEFT:
			Player.get().jumpLeft();
			break;
		case Keys.RIGHT:
			Player.get().jumpRigth();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Keys.BACK) {
			Gdx.app.exit();
			// GVars.app.setScreen(GVars.app.menuScreen);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
