package com.mpv.screens.dialogs;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.tablelayout.Cell;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObject;

public class PauseDialog extends Dialog {

	public PauseDialog(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		this.getContentTable().add(new Image()).size(GVars.scrWidth/1.6f);
		this.button("Menu", true).button("Back", false).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
		for (Cell<?> cell :  this.getButtonTable().getCells()) {
			cell.size(Const.PLAYER_SIZE*GVars.BOX_TO_WORLD, Const.PLAYER_HALF*GVars.BOX_TO_WORLD);
		}
	}
	
	protected void result (Object obj) {
		if (obj.equals(true)){
			this.hide();
			GVars.app.setScreen(GVars.app.menuScreen);
		}else {
			this.hide();
			GameObject.getInstance().gameResume();
		}
	}
}
