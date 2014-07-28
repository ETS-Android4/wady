package com.mpv.screens.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mpv.data.Assets;
import com.mpv.data.GVars;
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
	private final Label labelTime;
	private final Button leftJump, rightJump;
	public static PauseDialog pauseDialog = new PauseDialog("", Assets.skin, "dialog");
	public static FinishDialog finishDialog = new FinishDialog("", Assets.skin, "dialog");
	public static FailedDialog failedDialog = new FailedDialog("", Assets.skin, "dialog");
	public static StartDialog startDialog = new StartDialog("", Assets.skin, "dialog");
	private static GameUIStage instance;
	public static GameUIStage getInstance() {
		return instance;
	}
	
	public GameUIStage(Viewport viewport,	 SpriteBatch batch) {
		super(viewport, batch);
		instance = this;
		final Button bPause = new Button(Assets.skin, "pause");
		//
		bPause.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				Assets.playSnd(Assets.buttonSnd);
				if (GameObject.state == GameObject.ACTIVE) {
					GameObject.getInstance().gamePause();
					gamePause();
				}
			}
		});

		labelFPS = new Label("", Assets.skin, "game-text");
		labelDebug = new Label("", Assets.skin, "game-text");
		labelTime = new Label("", Assets.skin, "normal-text");
		leftJump = new Button(Assets.skin, "leftJump");
		rightJump = new Button(Assets.skin, "rightJump");
		Widget empty = new Widget();
		Table controlPanel = new Table();
		controlPanel.setFillParent(true);
		controlPanel.debug().top();
		controlPanel.add(new Image(Assets.skin.getDrawable("timer"))).size(GVars.scrWidth/12f);
		controlPanel.add(labelTime).expand(true, false).height(getWidth()/13).width(getWidth()/4.3f).left();
		
		//controlPanel.add(labelDebug).expand(true, false).size(GVars.scrHeight/13);
		//controlPanel.add(labelFPS).expand(true, false).size(GVars.scrHeight/13);
		controlPanel.add(bPause).size(GVars.scrWidth/6.4f).right();
		Table buttonPanel = new Table();
		buttonPanel.setFillParent(true);
		buttonPanel.debug().bottom();
		buttonPanel.add(leftJump).size(GVars.scrWidth/4.8f);
		buttonPanel.add(empty).width(GVars.scrWidth/3.2f);
		buttonPanel.add(rightJump).size(GVars.scrWidth/4.8f).row();
		buttonPanel.add(empty).size(getWidth()/25f);
		this.addActor(controlPanel);
		this.addActor(buttonPanel);
		
		leftJump.addListener(new ClickListener() {
			Vector2 leftForce = new Vector2(-1f, 1f);
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				GVars.activePlayer.applyForce(leftForce);
			}
		});
		rightJump.addListener(new ClickListener() {
			Vector2 rightForce = new Vector2(1f, 1f);
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				//super.touchDown(event, x, y, pointer, button);
				GVars.activePlayer.applyForce(rightForce);
			}
		});
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
		GameUIStage.finishDialog.show(instance);
	}
	public void gameOver() {
		Gdx.input.setInputProcessor(instance);
		GameUIStage.failedDialog.show(instance);
		Assets.playSnd(Assets.failSnd);
	}

	public void gameStart() {
		Player.getInstance().resetGame();
		Gdx.input.setInputProcessor(instance);
		GameUIStage.startDialog.show(instance);
	}
}
