package com.mpv.screens.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mpv.data.Assets;
import com.mpv.data.GVars;

public class GameUIStage extends Stage {

	public static Label labelFPS;
	public static Label labelDebug;
	public GameUIStage(Viewport viewport,	 SpriteBatch batch) {
		super(viewport, batch);
		Button bExit = new Button(Assets.skin, "button-exit");
		//
		bExit.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				Gdx.app.exit();
			}
		});
		labelFPS = new Label("", Assets.skin, "normal-text");
		labelDebug = new Label("", Assets.skin, "normal-text");
		Table controlPanel = new Table();
		controlPanel.setFillParent(true);
		controlPanel.debug().bottom().right();
		controlPanel.add(labelDebug).expand(true, false).size(GVars.scrHeight/13);
		controlPanel.add(labelFPS).size(GVars.scrHeight/13);
		controlPanel.add(bExit).size(GVars.scrHeight/15);   
		this.addActor(controlPanel);
	}

}
