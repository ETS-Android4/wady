package com.mpv.screens.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mpv.data.Assets;
import com.mpv.data.GVars;
import com.mpv.game.world.GameObject;
import com.mpv.screens.dialogs.PauseDialog;

public class GameUIStage extends Stage {

	public static Label labelFPS;
	public static Label labelDebug;
	public static Label labelTime;
	public static PauseDialog pauseDialog = new PauseDialog("", Assets.skin, "default");
	private GameUIStage instance;
	
	public GameUIStage(Viewport viewport,	 SpriteBatch batch) {
		super(viewport, batch);
		instance = this;
		Button bExit = new Button(Assets.skin, "button-exit");
		final Button bPlay = new Button(Assets.skin, "button-play");
		//
		bExit.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				Gdx.app.exit();
			}
		});
		bPlay.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				if (GameObject.state == GameObject.ACTIVE) {
					GameObject.getInstance().gamePause();
					GameUIStage.pauseDialog.show(instance);
				}
			}
		});
		labelFPS = new Label("", Assets.skin, "game-text");
		labelDebug = new Label("", Assets.skin, "game-text");
		labelTime = new Label("", Assets.skin, "game-text");
		Table controlPanel = new Table();
		controlPanel.setFillParent(true);
		controlPanel.debug().bottom().left();
		controlPanel.add(new Image(Assets.skin.getDrawable("timer"))).size(GVars.scrHeight/13);
		controlPanel.add(labelTime).expand(true, false).height(GVars.scrHeight/13).width(getWidth()/4.3f).left();
		//controlPanel.add(labelDebug).expand(true, false).size(GVars.scrHeight/13);
		
		//controlPanel.add(labelFPS).expand(true, false).size(GVars.scrHeight/13);
		controlPanel.add(bPlay).size(GVars.scrHeight/13).right();
		//controlPanel.add(bExit).size(GVars.scrHeight/13);
		this.addActor(controlPanel);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		labelTime.setText(String.format("%02d:%02d", GVars.gameTimeMin, GVars.gameTimeSec));
		super.draw();
	}
	
}
