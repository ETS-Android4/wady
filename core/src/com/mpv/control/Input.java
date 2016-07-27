package com.mpv.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mpv.GameStarter;
import com.mpv.game.actors.Player;
import com.mpv.game.world.GameObj;

public class Input implements InputProcessor {

	GameStarter app;

	@Override
	public boolean keyDown(int keycode) {
		if (GameObj.state != GameObj.ACTIVE) {
			return false;
		}
		switch (keycode) {
		case Keys.LEFT:
			Player.get().powerLeft(true);
			break;
		case Keys.RIGHT:
			Player.get().powerRigth(true);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.BACK) {
			Gdx.app.exit();
			// GVars.app.setScreen(GVars.app.menuScreen);
		}
		switch (keycode) {
		case Keys.LEFT:
			Player.get().powerLeft(false);
			break;
		case Keys.RIGHT:
			Player.get().powerRigth(false);
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
