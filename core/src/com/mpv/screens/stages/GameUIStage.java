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
import com.mpv.data.Settings;
import com.mpv.game.players.Player;
import com.mpv.game.world.GameObject;
import com.mpv.game.world.GameTimer;
import com.mpv.screens.dialogs.FailedDialog;
import com.mpv.screens.dialogs.FinishDialog;
import com.mpv.screens.dialogs.PauseDialog;
import com.mpv.screens.dialogs.StartDialog;

public class GameUIStage extends Stage {

	public static Label labelFPS;
	public static Label labelDebug;
	public static Label labelTime;
	public static PauseDialog pauseDialog = new PauseDialog("", Assets.skin, "dialog");
	public static FinishDialog finishDialog = new FinishDialog("", Assets.skin, "dialog");
	public static FailedDialog failedDialog = new FailedDialog("", Assets.skin, "dialog");
	public static StartDialog startDialog = new StartDialog("", Assets.skin, "dialog");
	private static GameUIStage instance;
	public static GameUIStage getInstance(){
		return instance;
	}
	
	public GameUIStage(Viewport viewport,	 SpriteBatch batch) {
		super(viewport, batch);
		instance = this;
		final Button bPlay = new Button(Assets.skin, "pause");
		//
		bPlay.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				if (GameObject.state == GameObject.ACTIVE) {
					GameObject.getInstance().gamePause();
					gamePause();
				}
			}
		});

		labelFPS = new Label("", Assets.skin, "game-text");
		labelDebug = new Label("", Assets.skin, "game-text");
		labelTime = new Label("", Assets.skin, "normal-text");
		Table controlPanel = new Table();
		controlPanel.setFillParent(true);
		controlPanel.debug().bottom().left();
		controlPanel.add(new Image(Assets.skin.getDrawable("timer"))).size(GVars.scrHeight/9.7f);
		controlPanel.add(labelTime).expand(true, false).height(GVars.scrHeight/13).width(getWidth()/4.3f).left();
		//controlPanel.add(labelDebug).expand(true, false).size(GVars.scrHeight/13);		
		//controlPanel.add(labelFPS).expand(true, false).size(GVars.scrHeight/13);
		controlPanel.add(bPlay).size(GVars.scrHeight/9.7f).right();
		//controlPanel.add(bExit).size(GVars.scrHeight/13);
		this.addActor(controlPanel);
	}
	

	@Override
	public void draw() {
		labelTime.setText(GameTimer.getLeftString());
		super.draw();
	}
	public void gamePause() {
		Gdx.input.setInputProcessor(instance);
		GameUIStage.pauseDialog.show(instance);
	}
	public void gameFinish() {
		Gdx.input.setInputProcessor(instance);
		FinishDialog.points.setText(String.valueOf(Settings.points[GameObject.mapIndex]));
		GameUIStage.finishDialog.show(instance);
	}
	public void gameOver() {
		Gdx.input.setInputProcessor(instance);
		GameUIStage.failedDialog.show(instance);
	}

	public void gameStart() {
		Player.getInstance().resetGame();
		Gdx.input.setInputProcessor(instance);
		GameUIStage.startDialog.show(instance);
	}
}
