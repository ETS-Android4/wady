package com.mpv.ui.dialogs;

import static com.mpv.data.Sounds.ID.BUTTON;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.Sounds;
import com.mpv.data.GVars;
import com.mpv.data.Settings;

public class ExitDialog extends Dialog {

	private float bSize = Const.PLAYER_SIZE * GVars.BOX_TO_WORLD * 1.2f;

	public ExitDialog(String title, Skin skin, String windowStyleName) {
		super(title, skin, windowStyleName);
		Button yes = new Button(Assets.skin, "yes-button");
		Button no = new Button(Assets.skin, "no-button");
		this.getContentTable().add(new Label("Are you sure?", skin, "title-text")).height(bSize / 1.6f).row();
		this.button(yes, true).button(no, false).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
		for (Cell<?> cell : this.getButtonTable().getCells()) {
			cell.size(bSize);
		}
	}

	protected void result(Object obj) {
		Sounds.play(BUTTON);
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
		// this.setPosition(this.getX(), this.getY() + getHeight() / 3f);
		return this;
	}

}
