package com.mpv.screens.stages;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.GVars;
import com.mpv.data.Settings;
import com.mpv.game.world.GameObject;

public class LevelStage extends Stage {
	
	private int tmpIndex = 0;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
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
		TextButton tmp;
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				buttonTable.add(emptyWidget).width(buttonSize/4);
				tmp = new TextButton(String.valueOf(i*4+j+1),Assets.skin, "item");
				buttonGroup.add(tmp);
				buttonTable.add(tmp).size(buttonSize);
				buttonTable.add(emptyWidget).width(buttonSize/4);
			}
			buttonTable.row();
			buttonTable.add(emptyWidget).height(buttonSize/2).row();
		}
		updateButtons();
		//left/right buttons
		Table controlTable = new Table();
		mainTable.add(controlTable);
		controlTable.add(leftButton).left();
		controlTable.add(emptyWidget).width(buttonSize);
		controlTable.add(rightButton).right();
		
		buttonTable.addListener(new ClickListener(){
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				tmpIndex = buttonGroup.getButtons().indexOf(buttonGroup.getChecked(), true); 
			}
		});
		
		rightButton.addListener(new ClickListener(){
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				if (tmpIndex != GameObject.mapIndex) {
					GameObject.mapIndex = tmpIndex; 
					Assets.loadMap(0, GameObject.mapIndex);
				}
				GVars.app.setScreen(GVars.app.gameScreen);
				GameObject.getInstance().gameResume();
			}
		});
		leftButton.addListener(new ClickListener(){
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				GVars.app.setScreen(GVars.app.menuScreen);
			}
		});
		
		this.addListener(new InputListener() {
			public boolean keyUp (InputEvent event, int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					GVars.app.setScreen(GVars.app.menuScreen);
				}
				return false;
			}
		});
	}
	public void updateButtons() {
		for (int i=1; i<16; i++) {
			if (Settings.points[i-1]==0) {
				buttonGroup.getButtons().get(i).setDisabled(true);
			}else
				buttonGroup.getButtons().get(i).setDisabled(false);
		}
	}
}
