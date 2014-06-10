package com.mpv.screens.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.esotericsoftware.tablelayout.Cell;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObject;
import com.mpv.screens.GameScreen;

public class StartDialog extends Dialog {
	
	public StartDialog(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		this.getContentTable().add(new Label("Ready?", Assets.skin)).row();
		this.getContentTable().add(new Widget()).size(GVars.scrHeight/13);
		this.getContentTable().setFillParent(false);
		//this.getContentTable().debug();
		this.button("Go!", true).key(Keys.ENTER, true);
		for (Cell<?> cell :  this.getButtonTable().getCells()) {
			cell.size(Const.PLAYER_SIZE*GVars.BOX_TO_WORLD, Const.PLAYER_HALF*GVars.BOX_TO_WORLD);
		}
	}
	
	protected void result (Object obj) {
		GameObject.getInstance().gameStart();
		Gdx.input.setInputProcessor(GameScreen.multiplexer);
	}
}
