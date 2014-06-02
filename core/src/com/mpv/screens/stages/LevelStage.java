package com.mpv.screens.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;

public class LevelStage extends Stage {
	
	public LevelStage() {
		super();
		float buttonSize=Const.PLAYER_SIZE*GVars.BOX_TO_WORLD/1.6f;
		Table table = new Table();
		//Debug
		table.debug();
		this.addActor(table);
		table.setFillParent(true);
		
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				table.add(new Image(Assets.skin, "button")).size(buttonSize);
			}
			table.row();
		}
	}
	
}
