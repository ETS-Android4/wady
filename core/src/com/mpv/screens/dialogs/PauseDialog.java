package com.mpv.screens.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObject;
import com.mpv.screens.GameScreen;

public class PauseDialog extends CustomDialog {

	private float bWidth = Gdx.graphics.getWidth() / 3.2f;
	private float bHeight = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD / 1.6f;

	public PauseDialog(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		this.getContentTable().add(new Label("PAUSE", skin, "title-text")).height(bHeight / 1.6f).pad(bHeight / 6f)
				.row();
		// this.getContentTable().add(new Image()).size(GVars.scrWidth/3.2f);
		this.button("Menu", true).button("Back", false).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
		for (Cell<?> cell : this.getButtonTable().getCells()) {
			cell.size(bWidth, bHeight).pad(bHeight / 6f);
		}
	}

	@Override
	protected void result(Object obj) {
		Assets.playSnd(Assets.buttonSnd);
		if (obj.equals(true)) {
			this.hide();
			GVars.app.setScreen(GVars.app.levelScreen);
		} else {
			this.hide();
			Gdx.input.setInputProcessor(GameScreen.multiplexer);
			GameObject.getInstance().gameResume();
		}
	}
}
