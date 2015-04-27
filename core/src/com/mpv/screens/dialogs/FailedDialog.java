package com.mpv.screens.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.Effect;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObj;
import com.mpv.screens.GameScreen;

public class FailedDialog extends CustomDialog {

	private float bSize = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD * 1.2f;

	public FailedDialog(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		Button play = new Button(Assets.skin, "play-button");
		Button menu = new Button(Assets.skin, "yes-button");
		this.getContentTable().add(new Label("Time's Up!", skin, "title-text")).height(bSize / 1.4f).row();
		this.button(menu, true).button(play, false).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
		for (Cell<?> cell : this.getButtonTable().getCells()) {
			cell.size(bSize);
		}
	}

	@Override
	protected void result(Object obj) {
		Effect.button();
		if (obj.equals(true)) {
			this.hide();
			GVars.app.setScreen(GVars.app.levelScreen);
		} else {
			this.hide();
			GameObj.get().gameResume();
			Assets.gameMusic();
			Gdx.input.setInputProcessor(GameScreen.multiplexer);
		}
	}
}
