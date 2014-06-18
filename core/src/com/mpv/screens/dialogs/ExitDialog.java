package com.mpv.screens.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.tablelayout.Cell;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.data.Settings;

public class ExitDialog extends Dialog {

	public ExitDialog(String title, Skin skin, String windowStyleName) {
		super(title, skin, windowStyleName);
		// TODO Auto-generated constructor stub
		this.text("Are you sure?").button("Exit", true).button("Back", false).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
		for (Cell<?> cell :  this.getButtonTable().getCells()) {
			cell.size(Const.PLAYER_SIZE*GVars.BOX_TO_WORLD, Const.PLAYER_HALF*GVars.BOX_TO_WORLD);
		}
	}
	protected void result (Object obj) {
		Assets.playSnd(Assets.buttonSnd);
		if (obj.equals(true)){
			Settings.save();
			Gdx.app.exit();
		}else {

		}
	}
	@Override
	public Dialog show(Stage stage) {
		// TODO Auto-generated method stub
		super.show(stage);
		this.setPosition(this.getX(), this.getY()+getHeight()/3f);
		return this;
	}
	
}
