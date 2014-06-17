package com.mpv.screens.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.tablelayout.Cell;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObject;
import com.mpv.screens.GameScreen;

public class FailedDialog extends Dialog {

	public FailedDialog(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		this.getContentTable().add(new Label("Failed", skin, "title-text")).row();
		//this.getContentTable().add(new Image()).size(GVars.scrWidth/3.2f);
		this.button("Menu", true).button("Retry", false).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
		for (Cell<?> cell :  this.getButtonTable().getCells()) {
			cell.size(Const.PLAYER_SIZE*GVars.BOX_TO_WORLD, Const.PLAYER_HALF*GVars.BOX_TO_WORLD);
		}
	}
	
	protected void result (Object obj) {
		if (obj.equals(true)){
			this.hide();
			GVars.app.setScreen(GVars.app.levelScreen);
		}else {
			this.hide();
			GameObject.getInstance().gameResume();
			Gdx.input.setInputProcessor(GameScreen.multiplexer);
		}
	}
}
