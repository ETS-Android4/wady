package com.mpv.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mpv.GameStarter;
import com.mpv.game.actors.Player;
import com.mpv.game.world.GameObj;

public class Input implements InputProcessor {

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
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
