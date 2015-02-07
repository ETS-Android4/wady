package com.mpv.screens.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mpv.data.Const;
import com.mpv.data.Effect;
import com.mpv.data.GVars;
import com.mpv.data.Settings;

public class ExitDialog extends Dialog {

	private float bWidth = Gdx.graphics.getWidth() / 3.2f;
	private float bHeight = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD / 1.6f;

	public ExitDialog(String title, Skin skin, String windowStyleName) {
		super(title, skin, windowStyleName);
		this.getContentTable().add(new Label("Are you sure?", skin, "title-text")).height(bHeight / 1.6f)
				.pad(bHeight / 6f).row();
		this.button("Yes", true).button("No", false).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
		for (Cell<?> cell : this.getButtonTable().getCells()) {
			cell.size(bWidth, bHeight).pad(bHeight / 6f);
		}
	}

	protected void result(Object obj) {
		Effect.button();
		if (obj.equals(true)) {
			Settings.save();
			Gdx.app.exit();
		} else {

		}
	}

	@Override
	public Dialog show(Stage stage) {
		// TODO Auto-generated method stub
		super.show(stage);
		this.setPosition(this.getX(), this.getY() + getHeight() / 3f);
		return this;
	}

}
