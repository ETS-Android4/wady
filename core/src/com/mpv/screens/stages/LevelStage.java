package com.mpv.screens.stages;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;

public class LevelStage extends Stage {
	
	public static ArrayList<TextButton> itemsList = new ArrayList<TextButton>(16);
	
	public LevelStage() {
		super();
		float buttonSize=Const.PLAYER_SIZE*GVars.BOX_TO_WORLD/1.6f;
		Table mainTable = new Table();
		Table buttonTable = new Table();
		Widget emptyWidget = new Widget();
		Button leftButton = new Button(Assets.skin, "arrow-left");
		Button rightButton = new Button(Assets.skin, "arrow-right");
		//Debug
		//mainTable.debug();
		//buttonTable.debug();
		this.addActor(mainTable);
		mainTable.setBackground(Assets.skin.getDrawable("none"));
		mainTable.setFillParent(true);
		mainTable.add(new Image(Assets.skin, "button")).size(this.getWidth()/1.6f, this.getHeight()/6.4f).row();
		mainTable.add(emptyWidget).height(buttonSize/2).row();
		mainTable.add(buttonTable).row();
		mainTable.add(emptyWidget).height(buttonSize/2).row();
		//buttonTable.setFillParent(true);
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				buttonTable.add(emptyWidget).width(buttonSize/4);
				itemsList.add(new TextButton(String.valueOf(i*4+j+1),Assets.skin, "item"));
				buttonTable.add(itemsList.get(itemsList.size()-1)).size(buttonSize);
				buttonTable.add(emptyWidget).width(buttonSize/4);
			}
			buttonTable.row();
			buttonTable.add(emptyWidget).height(buttonSize/2).row();
		}
		Table controlTable = new Table();
		mainTable.add(controlTable);
		controlTable.add(leftButton).left();
		controlTable.add(emptyWidget).width(buttonSize);
		controlTable.add(rightButton).right();
		
		this.addListener(new InputListener() {
			public boolean keyUp (InputEvent event, int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					GVars.app.setScreen(GVars.app.menuScreen);
				}
				return false;
			}
		});   
	}
	
}
