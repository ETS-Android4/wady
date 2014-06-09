package com.mpv.screens.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.esotericsoftware.tablelayout.Cell;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObject;
import com.mpv.screens.GameScreen;

public class FinishDialog extends Dialog {

	public static Label points = new Label("", Assets.skin, "normal-text");
	
	public FinishDialog(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		Widget empty = new Widget();
		Table pointsTable = new Table();
		//pointsTable.debug();
		pointsTable.add(new Image(Assets.skin.getDrawable("star"))).size(GVars.scrHeight/13).left();
		pointsTable.add(empty).size(GVars.scrHeight/13);
		pointsTable.add(points);
		//this.getContentTable().debug();
		this.getContentTable().add(new Label("Completed!", skin)).row();
		this.getContentTable().add(empty).width(GVars.scrWidth/1.6f).height(GVars.scrHeight/13).row();
		this.getContentTable().add(pointsTable).row();
		this.getContentTable().add(empty).size(GVars.scrHeight/13);
		this.button("Menu", true).button("Retry", false).key(Keys.ENTER, true).key(Keys.ESCAPE, false);
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
			Gdx.input.setInputProcessor(GameScreen.multiplexer);
		}
	}
	
}
