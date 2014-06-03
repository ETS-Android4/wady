package com.mpv.screens.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;

public class LevelStage extends Stage {
	
	public LevelStage() {
		super();
		float buttonSize=Const.PLAYER_SIZE*GVars.BOX_TO_WORLD/1.6f;
		Table mainTable = new Table();
		Table buttonTable = new Table();
		Widget emptyWidget = new Widget();
		//Debug
		//mainTable.debug();
		//buttonTable.debug();
		this.addActor(mainTable);
		mainTable.setBackground(Assets.skin.getDrawable("window"));
		mainTable.setFillParent(true);
		mainTable.add(new Image(Assets.skin, "caption-levels")).size(this.getWidth()/1.6f, this.getHeight()/6.4f).row();
		mainTable.add(emptyWidget).height(buttonSize).row();
		mainTable.add(buttonTable);
		//buttonTable.setFillParent(true);
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				buttonTable.add(emptyWidget).width(buttonSize/4);
				buttonTable.add(new Image(Assets.skin, "button")).size(buttonSize);
				buttonTable.add(emptyWidget).width(buttonSize/4);
			}
			buttonTable.row();
			buttonTable.add(emptyWidget).height(buttonSize/2).row();
		}
	}
	
}
